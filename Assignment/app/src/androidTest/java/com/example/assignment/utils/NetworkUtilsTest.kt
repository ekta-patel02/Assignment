package com.example.assignment.utils

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
class NetworkUtilTest: TestCase(){

    private lateinit var context: Context
    @Before
    public override fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun checkInternetConnectivity(){
        val isConnect = NetworkUtils.hasNetwork(context)
        assertThat(isConnect).isTrue()
    }
}