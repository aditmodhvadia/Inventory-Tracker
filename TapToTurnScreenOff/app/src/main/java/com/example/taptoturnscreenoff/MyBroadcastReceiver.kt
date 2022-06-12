package com.example.taptoturnscreenoff

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager

class MyBroadcastReceiver : BroadcastReceiver() {
    private var powerManager: PowerManager? = null
    private var wakeLock: PowerManager.WakeLock? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        powerManager = context?.getSystemService(Context.POWER_SERVICE) as PowerManager?
        wakeLock = powerManager?.newWakeLock(
            PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,
            "turnscreenoff:tag"
        )
        wakeLock?.acquire(10 * 60 * 1000L /*10 minutes*/)
    }
}