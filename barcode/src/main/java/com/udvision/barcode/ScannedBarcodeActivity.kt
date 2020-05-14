package com.udvision.barcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_scanned_barcode.*
import java.io.IOException


class ScannedBarcodeActivity : AppCompatActivity() {
    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    var intentData = ""
    var isEmail = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_barcode)
        initViews()
    }

    private fun initViews() {
        btnAction.setOnClickListener(View.OnClickListener {
            if (intentData.length > 0) {
                if (isEmail){
                 /*   startActivity(
                    Intent(this@ScannedBarcodeActivity, EmailActivity::class.java)
                        .putExtra("email_address", intentData)) */
                } else {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(intentData)))
                }
            }
        })
    }

    private fun initialiseDetectorsAndSources() {
        Toast.makeText(applicationContext, "Barcode scanner started", Toast.LENGTH_SHORT)
            .show()
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()
        surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@ScannedBarcodeActivity,
                            Manifest.permission.CAMERA
                        ) === PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource.start(surfaceView!!.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@ScannedBarcodeActivity,
                            arrayOf(Manifest.permission.CAMERA),
                            ScannedBarcodeActivity.Companion.REQUEST_CAMERA_PERMISSION
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(
                    applicationContext,
                    "To prevent memory leaks barcode scanner has been stopped",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() != 0) {
                    txtBarcodeValue!!.post {
                        if (barcodes.valueAt(0).email != null) {
                            txtBarcodeValue!!.removeCallbacks(null)
                            intentData = barcodes.valueAt(0).email.address
                            txtBarcodeValue!!.text = intentData
                            isEmail = true
                            btnAction!!.text = "ADD CONTENT TO THE MAIL"
                        } else {
                            isEmail = false
                            btnAction!!.text = "LAUNCH URL"
                            intentData = barcodes.valueAt(0).displayValue
                            txtBarcodeValue!!.text = intentData
                        }
                    }
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        cameraSource!!.release()
    }

    override fun onResume() {
        super.onResume()
        initialiseDetectorsAndSources()
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 201
    }
}

