package P2_POO_Kotlin.E3_Figuras

/**
 * Clase que representa un rectángulo.
 *
 * @property length Longitud del rectángulo.
 * @property width Ancho del rectángulo.
 */
class Rectangle(private val length: Double, private val width: Double) : Shape() {
    override var area: Double = 0.0
    override var perimeter: Double = 0.0

    init {
        calculateArea()
        calculatePerimeter()
    }

    override fun calculateArea() {
        area = length * width
    }

    override fun calculatePerimeter() {
        perimeter = 2 * (length + width)
    }
}