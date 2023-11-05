package android.dongdong.kotlin_library.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.core.app.NotificationCompat
import dong.duan.ecommerce.R
import dong.duan.ecommerce.library.AppContext
import java.util.Calendar

fun show_notification(title: String = "Notification", notification: Any, icon: Int = R.mipmap.ic_launcher,noti_id:Int =0) {
    val builder = NotificationCompat.Builder(AppContext.context, "channelId")
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(notification.toString())
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if (notification.toString().length > 30) {
        val bigTextStyle = NotificationCompat.BigTextStyle()
        bigTextStyle.bigText(notification.toString())
        builder.setStyle(bigTextStyle)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "channelId"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)

        val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        builder.setChannelId(channelId)
    }

    val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(noti_id, builder.build())
}

fun <T> show_notification_toresult(rs_activity: Class<T>, title: String = "Notification", notification: Any, icon: Int = R.mipmap.ic_launcher) {
    val result_intent = Intent(AppContext.context, rs_activity)
    result_intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val pieResult= PendingIntent.getActivities(AppContext.context,
        Calendar.getInstance().timeInMillis.toInt(), arrayOf(result_intent),
        PendingIntent.FLAG_IMMUTABLE)

    val builder = NotificationCompat.Builder(AppContext.context, "channelId")
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(notification.toString())
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(icon,"Detail",pieResult)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "channelId"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)
        val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        builder.setChannelId(channelId)
    }

    val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(0, builder.build())
}
fun show_list_notification(title: String = "Notification",  icon: Int = R.mipmap.ic_launcher,big_title:String="List",message:String, vararg notification: String) {

    val conbatStyle=NotificationCompat.InboxStyle();
    conbatStyle.setBigContentTitle(big_title)
    for (s in notification){
        conbatStyle.addLine(s.toString())
    }

    val builder = NotificationCompat.Builder(AppContext.context, "channelId")
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(message)
        .setStyle(conbatStyle)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "channelId"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)

        val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        builder.setChannelId(channelId)
    }

    val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(0, builder.build())
}

fun show_image_notification(title: String = "Notification",  icon: Int = R.mipmap.ic_launcher,message:String,bitmap: Bitmap) {
    val bigImage= NotificationCompat.BigPictureStyle();
    bigImage.bigPicture(bitmap).build()
    val builder = NotificationCompat.Builder(AppContext.context, "channelId")
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setContentText(message)
        .setStyle(bigImage)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "channelId"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)

        val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        builder.setChannelId(channelId)
    }

    val notificationManager = AppContext.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(0, builder.build())
}