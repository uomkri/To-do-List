package ru.uomkri.simbirtest.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.uomkri.simbirtest.db.TaskDao
import ru.uomkri.simbirtest.utils.Task
import ru.uomkri.simbirtest.utils.TimeFormat
import ru.uomkri.simbirtest.utils.Utils
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    val dao: TaskDao,
    val utils: Utils
): ViewModel() {

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task>
        get() = _task

    fun getTask(id: Int) {
        val res = dao.getTaskById(id)

        _task.postValue(res)
    }

    fun formatDatetime(ts: String): String {

        return utils.getStringFromTimestamp(ts, TimeFormat.DATETIME)
    }
}