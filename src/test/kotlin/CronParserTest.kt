import org.junit.jupiter.api.Test
import uk.co.suskins.parse
import kotlin.test.assertEquals

class CronParserTest {
    @Test
    fun `can parse an input`() {
        val input = "*/15 0 1,15 * 1-5 /usr/bin/find"

        val result = parse(input)

        assertEquals(
            """
            minute 0 15 30 45
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            result
        )
    }
}