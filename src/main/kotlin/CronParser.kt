package uk.co.suskins

private const val space = " "

private const val minMinutes = 0
private const val maxMinutes = 59

private const val minHours = 0
private const val maxHours = 23

private const val minDayOfMonth = 1
private const val maxDayOfMonth = 31

private const val minMonth = 1
private const val maxMonth = 12

private const val minDayOfWeek = 1
private const val maxDayOfWeek = 7

fun parse(inputString: String): String {
    val input = parseInput(inputString)

    return """
            minute ${input.parseMinutes()}
            hour ${input.parseHours()}
            day of month ${input.parseDayOfMonth()}
            month ${input.parseMonth()}
            day of week ${input.parseDayOfWeek()}
            command ${input.parseCommand()}
            """.trimIndent()
}


fun parseInput(inputString: String): Input {
    val split = inputString.trim().split(space)

    return Input(split[0], split[1], split[2], split[3], split[4], split.drop(5).joinToString(space))
}

class Input(
    private val minute: String,
    private val hour: String,
    private val dayOfMonth: String,
    private val month: String,
    private val dayOfWeek: String,
    private val command: String,
) {
    fun parseMinutes() = parse(minute, minMinutes, maxMinutes)

    fun parseHours() = parse(hour, minHours, maxHours)

    fun parseDayOfMonth() = parse(dayOfMonth, minDayOfMonth, maxDayOfMonth)

    fun parseMonth() = parse(month, minMonth, maxMonth)

    fun parseDayOfWeek() = parse(dayOfWeek, minDayOfWeek, maxDayOfWeek)

    private fun parse(value: String, min: Int, max: Int) =
        when {
            value == "*" -> {
                (min..max).joinToString(space)
            }

            value.contains("/") -> {
                (min..max step value.split("/")[1].toInt()).joinToString(space)
            }

            value.contains("-") -> {
                val split = value.split("-")
                (validate(split[0], min, max)..(validate(split[1], min, max))).joinToString(space)
            }

            value.contains(",") -> {
                value.split(",").map { validate(it, min, max) }.joinToString(space)
            }

            else -> {
                validate(value, min, max)
            }
        }

    private fun validate(value: String, min: Int, max: Int): Int {
        if (value.toInt() > max || value.toInt() < min) {
            throw IllegalArgumentException("Invalid argument $value")
        }
        return value.toInt()
    }

    fun parseCommand() = command
}