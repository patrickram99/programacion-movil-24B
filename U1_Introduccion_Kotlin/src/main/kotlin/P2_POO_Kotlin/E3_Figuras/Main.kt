/*
Figuras:
Programa showcase para demostrar uso de clases con ejemplos de Square (cuadrado), CIrcle (circulo)
y Rectangle (rectangulo) que heredan de clase abstracta Shape (figura). Se imprimen los datos

Autor                   : Patrick Ramirez
Fecha de creacion       : Domingo 01 de septiembre
Fecha de creacion       : Lunes 02 de septiembre
*/

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
