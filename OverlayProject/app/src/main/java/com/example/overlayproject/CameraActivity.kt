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
import android.graphics.PixelFormat
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

    //private lateinit var cameraIntrinsics: CameraIntrinsicsHeper.CameraIntricni

    @Volatile
    private var latestPoseMatrix: FloatArray? = null

    companion object {
        init {
            Utils.init()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCamera2Binding.inflate(layoutInflater)
        setContentView(R.layout.activity_camera2)

        surfaceView = findViewById(R.id.surface_view)
        preview = findViewById(R.id.viewFinder)
        choreographer = Choreographer.getInstance()

        modelViewer = ModelViewer(surfaceView).apply {
            scene.skybox = null
            view.apply {
                blendMode = View.BlendMode.TRANSLUCENT
            }
        }

        //loadGlb()
        surfaceView.setZOrderMediaOverlay(true)
        surfaceView.holder.setFormat(PixelFormat.TRANSLUCENT)
        cameraExecutor = Executors.newSingleThreadExecutor()
    }


}