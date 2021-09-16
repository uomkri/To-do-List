package ru.uomkri.simbirtest.screens.newtask

import android.util.Log
import androidx.lifecycle.ViewModel
import ru.uomkri.simbirtest.db.TaskDao
import ru.uomkri.simbirtest.utils.RawDate
import ru.uomkri.simbirtest.utils.Task
import ru.uomkri.simbirtest.utils.Utils
import javax.inject.Inject

class NewTaskViewModel @Inject constructor(
    val utils: Utils,
    val dao: TaskDao
): ViewModel() {

    fun apply(name: String, description: String, datetime: RawDate) {
        val millis = utils.toMillis(datetime)
        Log.e("TML", millis.toString())

        val task = Task(
            id = 0,
            name = name,
            description = description,
            dateStart = millis.toString(),
            dateFinish = "0"
        )

        Log.e("task", task.toString())

        dao.addItem(task)
    }

}