package uk.co.suskins

private const val space = " "

fun parse(inputString: String): String {
    val input = splitInput(inputString)

    return """
            minute ${input.parseMinutes()}
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent()
}


fun splitInput(inputString: String): Input {
    val split = inputString.split(space)

    return Input(split[0])
}

class Input(private val minute: String) {
    fun parseMinutes(): String {
        return if (minute == "*") {
            (0..59).joinToString(space)
        } else if (minute.contains("/")) {
            (0..59 step minute.split("/")[1].toInt()).joinToString(space)
        } else {
            minute
        }
    }
}