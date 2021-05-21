package me.gyanesh.hdp.util

import android.app.Activity
import android.content.*
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import android.text.format.DateUtils
import android.util.Base64
import android.util.Log
import android.util.Patterns
import me.gyanesh.hdp.HDPApp
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object {
        val suffixes: NavigableMap<Long, String> = TreeMap()

        init {
            suffixes[1_000L] = "K"
            suffixes[1_000_000L] = "M"
            suffixes[1_000_000_000L] = "G"
            suffixes[1_000_000_000_000L] = "T"
            suffixes[1_000_000_000_000_000L] = "P"
            suffixes[1_000_000_000_000_000_000L] = "E"
        }
    }

}

inline fun <reified T : Activity> Activity.startActivity(finishCurrent: Boolean = false) {
    val i = Intent(this, T::class.java)
    if (finishCurrent) {
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(i)
}

fun String?.isValidEmail() =
    !isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(this.toString()).matches()

fun String?.isValidUsername() =
    !isNullOrBlank() && (this.toString()).matches("^[a-z0-9_]*$".toRegex()) && (this.toString().length in 6..30)

fun String?.toUtcTimeStamp(): Long {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        formatter.parse(this!!)?.time!!
    } catch (e: Exception) {
        e.printStackTrace()
        Long.MIN_VALUE
    }
}

fun toElapsedTime(date: String?): String {
    return try {
        return DateUtils.getRelativeTimeSpanString(
            date.toUtcTimeStamp(),
            Date().time,
            0L,
            DateUtils.FORMAT_ABBREV_ALL
        ).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun dpToPx(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

fun Any?.tag() = this?.javaClass?.simpleName.toString()


//fun String.toRequestBody(): RequestBody =
//    this.toRequestBody("multipart/form-data".toMediaTypeOrNull())

fun Long.compactFormat(): String {
    //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
    if (this == Long.MIN_VALUE) return (Long.MIN_VALUE + 1).compactFormat()
    if (this < 0) return "-" + (-this).compactFormat()
    if (this < 1000) return this.toString() //deal with easy case
    val e: MutableMap.MutableEntry<Long, String> = Utils.suffixes.floorEntry(this)!!
    val divideBy = e.key
    val suffix = e.value
    val truncated = this / (divideBy / 10) //the number part of the output times 10
    val hasDecimal =
        truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
    return if (hasDecimal) (truncated / 10.0).toString() + suffix else (truncated / 10).toString() + suffix
}

fun Context.copyToClipboard(message: String) {
    val clipboard: ClipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("message", message)
    clipboard.setPrimaryClip(clip)
}

fun playStoreLink(packageName: String) =
    Uri.parse("https://play.google.com/store/apps/details?id=$packageName").toString()

fun Context.openWebsite(url: String) {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )
    } catch (e: Exception) {
        Log.v("Utils", "Error opening website")
    }
}

fun Context.openWhatsAppConversation(
    numberWithCountryCode: String,
    message: String? = null
) {
    try {
        var u = "https://api.whatsapp.com/send?phone=$numberWithCountryCode"
        if (message != null) u += "&text=$message"
        val uri = Uri.parse(u)
        val sendIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(sendIntent)
    } catch (e: java.lang.Exception) {
        Log.e("Utils : OpenWhatsApp", e.message.toString())
    }
}

fun Context.navigateToPlayStore(packageName: String) {
    val p = packageName.replace('_', '.')
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$p")
            )
        )
    } catch (e: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$p")
            )
        )
    }
}

fun Context.sendMailToNbliK(title: String, subject: String? = null) {
    val context = this
//    Intent(Intent.ACTION_SENDTO).apply {
//        type = "message/rfc822"
//        data = Uri.parse("mailto:contact.nblik@gmail.com")
//        putExtra(Intent.EXTRA_SUBJECT, subject)
//        try {
//            startActivity(Intent.createChooser(this, title))
//        } catch (e: ActivityNotFoundException) {
//            context.toastError(getString(R.string.no_mail_app))
//        }
//    }
}

fun getString(resId: Int) = HDPApp.getInstance().getString(resId)

fun Bitmap.encodeImageToBase64(): String? {
    val baos = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val b: ByteArray = baos.toByteArray()
    return Base64.encodeToString(b, Base64.NO_WRAP)
}

fun Bitmap.toBase64(): String {
    val base64 = encodeImageToBase64()
    return "data:image/jpg;base64,$base64"
}