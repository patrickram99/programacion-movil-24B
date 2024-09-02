package P2_POO_Kotlin.E3_Figuras

import kotlin.math.PI

/**
 * Clase que representa un círculo.
 *
 * @property radius Radio del círculo.
 */
class Circle (private val radius: Double) : Shape() {
    override var area: Double = 0.0
    override var perimeter: Double = 0.0

    init {
        calculateArea()
        calculatePerimeter()
    }

    override fun calculateArea() {
        area = PI * radius * radius
    }

    override fun calculatePerimeter() {
        perimeter = 2 * PI * radius
    }
}