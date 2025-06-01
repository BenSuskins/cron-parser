package uk.co.suskins

private const val space = " "

fun parse(inputString: String): String {
    val input = splitInput(inputString)

    return """
            minute ${input.parseMinutes()}
            hour ${input.parseHours()}
            day of month ${input.parseDayOfMonth()}
            month ${input.parseMonth()}
            day of week ${input.parseDayOfWeek()}
            command ${input.parseCommand()}
            """.trimIndent()
}


fun splitInput(inputString: String): Input {
    val split = inputString.split(space)

    return Input(split[0], split[1], split[2], split[3], split[4], split.subList(5, split.size).joinToString(space))
}

class Input(
    private val minute: String,
    private val hour: String,
    private val dayOfMonth: String,
    private val month: String,
    private val dayOfWeek: String,
    private val command: String,
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

    fun parseDayOfMonth(): String {
        return if (dayOfMonth == "*") {
            (1..31).joinToString(space)
        } else if (dayOfMonth.contains("/")) {
            (1..31 step dayOfMonth.split("/")[1].toInt()).joinToString(space)
        } else if (dayOfMonth.contains("-")) {
            val split = dayOfMonth.split("-")
            (split[0].toInt()..split[1].toInt()).joinToString(space)
        } else if (dayOfMonth.contains(",")) {
            val split = dayOfMonth.split(",")
            listOf(split[0], split[1]).joinToString(space)
        } else {
            dayOfMonth
        }
    }

    fun parseMonth(): String {
        return if (month == "*") {
            (1..12).joinToString(space)
        } else if (month.contains("/")) {
            (1..12 step month.split("/")[1].toInt()).joinToString(space)
        } else if (month.contains("-")) {
            val split = month.split("-")
            (split[0].toInt()..split[1].toInt()).joinToString(space)
        } else if (month.contains(",")) {
            val split = month.split(",")
            listOf(split[0], split[1]).joinToString(space)
        } else {
            month
        }
    }

    fun parseDayOfWeek(): String {
        return if (dayOfWeek == "*") {
            (1..7).joinToString(space)
        } else if (dayOfWeek.contains("/")) {
            (1..7 step dayOfWeek.split("/")[1].toInt()).joinToString(space)
        } else if (dayOfWeek.contains("-")) {
            val split = dayOfWeek.split("-")
            (split[0].toInt()..split[1].toInt()).joinToString(space)
        } else if (dayOfWeek.contains(",")) {
            val split = dayOfWeek.split(",")
            listOf(split[0], split[1]).joinToString(space)
        } else {
            dayOfWeek
        }
    }

    fun parseCommand(): String {
        return command
    }
}