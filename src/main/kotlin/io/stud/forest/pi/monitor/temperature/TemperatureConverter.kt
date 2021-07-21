package io.stud.forest.pi.monitor.temperature

import io.stud.forest.reflection.constructorOf
import kotlin.reflect.KClass


/**
 * The implementation is based on the following formula:
 *
 *                   C / 5 =
 *          ( F - 32 ) / 9 =
 *                  Re / 4 =
 *      ( K - 273.15 ) / 5 =
 *     ( Ra - 491.67 ) / 9 =
 */
class TemperatureConverter {
    companion object {
        private inline fun <reified T : Temperature> rateFor(const: Float, multiplier: Int) = Pair(T::class, Rate(const, multiplier))

        val rates: Map<KClass<out Temperature>, Rate> = mapOf(
                rateFor<Celsius>(0f, 5),
                rateFor<Fahrenheit>(-32f, 9),
                rateFor<Reaumur>(0f, 4),
                rateFor<Kelvin>(-273.15f, 5),
                rateFor<Rankine>(-491.67f, 9)
        )

        inline fun <reified In : Temperature, reified Out : Temperature>
                convert(input: In): Out {
            val outConstructor = constructorOf<Out>(Float::class)

            val temperatureNotRecognized = "Temperature type not recognized, check if rates map contains it"
            val inRate = rates[input::class] ?: error(temperatureNotRecognized)
            val outRate = rates[Out::class] ?: error(temperatureNotRecognized)

            val outputValue = (((input.value + inRate.const) * outRate.multiplier) / inRate.multiplier) - outRate.const

            return outConstructor!!.call(outputValue)
        }
    }
}

class Rate(
        val const: Float,
        val multiplier: Int
)