package com.example.project

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

// 해결 못 해써..
// 이건 pull request test 다시 다시
class MyFirebaseMessageService : FirebaseMessagingService() {

    // 해결
    override fun onNewToken(p0:String) {
        super.onNewToken(p0)
        Log.d("kkang", "fcm token..........$p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.d("kkang", "fcm message..........${p0.notification}")
        Log.d("kkang", "fcm message..........${p0.data}")
    }
}