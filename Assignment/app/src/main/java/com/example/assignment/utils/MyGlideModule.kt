package com.example.assignment.utils

import android.content.Context
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import okhttp3.OkHttpClient
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import java.io.InputStream

@GlideModule
class MyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpClient: OkHttpClient = UnsafeOkHttpClient.unsafeOkHttpClient
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
    }
}