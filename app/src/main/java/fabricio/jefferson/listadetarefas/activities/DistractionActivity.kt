package fabricio.jefferson.projectlogin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import fabricio.jefferson.listadetarefas.utils.DownloadWorker
import fabricio.jefferson.listadetarefas.utils.MyAsyncTask
import fabricio.jefferson.listadetarefas.R
import fabricio.jefferson.listadetarefas.utils.TaskListener
import kotlinx.android.synthetic.main.activity_distraction.*

class DistractionActivity : AppCompatActivity() {

    private lateinit var asyncTask: MyAsyncTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_distraction)

        asyncTask = MyAsyncTask(
            this,
            object : TaskListener {
                override fun onTaskComplete(result: String) {
                    textViewDistraction.text = result
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val workManager = WorkManager.getInstance()

        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val inputData = Data.Builder()
            .putString("url","https://newsapi.org/v2/everything?q=bitcoin&apiKey=d345755c3dcc426f8f5bcb86324efefe").build()

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .setConstraints(constraints).setInputData(inputData).build()

        workManager.enqueue(oneTimeWorkRequest)

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state.isFinished){
                    textViewDistraction.text = workInfo.outputData.getString("json")
                }
            })

    }
}
