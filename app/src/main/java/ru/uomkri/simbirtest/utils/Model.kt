package ru.uomkri.simbirtest.utils

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Task(
        val id: Int,
        @Json(name = "date_start")
        val dateStart: String,
        @Json(name = "date_finish")
        val dateFinish: String,
        val name: String,
        val description: String
)

data class HourItem(
        val hourFormatted: String,
        val millis: Long,
        val tasks: MutableList<Task> = mutableListOf()
)

data class RawDate(
        val year: Int,
        val month: Int,
        val day: Int,
        var hour: Int?,
        var minute: Int?
)