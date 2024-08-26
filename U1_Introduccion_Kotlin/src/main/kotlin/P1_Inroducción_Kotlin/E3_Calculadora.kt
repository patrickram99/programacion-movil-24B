package P1_Inroducción_Kotlin
import kotlin.system.exitProcess

fun main() {
    menuOfOperation()
}

// Funcion para mostrar las opciones de la calculadora y seleccionar alguna
fun menuOfOperation() {
    while (true) {
        println("==== Menú ====")
        println("1. Suma")
        println("2. Resta")
        println("3. Multiplicación")
        println("4. División")
        println("5. Salir")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> performOperation("Suma") { a, b -> a + b }
            2 -> performOperation("Resta") { a, b -> a - b }
            3 -> performOperation("Multiplicación") { a, b -> a * b }
            4 -> performOperation("División") { a, b ->
                if (b != 0.0) a / b else throw IllegalArgumentException("No se puede dividir por cero")
            }
            5 -> {
                println("Gracias por usar la calculadora. ¡Hasta luego!")
                exitProcess(0)
            }
            else -> println("Opción inválida. Por favor, seleccione una opción válida.")
        }
    }
}

// Funcion para realizar la operacion matematica seleccionada
fun performOperation(operationName: String, operation: (Double, Double) -> Double) {
    println("Ingrese el primer número:")
    val num1 = readLine()?.toDoubleOrNull()
    println("Ingrese el segundo número:")
    val num2 = readLine()?.toDoubleOrNull()

    if (num1 != null && num2 != null) {
        try {
            val result = operation(num1, num2)
            println("Resultado de $operationName: $result")
        } catch (e: IllegalArgumentException) {
            println(e.message)
        }
    } else {
        println("Entrada inválida. Por favor, ingrese números válidos.")
    }
    println()
}

