/*
Adivina Número:
Un juego en el que el programa genera un número aleatorio entre 1 y 30. El usuario debe adivinar el número,
recibiendo pistas de si el número es mayor o menor que el ingresado. El usuario tiene 5 intentos para adivinar
correctamente antes de que el juego termine. Si adivina correctamente, se muestra un mensaje de felicitación;
si no, se muestra "Perdiste :(".


Autor                   : Patrick Ramirez
Fecha de creacion       : Lunes 26 de agosto
Fecha de modificacion   : Lunes 26 de agosto

*/

package P1_Inroducción_Kotlin

import kotlin.random.Random

// Funcion para el juego adivinar el numero
fun adivinaNumero() {
    // Generar un número aleatorio entre 1 y 30
    val numeroSecreto = Random.nextInt(1, 31)
    var intentosRestantes = 5

    println("¡Bienvenido a Adivina Número!")
    println("He pensado en un número entre 1 y 30. Tienes 5 intentos para adivinarlo.")

    // Loop para verificar los intentos disponibles
    while (intentosRestantes > 0) {
        print("Ingresa tu intento (intentos restantes: $intentosRestantes): ")
        val intento = readLine()?.toIntOrNull()

        if (intento == null) {
            println("Por favor, ingresa un número válido.")
            continue
        }

        // Logica para comparar numeros y dar pista correspondiente
        when {
            intento == numeroSecreto -> {
                println("¡OMG GANASTE! Has adivinado el número correcto: $numeroSecreto")
                return
            }
            intento < numeroSecreto -> println("El número es mayor que $intento")
            else -> println("El número es menor que $intento")
        }

        intentosRestantes--
    }

    println("Perdiste :(\n El número secreto era $numeroSecreto")
}

fun main() {
    adivinaNumero()
}