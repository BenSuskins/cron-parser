import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `can parse minutes`() {
        assertEquals(
            """
            minute 15
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("* 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 0 15 30 45
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("*/15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 0 1 2 3 4 5 6 7 8 9 10 11 12
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("0-12 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 0 12
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("0,12 0 1,15 * 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("75 1 15 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1-90 1 15 1 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse hours`() {
        assertEquals(
            """
            minute 15
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 * 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0 12
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 */12 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0 1 2 3 4 5 6 7 8 9 10 11 12
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0-12 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0 12
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0,12 1,15 * 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 63 15 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1-63 15 1 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse days`() {
        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 * * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1 16 31
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 */15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1 2 3 4 5 6 7 8 9 10 11 12
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1-12 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1 15
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 1 35 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 1-35 1 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse months`() {
        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1 2 3 4 5 6 7 8 9 10 11 12
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1 7
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 */6 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1 2 3 4 5 6
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1-6 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1 6
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1,6 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 15 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1-15 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse day of week`() {
        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1 2 3 4 5 6 7
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 * /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1 4 7
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 */3 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1 2 3 4 5
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1 6
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1,6 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1 8 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1 1-8 /usr/bin/find")
        }
    }

    @Test
    fun `can parse command`() {
        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1
            command /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1
            command hello world
            """.trimIndent(),
            parse("15 0 1 1 1 hello world")
        )

        assertEquals(
            """
            minute 15
            hour 0
            day of month 1
            month 1
            day of week 1
            command /hello/world
            """.trimIndent(),
            parse("15 0 1 1 1 /hello/world")
        )
    }

    @Test
    fun `can parse edge cases`() {
        assertEquals(
            """
            minute 15 16 17 18
            hour 0
            day of month 1
            month 1
            day of week 1
            command /usr/bin/find
            """.trimIndent(),
            parse("15,16,17,18 0 1 1 1 /usr/bin/find")
        )

        assertEquals(
            """
            minute 15 16 17 18
            hour 0
            day of month 1
            month 1
            day of week 1
            command /usr/bin/find
            """.trimIndent(),
            parse("   15,16,17,18 0 1 1 1 /usr/bin/find    ")
        )
    }

}