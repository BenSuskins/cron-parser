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
            minute         0 15 30 45
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            result
        )
    }

    @Test
    fun `can parse minutes`() {
        assertEquals(
            """
            minute         15
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("* 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         0 15 30 45
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("*/15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         0 1 2 3 4 5 6 7 8 9 10 11 12
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("0-12 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         0 12
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("0,12 0 1,15 * 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("75 1 15 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1-90 1 15 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1,90 1 15 1 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse hours`() {
        assertEquals(
            """
            minute         15
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 * 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0 12
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 */12 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0 1 2 3 4 5 6 7 8 9 10 11 12
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0-12 1,15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0 12
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0,12 1,15 * 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 63 15 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1-63 15 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1,63 15 1 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse days`() {
        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 * * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1 16 31
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 */15 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1 2 3 4 5 6 7 8 9 10 11 12
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1-12 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1 15
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1,15 * 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 1 35 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 1-35 1 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 1,35 1 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse months`() {
        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1 2 3 4 5 6 7 8 9 10 11 12
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 * 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1 7
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 */6 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1 2 3 4 5 6
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1-6 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1 6
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1,6 1-5 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 15 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1-15 1 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1,15 1 /usr/bin/find")
        }
    }

    @Test
    fun `can parse day of week`() {
        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1 2 3 4 5 6 7
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 * /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1 4 7
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 */3 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1 2 3 4 5
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1-5 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1 6
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1,6 /usr/bin/find")
        )

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1 8 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1 1-8 /usr/bin/find")
        }

        assertThrows<IllegalArgumentException> {
            parse("1 1 15 1 1,8 /usr/bin/find")
        }
    }

    @Test
    fun `can parse command`() {
        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1
            command        /usr/bin/find
            """.trimIndent(),
            parse("15 0 1 1 1 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1
            command        helloworld
            """.trimIndent(),
            parse("15 0 1 1 1 helloworld")
        )

        assertEquals(
            """
            minute         15
            hour           0
            day of month   1
            month          1
            day of week    1
            command        /hello/world
            """.trimIndent(),
            parse("15 0 1 1 1 /hello/world")
        )
    }

    @Test
    fun `can parse edge cases`() {
        assertEquals(
            """
            minute         15 16 17 18
            hour           0
            day of month   1
            month          1
            day of week    1
            command        /usr/bin/find
            """.trimIndent(),
            parse("15,16,17,18 0 1 1 1 /usr/bin/find")
        )

        assertEquals(
            """
            minute         15 16 17 18
            hour           0
            day of month   1
            month          1
            day of week    1
            command        /usr/bin/find
            """.trimIndent(),
            parse("   15,16,17,18 0 1 1 1 /usr/bin/find    ")
        )

        assertEquals(
            """
            minute         15 16 17 18
            hour           0
            day of month   1
            month          1
            day of week    1
            command        /usr/bin/find
            """.trimIndent(),
            parse("15,15,16,17,18 0 1 1 1 /usr/bin/find")
        )
    }

    @Test
    fun `Can accept a command with a arguments`() {
        assertEquals(
            """
            minute         0 15 30 45
            hour           1 2 3 4 5
            day of month   1 15
            month          2 4
            day of week    1 2 3 4 5
            command        /usr/bin/find -v foo -v bar
            """.trimIndent(),
            parse("*/15 1-5 1,15 2,4 1-5 /usr/bin/find -v foo -v bar")
        )

        assertEquals(
            """
            minute         0 15 30 45
            hour           1 2 3 4 5
            day of month   1 15
            month          2 4
            day of week    1 2 3 4 5
            command        -v foo
            """.trimIndent(),
            parse("*/15 1-5 1,15 2,4 1-5 -v foo")
        )
    }

    @Test
    fun `Can accept an optional year parameter`() {
        assertEquals(
            """
            minute         0 15 30 45
            hour           1 2 3 4 5
            day of month   1 15
            month          2 4
            day of week    1 2 3 4 5
            command        /usr/bin/find -v foo
            year           2025
            """.trimIndent(),
            parse("*/15 1-5 1,15 2,4 1-5 2025 /usr/bin/find -v foo")
        )

        assertEquals(
            """
            minute         0 15 30 45
            hour           1 2 3 4 5
            day of month   1 15
            month          2 4
            day of week    1 2 3 4 5
            command        -v foo
            year           2025 2026 2027
            """.trimIndent(),
            parse("*/15 1-5 1,15 2,4 1-5 2025-2027 -v foo")
        )

        assertEquals(
            """
            minute         0 15 30 45
            hour           1 2 3 4 5
            day of month   1 15
            month          2 4
            day of week    1 2 3 4 5
            command        -v foo
            year           2025 2026 2027 2028 2029 2030 2031 2032 2033 2034 2035 2036 2037 2038 2039 2040 2041 2042 2043 2044 2045 2046 2047 2048 2049 2050 2051 2052 2053 2054 2055 2056 2057 2058 2059 2060 2061 2062 2063 2064 2065 2066 2067 2068 2069 2070 2071 2072 2073 2074 2075 2076 2077 2078 2079 2080 2081 2082 2083 2084 2085 2086 2087 2088 2089 2090 2091 2092 2093 2094 2095 2096 2097 2098 2099 2100
            """.trimIndent(),
            parse("*/15 1-5 1,15 2,4 1-5 * -v foo")
        )

        assertEquals(
            """
            minute         0 15 30 45
            hour           1 2 3 4 5
            day of month   1 15
            month          2 4
            day of week    1 2 3 4 5
            command        -v foo
            year           2025 2075
            """.trimIndent(),
            parse("*/15 1-5 1,15 2,4 1-5 */50 -v foo")
        )
    }
}