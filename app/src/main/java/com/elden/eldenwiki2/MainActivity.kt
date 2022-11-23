package com.elden.eldenwiki2

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    val CHANNELID = "canal"
    val notificacionID = 100


    private lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.imageView)
        image.alpha = 0f
        image.animate().setDuration(2000).alpha(1f).withEndAction {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()

            crearCanalesNotificaciones()
            enviarNotificacion()

        }


    }

    private fun crearCanalesNotificaciones() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            val nombreTitulo = "Titulo Notificación"
            val descrpcion = "Mensaje de la notificación"
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val canal = NotificationChannel(CHANNELID, nombreTitulo, importancia). apply {
                description = descrpcion
            }

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(canal)
        }
    }

    private fun enviarNotificacion() {
        val builder = NotificationCompat.Builder(this, CHANNELID)
            .setContentTitle("Elden Ring Wiki")
            .setContentText("Praise the Sun!")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_baseline_settings_24)
        with(NotificationManagerCompat.from(this)) {
            notify(notificacionID, builder.build())
        }
    }
}