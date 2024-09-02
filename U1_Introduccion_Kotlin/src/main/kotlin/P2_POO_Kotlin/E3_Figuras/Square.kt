package P2_POO_Kotlin.E3_Figuras

/**
 * Clase que representa un cuadrado.
 *
 * @property side Longitud del lado del cuadrado.
 */
class Square (private val side: Double) : Shape() {
    override var area: Double = 0.0
    override var perimeter: Double = 0.0

    init {
        calculateArea()
        calculatePerimeter()
    }

    override fun calculateArea() {
        area = side * side
    }

    override fun calculatePerimeter() {
        perimeter = 4 * side
    }
}
