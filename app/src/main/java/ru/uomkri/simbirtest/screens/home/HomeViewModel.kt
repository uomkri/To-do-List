package ru.uomkri.simbirtest.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
//import io.realm.Realm
import ru.uomkri.simbirtest.db.TaskDao
import ru.uomkri.simbirtest.screens.home.item.HeaderItem
import ru.uomkri.simbirtest.screens.home.item.TaskItem
//import ru.uomkri.simbirtest.db.DbTask
import ru.uomkri.simbirtest.utils.HourItem
import ru.uomkri.simbirtest.utils.Task
import ru.uomkri.simbirtest.utils.TimeFormat
import ru.uomkri.simbirtest.utils.Utils
import java.lang.reflect.ParameterizedType
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        val utils: Utils,
        moshi: Moshi,
        val dao: TaskDao
): ViewModel() {

    private val _jsonData = MutableLiveData<List<Task>>()
    val jsonData: LiveData<List<Task>>
        get() = _jsonData

    private val _selectedDayTasks = MutableLiveData<List<HourItem>>()
    val selectedDayTasks: LiveData<List<HourItem>>
        get() = _selectedDayTasks

    private val _dbData = MutableLiveData<List<Task>>()
    val dbData: LiveData<List<Task>>
        get() = _dbData


    private val type: ParameterizedType = Types.newParameterizedType(List::class.java, Task::class.java)
    private val jsonAdapter: JsonAdapter<List<Task>> = moshi.adapter(type)

    fun getJsonData() {

        val json = utils.loadJSONFromAsset()

        if (json != null) {
            val list = jsonAdapter.fromJson(json)

            _jsonData.postValue(list)

        }
    }

    fun updateRealmFromJson() {
        val new = mutableListOf<Task>()
        for (i in jsonData.value!!) {
            if (!dao.alreadyExists(i)) {
                new.add(i)
            }
        }
        if (!new.isNullOrEmpty()) {
            dao.addList(new)
        }
    }

    fun updateSelectedDayTasks(day: Instant) {
        val newList = generateEmptyDay(day)
        val tasks = dao.getTasksForADay(day)

        for (i in 0 until newList.size) {
             val hourTasks = tasks.filter {
                 it.dateStart.toLong() >= newList[i].millis && it.dateStart.toLong() < Instant.ofEpochMilli(newList[i].millis).plus(1, ChronoUnit.HOURS).toEpochMilli()
             }
            newList[i].tasks.addAll(hourTasks)
        }
        Log.e("nig", tasks.toString())
        _selectedDayTasks.postValue(newList.toList())
    }

    fun toItems(list: List<HourItem>): Map<HeaderItem, List<TaskItem>> {
        val map = mutableMapOf<HeaderItem, List<TaskItem>>()

        for (i in list) {
            val header = HeaderItem(i.hourFormatted)
            val tasks = i.tasks.map {
                return@map TaskItem(
                    id = it.id,
                    text = it.name,
                    time = utils.getStringFromTimestamp(it.dateStart, TimeFormat.TIME)
                )
            }
            map[header] = tasks
        }
        return map
    }

    private fun generateEmptyDay(day: Instant): MutableList<HourItem> {
        val list = mutableListOf<HourItem>()
        for (i in 0..23) {
            list.add(
                    HourItem(
                            hourFormatted = utils.numberToHourString(i),
                            tasks = mutableListOf(),
                            millis = day.plus(i.toLong(), ChronoUnit.HOURS).toEpochMilli()
                    )
            )
            Log.e("mil", day.plus(i.toLong(), ChronoUnit.HOURS).toEpochMilli().toString())
        }
        return list
    }


}