package com.cozzal.partner.feature

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import com.cozzal.partner.R
import com.cozzal.partner.feature.owner.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed(object : Runnable {
            override fun run() {
                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }, splashInterval.toLong())
    }

    companion object {

        private const val splashInterval = 2500
    }
}
