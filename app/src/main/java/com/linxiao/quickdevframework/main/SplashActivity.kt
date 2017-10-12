package com.linxiao.quickdevframework.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.linxiao.framework.activity.BaseSplashActivity
import com.linxiao.quickdevframework.databinding.ActivitySplashBinding

class SplashActivity : BaseSplashActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        setContentView(binding.root)
        binding.apply {
            name="123"
        }
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }


}
