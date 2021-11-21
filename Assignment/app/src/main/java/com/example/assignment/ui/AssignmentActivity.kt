package com.example.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.BuildConfig
import com.example.assignment.R
import timber.log.Timber

class AssignmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        turnLogsOn()
        initFragment()
    }

    private fun turnLogsOn() {
        if(BuildConfig.DEBUG){
            //turn on logs
            Timber.plant(Timber.DebugTree())
        }
    }


    private fun initFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frmContainer, AssignmentFragment())
        fragmentTransaction.commit()
    }
}