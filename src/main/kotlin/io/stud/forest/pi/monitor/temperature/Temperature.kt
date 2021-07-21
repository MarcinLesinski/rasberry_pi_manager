package io.stud.forest.pi.monitor.temperature


sealed class Temperature(
        val value: Float = 0f,
        val unit: String) {
    fun toCelsius(): Celsius = TemperatureConverter.convert(this)
    fun toFahrenheit(): Fahrenheit = TemperatureConverter.convert(this)
    fun toKelvin(): Kelvin = TemperatureConverter.convert(this)
    fun toRankine(): Rankine = TemperatureConverter.convert(this)
    fun toReaumur(): Reaumur = TemperatureConverter.convert(this)

    /**
     * Convert temperature to unit given in [unitName].
     * If unit name is not recognized Celsius will be return.
     * @return [Temperature]
     * @param[unitName] name or symbol of unit. Method recognizes following values:
     * - Celsius - "Celsius", "celsius", "C", "c"
     * - Fahrenheit - "Fahrenheit", "fahrenheit", "F", "f"
     * - Kelvin - "Kelvin", "kelvin", "K", "k"
     * - Rankine - "Rankine", "rankine", "Ra", "ra"
     * - Reaumur - "Reaumur", "reaumur", "Re", "re"
     */
    fun convertTo(unitName: String): Temperature =
            when (unitName) {
                "C", "c", "Celsius", "celsius" -> this.toCelsius()
                "F", "f", "Fahrenheit", "fahrenheit" -> this.toFahrenheit()
                "K", "k", "Kelvin", "kelvin" -> this.toKelvin()
                "Ra", "ra", "Rankine", "rankine" -> this.toRankine()
                "Re", "re", "Reaumur", "reaumur" -> this.toReaumur()
                else -> this.toCelsius()
            }

    inline fun <reified T : Temperature> convertTo(): T {
        return TemperatureConverter.convert(this)
    }
}

class Celsius(temperature: Float) : Temperature(temperature, "Celsius")
class Fahrenheit(temperature: Float) : Temperature(temperature, "Fahrenheit")
class Kelvin(temperature: Float) : Temperature(temperature, "Kelvin")
class Rankine(temperature: Float) : Temperature(temperature, "Rankine")
class Reaumur(temperature: Float) : Temperature(temperature, "Reaumur")
