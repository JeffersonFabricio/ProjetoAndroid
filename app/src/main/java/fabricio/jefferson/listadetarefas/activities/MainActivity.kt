package fabricio.jefferson.listadetarefas.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import fabricio.jefferson.listadetarefas.R
import fabricio.jefferson.listadetarefas.job.LembreteWork
import fabricio.jefferson.listadetarefas.job.RememberWork
import kotlinx.android.synthetic.main.main_activity.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        createNotification()

        btnMainRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnMainLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createNotification(){

        val myWorkBuilder: PeriodicWorkRequest.Builder = PeriodicWorkRequest.Builder(
            RememberWork::class.java, 24, TimeUnit.HOURS
        )
        val myWork: PeriodicWorkRequest = myWorkBuilder.build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "jobRemember", ExistingPeriodicWorkPolicy.KEEP, myWork
        )

    }

}
