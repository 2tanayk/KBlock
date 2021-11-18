package com.kamathtanay.kblock.util

import java.text.SimpleDateFormat
import java.util.*


fun getDateTime(){

    val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
}