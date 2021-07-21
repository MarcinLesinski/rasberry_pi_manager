package io.stud.forest.pi.monitor.temperature

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.data.Offset.offset
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


internal class TemperatureConverterTests {

    @ParameterizedTest
    @MethodSource("conversionTestSource")
    fun <DestinationUnit : Temperature> `should convert one unit to other one`(conversion: () -> DestinationUnit, expected: Float) {
        val actual = conversion()
        assertThat(actual.value).isEqualTo(expected, offset(0.01f))
    }

    companion object {

        @JvmStatic
        @Suppress("LocalVariableName")
        fun conversionTestSource(): Stream<Arguments> {

            val `18 C` = Celsius(18f)
            val `43 F` = Fahrenheit(43f)
            val `28,32 K` = Kelvin(28.32f)
            val `666 Ra` = Rankine(666f)
            val `5 Re` = Reaumur(5f)

            return Stream.of(
                    Arguments.of(`18 C`::toCelsius, 18f),
                    Arguments.of(`18 C`::toFahrenheit, 64.4f),
                    Arguments.of(`18 C`::toKelvin, 291.15f),
                    Arguments.of(`18 C`::toRankine, 524.07f),
                    Arguments.of(`18 C`::toReaumur, 14.4f),

                    Arguments.of(`43 F`::toFahrenheit, 43f),
                    Arguments.of(`43 F`::toCelsius, 6.11f),
                    Arguments.of(`43 F`::toKelvin, 279.26f),
                    Arguments.of(`43 F`::toRankine, 502.67f),
                    Arguments.of(`43 F`::toReaumur, 4.88f),


                    Arguments.of(`28,32 K`::toKelvin, 28.32f),
                    Arguments.of(`28,32 K`::toCelsius, -244.83f),
                    Arguments.of(`28,32 K`::toFahrenheit, -408.69f),
                    Arguments.of(`28,32 K`::toRankine, 50.97f),
                    Arguments.of(`28,32 K`::toReaumur, -195.86f),

                    Arguments.of(`666 Ra`::toRankine, 666f),
                    Arguments.of(`666 Ra`::toCelsius, 96.85f),
                    Arguments.of(`666 Ra`::toFahrenheit, 206.33f),
                    Arguments.of(`666 Ra`::toKelvin, 370f),
                    Arguments.of(`666 Ra`::toReaumur, 77.48f),

                    Arguments.of(`5 Re`::toReaumur, 5f),
                    Arguments.of(`5 Re`::toCelsius, 6.25f),
                    Arguments.of(`5 Re`::toFahrenheit, 43.25f),
                    Arguments.of(`5 Re`::toKelvin, 279.4f),
                    Arguments.of(`5 Re`::toRankine, 502.92f),

                    Arguments.of({ `18 C`.to<Celsius>() }, 18f),
                    Arguments.of({`18 C`.to<Fahrenheit>()}, 64.4f),
                    Arguments.of({`18 C`.to<Kelvin>()}, 291.15f),
                    Arguments.of({`18 C`.to<Rankine>()}, 524.07f),
                    Arguments.of({`18 C`.to<Reaumur>()}, 14.4f),
            )
        }

    }
}