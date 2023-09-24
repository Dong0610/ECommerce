package dong.duan.ecommerce.library

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


val screen_width :Int get() {
    val windowManager = AppContext.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}
val screen_height:Int get() {
    val windowManager = AppContext.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}
@Suppress("DEPRECATION")
fun no_titlebar(activity: Activity) {
    activity.requestWindowFeature(Window.FEATURE_NO_TITLE)
    activity.window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
    activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
}



fun formatTime(time: Date): String {
    try {
        val outputFormat = SimpleDateFormat("HH:mm:ss  EEEE 'ngày' dd/MM/yyyy", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Ho_Chi_Minh") // Setting the desired timezone (Vietnam)

        return outputFormat.format(time)
    } catch (e: Exception) {
        e.printStackTrace()
        return "Invalid Time"
    }
}


fun check_email(email: String): Boolean {
    val regexPattern = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    return regexPattern.matches(email)
}

fun width_percent(percent:Float):Int{
    return ((screen_width/100)*percent).toInt()
}

fun height_percent(percent: Float):Int{
    return ((screen_height/100)*percent).toInt()
}

