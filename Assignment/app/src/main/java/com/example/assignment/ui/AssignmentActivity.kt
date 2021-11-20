package com.example.assignment.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R

class AssignmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFragment()
    }


    private fun initFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frmContainer, AssignmentFragment())
        fragmentTransaction.commit()
    }
}