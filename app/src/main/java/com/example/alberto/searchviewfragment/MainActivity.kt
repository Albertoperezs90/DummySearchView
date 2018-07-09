package com.example.alberto.searchviewfragment

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //set content view AFTER ABOVE sequence (to avoid crash)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.container, MainFragment()).commit()
    }
}
