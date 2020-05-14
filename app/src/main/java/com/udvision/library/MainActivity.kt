package com.udvision.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udvision.barcode.BarcodeActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this,BarcodeActivity::class.java))
    }
}
