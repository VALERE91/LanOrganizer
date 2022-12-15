package io.tech4fun.lanorganizer.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.widget.Toast

/** Command to the service to display a message  */
const val MSG_SAY_HELLO = 1

class MyBoundService : Service() {
    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    private lateinit var mMessenger: Messenger

    /**
     * Handler of incoming messages from clients.
     */
    internal class IncomingHandler(context: Context, private val applicationContext: Context = context.applicationContext) : Handler(Looper.myLooper()!!) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SAY_HELLO -> {
                    Thread.sleep(5_000)
                    Toast.makeText(applicationContext, "hello!", Toast.LENGTH_SHORT).show()
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Toast.makeText(applicationContext, "binding", Toast.LENGTH_SHORT).show()
        mMessenger = Messenger(IncomingHandler(this))
        return mMessenger.binder
    }
}