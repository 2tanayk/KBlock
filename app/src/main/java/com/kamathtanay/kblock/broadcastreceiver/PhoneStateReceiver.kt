package com.kamathtanay.kblock.broadcastreceiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telecom.TelecomManager
import android.util.Log
import android.telephony.TelephonyManager
import android.telephony.PhoneStateListener
import com.kamathtanay.kblock.data.db.entity.Contact


class PhoneStateReceiver(val blockedList: List<Contact>) : BroadcastReceiver() {
    lateinit var phoneNumber:String
    lateinit var phoneStateListener: PhoneStateListener


    @SuppressLint("MissingPermission")
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.e("PhoneStateReceiver", "call recd.!")
        val telephonyManager = p0!!.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val telecomManager = p0.getSystemService(Context.TELECOM_SERVICE) as TelecomManager

        phoneStateListener = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                super.onCallStateChanged(state, incomingNumber)
                phoneNumber=incomingNumber
                Log.e("incomingNumber", incomingNumber)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    for(contact in blockedList){
                        Log.e("Contact in list", contact.contactPhoneNumber)
                        if (contact.contactPhoneNumber.equals(phoneNumber)){
                            Log.e("No.$phoneNumber in list","ending call")
                            telecomManager.endCall()
                            break
                        }
                    }
                }
            }
        }

//        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE)
    }
}
