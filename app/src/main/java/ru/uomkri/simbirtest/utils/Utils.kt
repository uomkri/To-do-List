package ru.uomkri.simbirtest.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Utils @Inject constructor(private val context: Context) {

    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open("data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return json
    }

    fun getStringFromTimestamp(ts: String, format: TimeFormat): String {
        val instant = Instant.ofEpochMilli(ts.toLong())
        val z = ZoneId.of("GMT")
        val zdt = instant.atZone(z)

        return when (format) {
            TimeFormat.TIME -> zdt.format(DateTimeFormatter.ofPattern("HH:mm"))
            TimeFormat.DATETIME -> zdt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            TimeFormat.DATE -> zdt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        }
    }

    fun numberToHourString(n: Int): String {
        var res = ""
        when {
            n < 10 -> res = "0$n:00"
            n >= 10 -> res = "$n:00"
        }
        return res
    }

    fun toMillis(dt: RawDate): Long {
        return Instant.now().atZone(ZoneId.of("GMT"))
            .withYear(dt.year)
            .withMonth(dt.month)
            .withDayOfMonth(dt.day)
            .withHour(dt.hour!!)
            .withMinute(dt.minute!!)
            .withSecond(0)
            .toInstant()
            .toEpochMilli()
    }
}

enum class TimeFormat {
    TIME, DATETIME, DATE
}

