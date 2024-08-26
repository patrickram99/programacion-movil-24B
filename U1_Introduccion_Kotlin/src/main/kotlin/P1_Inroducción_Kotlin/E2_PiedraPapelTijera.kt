/*
Piedra, Papel, Tijera:
Juego tradicional de Piedra, Papel y Tijera donde el usuario juega contra la computadora que selecciona su opcion al
azar

Autor                   : Patrick Ramirez
Fecha de creacion       : Sabado 24 de agosto
Fecha de modificacion   : Lunes 26 de agosto

*/

package p1IntroduccionKotlin

// Definimos las opciones del juego como constantes (val)
val options = mapOf(
    "Piedra" to mapOf("wins" to "Tijeras", "loses" to "Papel"),
    "Papel" to mapOf("wins" to "Piedra", "loses" to "Tijeras"),
    "Tijeras" to mapOf("wins" to "Papel", "loses" to "Piedra")
)

val values = listOf("Piedra", "Papel", "Tijeras")

fun main(args: Array<String>) {
    val computerOption = getRandomOption()
    val input = getUserInput()

    when {
        playerWins(input, computerOption) -> println("Has GANADO, jugaste $input contra $computerOption")
        playerLoses(input, computerOption) -> println("Has PERDIDO, jugaste $input contra $computerOption")
        else -> println("Es un EMPATE, ambos jugaron $input")
    }
}

// Función para obtener una opción aleatoria
fun getRandomOption(): String {
    return values.random()
}

// Función para verificar si el usuario gana
fun playerWins(playerOption: String, computerOption: String): Boolean {
    return computerOption == options[playerOption]?.get("wins")
}

// Función para verificar si el usuario pierde
fun playerLoses(playerOption: String, computerOption: String): Boolean {
    return computerOption == options[playerOption]?.get("loses")
}

// Función para solicitar y validar la entrada del usuario
fun getUserInput(): String {
    while (true) {
        println("Por favor, escribe tu jugada (Piedra, Papel o Tijeras):")
        val input = readln()

        if (input in values) {
            return input
        } else {
            println("Entrada no válida. Intenta de nuevo.")
        }
    }
}