package P2_POO_Kotlin.E3_Figuras


/**
 * Clase abstracta que representa una forma
 */
abstract class Shape {
    abstract val area: Double
    abstract val perimeter: Double

    abstract fun calculateArea()
    abstract fun calculatePerimeter()

    fun printResults() {
        println("Área: $area")
        println("Perímetro: $perimeter")
    }
}


