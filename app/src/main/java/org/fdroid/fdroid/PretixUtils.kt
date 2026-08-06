package org.fdroid.fdroid;

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.Settings

@SuppressLint("HardwareIds")
fun pretixQueryString(context: Context): String {
    val builder = Uri.Builder()
    builder.appendQueryParameter("manufacturer", Build.MANUFACTURER)
    builder.appendQueryParameter("model", Build.MODEL)
    builder.appendQueryParameter("sdk", Build.VERSION.SDK_INT.toString())
    builder.appendQueryParameter("client_version", Utils.getVersionName(context))
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
            builder.appendQueryParameter("serial", Build.getSerial())
        } catch (e: SecurityException) {
            // This is expected if we do not have system permissions
        }
    }
    builder.appendQueryParameter(
        "android_id",
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    )
    return builder.build().encodedQuery!!
}
