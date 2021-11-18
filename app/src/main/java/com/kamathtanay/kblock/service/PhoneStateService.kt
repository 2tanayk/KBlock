package com.kamathtanay.kblock.service


import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telecom.TelecomManager
import android.telephony.PhoneStateListener
import android.telephony.PhoneStateListener.LISTEN_CALL_STATE
import android.telephony.TelephonyManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import com.kamathtanay.kblock.ConstantsKBlock.CHANNEL_ID
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.db.entity.CallLog
import com.kamathtanay.kblock.data.db.entity.Contact
import com.kamathtanay.kblock.data.repository.BlockedRepository
import com.kamathtanay.kblock.data.repository.CallLogRepository
import com.kamathtanay.kblock.ui.mainscreen.MainActivity
import java.text.SimpleDateFormat
import java.util.*


class PhoneStateService : Service() {
    private lateinit var db: AppDatabase
    private lateinit var blockedRepository:BlockedRepository
    private lateinit var callLogRepository: CallLogRepository
    private lateinit var blockedContactsObserver:Observer<List<Contact>>

    private lateinit var telephonyManager: TelephonyManager
    private lateinit var telecomManager:TelecomManager
    private lateinit var phoneStateListener: PhoneStateListener
    private lateinit var blockedContactList:List<Contact>


    override fun onCreate() {
        super.onCreate()
        db=AppDatabase(applicationContext)
        blockedRepository=BlockedRepository(db)
        callLogRepository= CallLogRepository(db)

        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager

        blockedContactsObserver = Observer<List<Contact>> { blockedList ->
            Log.e("Blocked", "list updated!")
            blockedContactList = blockedList
        }
        blockedRepository.getAllBlockedContacts().observeForever(blockedContactsObserver)

    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("PhoneStateService", "onStartCommand")

        phoneStateListener = object : PhoneStateListener() {
            @SuppressLint("MissingPermission")
            override fun onCallStateChanged(state: Int, phoneNumber: String?) {
                super.onCallStateChanged(state, phoneNumber)
                Log.e("Call from", "$phoneNumber")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    for (contact in blockedContactList) {
                        Log.e("Contact in list", contact.contactPhoneNumber)
                        if (contact.contactPhoneNumber.equals(phoneNumber)) {
                            Log.e("No.$phoneNumber in list", "ending call")
                            telecomManager.endCall()
                            informUserWithNotification(contact.contactName)
                            saveToBlockedCallLogs(contact.contactName, phoneNumber)
                            break
                        }
                    }
                }
            }
            }

        telephonyManager.listen(phoneStateListener, LISTEN_CALL_STATE)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("KBlock")
            .setContentText("Call Blocking Service is On")
            .setSmallIcon(R.drawable.ic_disconnect)
            .setContentIntent(pendingIntent)
            .build()

        startForeground((System.currentTimeMillis()%10000).toInt(), notification)

        return START_STICKY
    }

    private fun informUserWithNotification(contactName: String) {
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_disconnect)
            .setContentTitle("KBlock")
            .setContentText("Call from $contactName Disconnected")
            .setAutoCancel(true)

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify((System.currentTimeMillis()%10000).toInt(), mBuilder.build())
    }

    private fun saveToBlockedCallLogs(contactName: String, phoneNumber: String) {
        val currentTime: String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        callLogRepository.insertNewCallLog(CallLog(logName = contactName, logPhoneNumber = phoneNumber,logTime =currentTime ))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::blockedContactsObserver.isInitialized) {
            blockedRepository.getAllBlockedContacts().removeObserver(blockedContactsObserver)
        }
    }
}