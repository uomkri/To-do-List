package ru.uomkri.simbirtest.db


import io.realm.Realm
import io.realm.kotlin.where
import ru.uomkri.simbirtest.utils.Task
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class TaskDao @Inject constructor(
    val mRealm: Realm
) {
    fun addItem(item: Task) {
        val rObject = item.toRealmObject()
        rObject.databaseId = (mRealm.where<DbTask>().max("databaseId") as Long).toInt() + 1
        mRealm.executeTransactionAsync {
            it.insert(rObject)
        }
    }

    fun addList(list: List<Task>) {
        val rList = mutableListOf<DbTask>()
        val curId = mRealm.where<DbTask>().findAllAsync().size
        var inc = 0
        for (i in list) {
            val rObject = i.toRealmObject()
            rObject.databaseId = curId + inc
            rList.add(rObject)
            inc += 1
        }
        mRealm.executeTransactionAsync {
            it.insertOrUpdate(rList)
        }
    }

    fun alreadyExists(item: Task): Boolean {
        val conflicts = mRealm.where<DbTask>()
            .beginGroup()
            .equalTo("databaseName", item.name)
            .equalTo("databaseDateStart", item.dateStart.toLong())
            .equalTo("databaseDateFinish", item.dateFinish.toLong())
            .endGroup()
            .findAll()
        return !conflicts.isNullOrEmpty()
    }

    fun getTasksForADay(day: Instant): List<Task> {
        val nextDay = day.plus(1, ChronoUnit.DAYS)
        val res = mRealm.where<DbTask>()
            .beginGroup()
            .lessThan("databaseDateFinish", nextDay.toEpochMilli())
            .greaterThanOrEqualTo("databaseDateStart", day.toEpochMilli())
            .endGroup()
            .findAll()

        return res.map {
            it.toKotlinObject()
        }
    }

    fun getTaskById(id: Int): Task {
        val res = mRealm.where<DbTask>()
            .equalTo("databaseId", id)
            .findFirst()
        return res!!.toKotlinObject()
    }

    private fun Task.toRealmObject(): DbTask = DbTask().apply {
        databaseId = 0
        databaseName = name
        databaseDescription = description
        databaseDateStart = dateStart.toLong()
        databaseDateFinish = dateFinish.toLong()
    }

    private fun DbTask.toKotlinObject(): Task = Task(
        id = databaseId!!,
        name = databaseName!!,
        description = databaseDescription!!,
        dateStart = databaseDateStart!!.toString(),
        dateFinish = databaseDateFinish!!.toString()
    )
}