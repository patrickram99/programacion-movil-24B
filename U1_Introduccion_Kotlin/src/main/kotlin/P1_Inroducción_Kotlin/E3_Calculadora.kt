/*
Calculadora elemental:
Una calculadora básica que permite al usuario realizar operaciones de suma, resta, multiplicación, división,
potenciación y radicación. Presenta un menú con estas opciones y una opción para salir. El programa valida
que la opción seleccionada sea válida y muestra el resultado de la operación elegida.

Autor                   : Patrick Ramirez
Fecha de creacion       : Lunes 26 de agosto
Fecha de modificacion   : Lunes 26 de agosto

*/

package P1_Inroducción_Kotlin
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.exitProcess

fun main() {
    menuOfOperation()
}

// Función para mostrar las opciones de la calculadora y seleccionar alguna
fun menuOfOperation() {
    while (true) {
        println("==== Menú ====")
        println("1. Suma")
        println("2. Resta")
        println("3. Multiplicación")
        println("4. División")
        println("5. Potenciación")
        println("6. Radicación")
        println("7. Salir")
        print("Seleccione una opción: ")

        // Seleccionar operación
        when (readLine()?.toIntOrNull()) {
            1 -> performOperation("Suma") { a, b -> a + b }
            2 -> performOperation("Resta") { a, b -> a - b }
            3 -> performOperation("Multiplicación") { a, b -> a * b }
            4 -> performOperation("División") { a, b ->
                if (b != 0.0) a / b else throw IllegalArgumentException("No se puede dividir por cero")
            }
            5 -> performOperation("Potenciación") { a, b -> a.pow(b) }
            6 -> performSingleInputOperation("Radicación") { a ->
                if (a >= 0) sqrt(a) else throw IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo")
            }
            7 -> {
                println("Gracias por usar la calculadora.")
                exitProcess(0)
            }
            else -> println("Opción inválida. Por favor, seleccione una opción válida.")
        }
    }
}

// Función para realizar la operación matemática seleccionada
fun performOperation(operationName: String, operation: (Double, Double) -> Double) {
    println("Ingrese el primer número:")
    val num1 = readLine()?.toDoubleOrNull()
    println("Ingrese el segundo número:")
    val num2 = readLine()?.toDoubleOrNull()

    // Verificar validez de los números
    if (num1 != null && num2 != null) {
        try {
            val result = operation(num1, num2) // Ejecutar la operación deseada
            println("Resultado de $operationName: $result")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    } else {
        println("Entrada inválida. Por favor, ingrese números válidos.")
    }
    println()
}

// Función para realizar operaciones con un solo número (ej. radicación)
fun performSingleInputOperation(operationName: String, operation: (Double) -> Double) {
    println("Ingrese el número:")
    val num = readLine()?.toDoubleOrNull()

    // Verificar validez del número
    if (num != null) {
        try {
            val result = operation(num) // Ejecutar la operación deseada
            println("Resultado de $operationName: $result")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    } else {
        println("Entrada inválida. Por favor, ingrese un número válido.")
    }
    println()
}

