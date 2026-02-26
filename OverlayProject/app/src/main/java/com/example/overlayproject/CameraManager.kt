package com.example.overlayproject

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.overlayproject.databinding.ActivityCamera2Binding
import java.io.ByteArrayOutputStream
import java.util.concurrent.ExecutorService

public class CameraManager(
    private val activity: CameraActivity,
    private val baseContext: Context,
    private val viewBinding: ActivityCamera2Binding,
    private val cameraExecutor: ExecutorService,
    private val wsManager: WebSocketManager) {

    fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(baseContext)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also{
                it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
            }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(android.util.Size(640, 480))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetRotation(viewBinding.root.display.rotation)
                .build()

            imageAnalyzer.setAnalyzer(cameraExecutor) {imageProxy ->
                try {
                    val jpegBytes = imageProxy.toJpegByteArray(60)
                    val bitmap = BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.size)
                    val rotatedBitmap = rotateBitmap(bitmap, 90f)
                    val outStream = ByteArrayOutputStream()
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream)
                    val rotatedBytes = outStream.toByteArray()
                    wsManager.sendFrame(rotatedBytes)


                    Log.d("TAG", "Frame Size: ${jpegBytes.size}")
                } catch (e: Exception) {
                    Log.e("TAG", "Frame Processing Error", e)
                } finally {
                    imageProxy.close()
                }
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    activity,
                    cameraSelector,
                    preview,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(baseContext))
    }


    fun rotateBitmap(source: Bitmap, angle: Float): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(source, 0, 0, source.width, source.height, matrix, true)
    }



    //image conversion
    fun ImageProxy.toJpegByteArray(quality: Int = 80): ByteArray {
        val nv21 = yuv420888ToNv21(this)
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), quality, out)
        return out.toByteArray()
    }


    private fun yuv420888ToNv21(image: ImageProxy): ByteArray {
        val width = image.width
        val height = image.height
        val ySize = width * height
        val uvSize = width * height / 4

        val nv21 = ByteArray(ySize + uvSize * 2)

        // Y plane
        val yBuffer = image.planes[0].buffer
        var rowStride = image.planes[0].rowStride
        var pos = 0
        for (row in 0 until height) {
            yBuffer.position(row * rowStride)
            yBuffer.get(nv21, pos, width)
            pos += width
        }

        // UV planes (interleaved VU for NV21)
        val uBuffer = image.planes[1].buffer
        val vBuffer = image.planes[2].buffer
        rowStride = image.planes[1].rowStride
        val pixelStride = image.planes[1].pixelStride

        val chromaHeight = height / 2
        val chromaWidth = width / 2

        for (row in 0 until chromaHeight) {
            var uvPos = row * rowStride
            for (col in 0 until chromaWidth) {
                nv21[pos++] = vBuffer.get(uvPos)
                nv21[pos++] = uBuffer.get(uvPos)
                uvPos += pixelStride
            }
        }
        return nv21
    }
}
