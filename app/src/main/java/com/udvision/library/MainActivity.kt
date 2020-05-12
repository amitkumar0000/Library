package com.udvision.library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
                val bundleAccount = data?.getParcelableExtra<Bundle?>("KEY_ACCOUNT")
                val account = bundleAccount?.get("ACCOUNT")
                if(account!= null){
                    Toast.makeText(this,"Login Success",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
