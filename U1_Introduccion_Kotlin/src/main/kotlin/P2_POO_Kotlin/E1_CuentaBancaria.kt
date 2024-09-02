/*
Cuenta Bancaria:
Clase que representa a una cuenta bancaria y la operación retirar, existe un limite de retiro
y la cuenta es asociada un nuevo objeto
Autor                   : Patrick Ramirez
Fecha de creacion       : Domingo 01 de septiembre
Fecha de creacion       : Lunes 02 de septiembre

*/

package P2_POO_Kotlin

/**
 * Clase de cuenta bancaria.
 *
 * @property saldo El saldo actual de la cuenta.
 * @property limiteRetiro El límite máximo de retiro permitido.
 */
class CuentaBancaria(saldoInicial: Double, private val limiteRetiro: Double) {

    // Propiedad saldo con getter público y setter privado para encapsulamiento
    var saldo: Double = 0.0
        private set(value) {
            // Validación para asegurar que el saldo no sea negativo
            if (value >= 0) {
                field = value
            } else {
                throw IllegalArgumentException("El saldo no puede ser negativo")
            }
        }

    init {
        // Inicialización del saldo utilizando el setter para validación
        saldo = saldoInicial
    }

    /**
     * Realiza un retiro de la cuenta.
     *
     * @param cantidad La cantidad a retirar.
     * @return Boolean indicando si el retiro fue exitoso.
     * @throws IllegalArgumentException si la cantidad es negativa o cero.
     */
    fun realizarRetiro(cantidad: Double): Boolean {
        validarCantidadPositiva(cantidad)

        when {
            excedeLimiteRetiro(cantidad) -> {
                println("Error: La cantidad excede el límite de retiro")
                return false
            }
            !hayFondosSuficientes(cantidad) -> {
                println("Error: Saldo insuficiente")
                return false
            }
            else -> {
                efectuarRetiro(cantidad)
                return true
            }
        }
    }

    // Validar cantidad de retiro sea positiva
    private fun validarCantidadPositiva(cantidad: Double) {
        if (cantidad <= 0) {
            throw IllegalArgumentException("La cantidad debe ser positiva")
        }
    }

    // Verifica si la cantidad excede el limite de retiro
    private fun excedeLimiteRetiro(cantidad: Double): Boolean = cantidad > limiteRetiro

    // Verifica si hay fondos suficientes
    private fun hayFondosSuficientes(cantidad: Double): Boolean = cantidad <= saldo

    // Realiza el retiro de la cantidad definida.
    private fun efectuarRetiro(cantidad: Double) {
        saldo -= cantidad
        println("Retiro exitoso. Nuevo saldo: $saldo")
    }
}


fun main() {
    // Crear una cuenta bancaria con un saldo inicial y un límite de retiro
    val cuenta = CuentaBancaria(saldoInicial = 1000.0, limiteRetiro = 500.0)

    // Mostrar el saldo inicial
    println("Saldo inicial: ${cuenta.saldo}")

    // Intentar realizar un retiro dentro del limite y positivo
    val retiroExitoso1 = cuenta.realizarRetiro(300.0)
    println("Retiro de 300 exitoso: $retiroExitoso1")
    println("Saldo después del retiro: ${cuenta.saldo}")
}