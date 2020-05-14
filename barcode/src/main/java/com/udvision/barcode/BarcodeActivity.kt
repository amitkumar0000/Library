package com.udvision.barcode

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_barcode.*


class BarcodeActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)
        initViews()
    }

    private fun initViews() {
        btnTakePicture.setOnClickListener(this)
        btnScanBarcode.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnTakePicture -> startActivity(
                Intent(
                    this,
                    PictureBarcodeActivity::class.java
                )
            )
            R.id.btnScanBarcode -> startActivity(
                Intent(
                    this,
                    ScannedBarcodeActivity::class.java
                )
            )
        }
    }
}