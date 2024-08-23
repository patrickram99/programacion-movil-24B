package org.ulasalle.P1_Inroducción_Kotlin

fun main() {

    println("Ingrese la puntuación del empleado: ")
    val points: Int? = readln().toIntOrNull()
    if (points == null || points < 0 || points > 10) {
        println("Valor inválido para la puntuación del empleado, esta debe ser un número entero entre 0 y 10")
    }

    println("Ingrese el salario del empleado: ")
    var salary: Double? = readln().toDoubleOrNull()
    if (salary == null || salary < 0) {
        println("Valor inválido para el salario del empleado, esta debe ser un número positivo")
    }
    salary = salary!! * points!! / 10

    when (points) {
        in 0 .. 3 -> println("Nivel de Rendimiento Inaceptable, Cantidad de Dinero Recibido $${salary}")
        in 4 .. 6 -> println("Nivel de Rendimiento Aceptable, Cantidad de Dinero Recibido $${salary}")
        else -> println("Nivel de Rendimiento Meritorio, Cantidad de Dinero Recibido $${salary}")
    }
}