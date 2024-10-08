/*
Evaluación Empleados:
Un programa que evalúa el rendimiento de los empleados en una empresa cada seis meses. Según la puntuación obtenida
(de 0 a 10), se clasifica en niveles de rendimiento: "Inaceptable", "Aceptable" o "Meritorio". Además, calcula la
cantidad de dinero que el empleado recibirá basándose en su salario mensual y su puntuación.

Autor                   : Patrick Ramirez
Fecha de creacion       : Viernes 23 de agosto
Fecha de modificacion   : Lunes 26 de agosto

*/

package org.ulasalle.P1_Inroducción_Kotlin

fun main() {
    val points = getValidPoints()
    val salary = getValidSalary()

    val bonus = calculateBonus(points, salary)
    val performanceLevel = getPerformanceLevel(points)

    println("Nivel de Rendimiento $performanceLevel, Cantidad de Dinero Recibido en soles $${String.format("%.2f", bonus)}")
    println("Nivel de Rendimiento $performanceLevel, Cantidad de Dinero Recibido en dolares $${String.format("%.2f", bonus * 0.27)}")

}

// Funcion para obtener la puntuacion del empleado
fun getValidPoints(): Int {
    while (true) {
        println("Ingrese la puntuación del empleado (0-10): ")
        val input = readLine()
        try {
            val points = input?.toIntOrNull()
            when {
                points == null -> println("Error: Por favor, ingrese un número entero válido.")
                points !in 0..10 -> println("Error: La puntuación debe estar entre 0 y 10.")
                else -> return points
            }
        } catch (e: Exception) {
            println("Error: Se produjo un error inesperado. Por favor, intente de nuevo.")
        }
    }
}

// Funcion para obtener el salario del empleado
fun getValidSalary(): Double {
    while (true) {
        println("Ingrese el salario del empleado: ")
        val input = readLine()
        try {
            val salary = input?.toDoubleOrNull()
            when {
                salary == null -> println("Error: Por favor, ingrese un número válido para el salario.")
                salary < 0 -> println("Error: El salario debe ser un número positivo.")
                else -> return salary
            }
        } catch (e: Exception) {
            println("Error: Se produjo un error inesperado. Por favor, intente de nuevo.")
        }
    }
}

// Funcion para calcular la cantidad de dineroq ue recibira el empleado
fun calculateBonus(points: Int, salary: Double): Double {
    return salary * points / 10
}

// Funcion para calcular el nivel de performance del empleado
fun getPerformanceLevel(points: Int): String {
    return when (points) {
        in 0..3 -> "Inaceptable"
        in 4..6 -> "Aceptable"
        else -> "Meritorio"
    }
}