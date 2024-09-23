package com.example.foundbook

import android.app.Application
import com.example.foundbook.data.AppContainer
import com.example.foundbook.data.AppDataContainer

class FoundBookApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}