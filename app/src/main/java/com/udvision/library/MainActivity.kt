package com.udvision.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udvision.barcode.BarcodeActivity
import android.widget.Toast
import com.udvision.login.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private val LOGIN_OK = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login()
    }

    private fun login() {
        startActivityForResult(Intent(this,LoginActivity::class.java),LOGIN_OK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            LOGIN_OK -> {
                val userDisplayName = data?.getStringExtra("KEY_USER_DISPLAYNAME")
                if(userDisplayName!= null){
                    Toast.makeText(this,"$userDisplayName is login Successfully",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,BarcodeActivity::class.java))
                }else{
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
