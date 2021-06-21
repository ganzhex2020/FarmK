package com.jony.farm.util

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import zlc.season.rxdownload4.manager.TaskManager
import zlc.season.rxdownload4.manager.manager
import zlc.season.rxdownload4.notification.SimpleNotificationCreator
import zlc.season.rxdownload4.recorder.RoomRecorder
import zlc.season.rxdownload4.task.Task

import java.io.File


fun Context.installApk(file: File) {

        val intent = Intent(Intent.ACTION_VIEW)
        val authority = "$packageName.apkdownload.provider"
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(this, authority, file)
        } else {
            Uri.fromFile(file)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
}


fun View.click(block: () -> Unit) {
    setOnClickListener {
        block()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.background(drawable: Drawable) {
    ViewCompat.setBackground(this, drawable)
}


fun String.createTaskManager(): TaskManager {
    return manager(
        notificationCreator = SimpleNotificationCreator(),
        recorder = RoomRecorder()
    )
}

fun Task.createTaskManager(): TaskManager {
    return manager(
        //    notificationCreator = SimpleNotificationCreator(),
        recorder = RoomRecorder()
    )
}