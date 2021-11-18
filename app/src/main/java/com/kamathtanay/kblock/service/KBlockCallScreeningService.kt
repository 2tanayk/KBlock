package com.kamathtanay.kblock.service
//
//import android.os.Build
//import android.provider.CallLog
//import android.telecom.Call
//import android.telecom.Call.Details.DIRECTION_INCOMING
//import android.telecom.CallScreeningService
//import android.telephony.PhoneNumberUtils
//import androidx.annotation.RequiresApi
//import java.util.*
//
//@RequiresApi(Build.VERSION_CODES.N)
//class KBlockCallScreeningService : CallScreeningService() {
//    override fun onScreenCall(p0: Call.Details) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            if (p0.callDirection == DIRECTION_INCOMING) {
//                val callResponseBuilder:CallResponse = CallResponse.Builder()
//                    .setDisallowCall(true)
//                    .setRejectCall(true)
//                    .setSkipNotification(true)
//                    .setSkipCallLog(true)
//                    .build()
//                respondToCall(p0, callResponseBuilder)
//            }
//        }
//    }
//}