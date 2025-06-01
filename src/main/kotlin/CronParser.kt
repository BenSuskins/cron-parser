package uk.co.suskins

private const val space = " "

fun parse(inputString: String): String {
    val input = splitInput(inputString)

    return """
            minute ${input.parseMinutes()}
            hour ${input.parseHours()}
            day of month ${input.parseDays()}
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent()
}


fun splitInput(inputString: String): Input {
    val split = inputString.split(space)

    return Input(split[0], split[1], split[2])
}

class Input(
    private val minute: String,
    private val hour: String,
    private val day: String
) {
    fun parseMinutes(): String {
        return if (minute == "*") {
            (0..59).joinToString(space)
        } else if (minute.contains("/")) {
            (0..59 step minute.split("/")[1].toInt()).joinToString(space)
        } else if (minute.contains("-")) {
            val split = minute.split("-")
            (split[0].toInt()..split[1].toInt()).joinToString(space)
        } else if (minute.contains(",")) {
            val split = minute.split(",")
            listOf(split[0], split[1]).joinToString(space)
        } else {
            minute
        }
    }

    fun parseHours(): String {
        return if (hour == "*") {
            (0..23).joinToString(space)
        } else if (hour.contains("/")) {
            (0..23 step hour.split("/")[1].toInt()).joinToString(space)
        } else if (hour.contains("-")) {
            val split = hour.split("-")
            (split[0].toInt()..split[1].toInt()).joinToString(space)
        } else if (hour.contains(",")) {
            val split = hour.split(",")
            listOf(split[0], split[1]).joinToString(space)
        } else {
            hour
        }
    }

    fun parseDays(): String {
        return if (day == "*") {
            (1..31).joinToString(space)
        } else if (day.contains("/")) {
            (1..31 step day.split("/")[1].toInt()).joinToString(space)
        } else if (day.contains("-")) {
            val split = day.split("-")
            (split[0].toInt()..split[1].toInt()).joinToString(space)
        } else if (day.contains(",")) {
            val split = day.split(",")
            listOf(split[0], split[1]).joinToString(space)
        } else {
            day
        }
    }
}