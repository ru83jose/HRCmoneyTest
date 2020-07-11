package com.hrcmoney.hrcmoneytest.Config

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable

class IntentMethod {
    object PublicIntent {
        fun openNewTabWindow(urls: String, context: Context) {
            val uris = Uri.parse(urls)
            val intents = Intent(Intent.ACTION_VIEW, uris)
            val b = Bundle()
            b.putBoolean("new_window", true)
            intents.putExtras(b)
            context.startActivity(intents)
        }

        inline fun <reified T : Activity> Context?.startIntentActivity() {
            if (this != null) {
                val intent = Intent(this, T::class.java)
                this.startActivity(intent)
            }
        }

        inline fun <reified T : Activity> Context?.startIntentFlagActivity() {
            if (this != null) {
                val intent = Intent(this, T::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                this.startActivity(intent)
            }
        }

        inline fun <reified T : Activity> Context?.startIntentParcelableActivity(
            key: String,
            extra: Parcelable
        ) {
            if (this != null) {
                val intent = Intent(this, T::class.java)
                intent.putExtra(key, extra)
                this.startActivity(intent)
            }
        }

        inline fun <reified T : Activity> Context?.startIntentStringExtraActivity(
            key: Array<String>,
            extra: Array<String>
        ) {
            if (key.size != extra.size) return
            if (this != null) {
                val intent = Intent(this, T::class.java)
                for (i in key.indices) {
                    intent.putExtra(key[i], extra[i])
                }
                this.startActivity(intent)
            }
        }
    }
}