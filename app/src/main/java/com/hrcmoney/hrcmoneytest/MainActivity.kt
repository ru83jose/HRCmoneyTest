package com.hrcmoney.hrcmoneytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hrcmoney.hrcmoneytest.Config.IntentMethod.PublicIntent.startIntentActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        button_main_go.setOnClickListener {
            startIntentActivity<ListActivity>()
        }
    }
}