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
    fun parseMinutes() = parse(0, 59, minute)

    fun parseHours() = parse(0, 23, hour)

    fun parseDayOfMonth() = parse(1, 31, dayOfMonth)

    fun parseMonth() = parse(1, 12, month)

    fun parseDayOfWeek() = parse(1, 7, dayOfWeek)

    private fun parse(min: Int, max: Int, value: String) =
        if (value == "*") {
            (min..max).joinToString(space)
        } else if (value.contains("/")) {
            (min..max step value.split("/")[1].toInt()).joinToString(space)
        } else if (value.contains("-")) {
            val split = value.split("-")
            (split[0].toInt()..split[1].toInt()).joinToString(space)
        } else if (value.contains(",")) {
            val split = value.split(",")
            listOf(split[0], split[1]).joinToString(space)
        } else {
            value
        }

    fun parseCommand() = command
}