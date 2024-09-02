package P2_POO_Kotlin.E3_Figuras


fun main() {
    val square = Square(5.0)
    val circle = Circle(3.0)
    val rectangle = Rectangle(4.0, 6.0)

    println("Cuadrado:")
    square.printResults()

    println("\nCírculo:")
    circle.printResults()

    println("\nRectángulo:")
    rectangle.printResults()
}
