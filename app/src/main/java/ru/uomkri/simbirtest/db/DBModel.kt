package ru.uomkri.simbirtest.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.lang.IllegalArgumentException

open class DbTask: RealmObject() {
    @PrimaryKey
    var databaseId: Int? = null

    @Required
    var databaseName: String? = null
    var databaseDescription: String? = null
    var databaseDateStart: Long? = null
    var databaseDateFinish: Long? = null
}