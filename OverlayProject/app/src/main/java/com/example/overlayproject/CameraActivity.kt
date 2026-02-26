package com.example.overlayproject

import android.os.Bundle
import android.view.Choreographer
import android.view.SurfaceView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.viewbinding.ViewBinding
import com.google.android.filament.utils.Utils
import com.google.ar.core.CameraIntrinsics
import java.util.concurrent.ExecutorService
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.overlayproject.databinding.ActivityCamera2Binding
import com.google.android.filament.View
import com.google.android.filament.utils.ModelViewer
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityCamera2Binding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var wsManager: WebSocketManager
    private lateinit var surfaceView: SurfaceView
    private lateinit var choreographer: Choreographer
    private lateinit var modelViewer: ModelViewer
    private lateinit var preview: PreviewView
    private lateinit var cameraManager: CameraManager
    private lateinit var backBtn: ImageButton
    @Volatile
    private var latestPoseMatrix: FloatArray? = null

    companion object {
        init {
            Utils.init()
        }
        private val REQUIRED_PERMISSIONS = mutableListOf (
            Manifest.permission.CAMERA,
        ).toTypedArray()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCamera2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        backBtn = findViewById<ImageButton>(R.id.back_button)

        backBtn.setOnClickListener {
            finish()
        }

        surfaceView = findViewById(R.id.surface_view)
        preview = findViewById(R.id.viewFinder)
        wsManager = WebSocketManager {
                poseMatrix -> latestPoseMatrix = poseMatrix
        }
        wsManager.connect()

//        choreographer = Choreographer.getInstance()
//
//        modelViewer = ModelViewer(surfaceView).apply {
//            scene.skybox = null
//            view.apply {
//                blendMode = View.BlendMode.TRANSLUCENT
//            }
//        }
//
//        //loadGlb()
//        surfaceView.setZOrderMediaOverlay(true)
//        surfaceView.holder.setFormat(PixelFormat.TRANSLUCENT)
        cameraExecutor = Executors.newSingleThreadExecutor()

        cameraManager = CameraManager(
            activity = this,
            baseContext = this,
            viewBinding = viewBinding,
            cameraExecutor = cameraExecutor,
            wsManager = wsManager
        )

        if (allPermissionsGranted()) {
            cameraManager.startCamera()
        }else{
            requestPermissions()
        }
    }

    //CameraX Management
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions())
    {
            permissions ->
        var permissionGranted = true
        permissions.entries.forEach {
            if (it.key in REQUIRED_PERMISSIONS && it.value == false)
                permissionGranted = false
        }

        if (!permissionGranted) {
            Toast.makeText(baseContext,
                "Permission request Denied",
                Toast.LENGTH_SHORT).show()
        } else {
            cameraManager.startCamera()
        }
    }




    //Lifecycle management
    override fun onResume() {
        super.onResume()
       // choreographer.postFrameCallback(frameCallback)
    }

    override fun onPause() {
        super.onPause()
       // choreographer.removeFrameCallback(frameCallback)
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        //choreographer.removeFrameCallback(frameCallback)
    }

}