package P2_POO_Kotlin

/**
 * Clase que representa un producto con precio y descuento.
 *
 * @property precio El precio base del producto.
 * @property descuento El porcentaje de descuento aplicable al producto.
 */
class Producto {
    var precio: Double = 0.0
        set(value) {
            require(value >= 0) { "El precio no puede ser negativo" }
            field = value
        }

    var descuento: Double = 0.0
        set(value) {
            require(value in 0.0..100.0) { "El descuento debe estar entre 0 y 100" }
            field = value
        }

    // Inicializar precio y desuento
    constructor(precio: Double, descuento: Double = 0.0) {
        this.precio = precio
        this.descuento = descuento
    }

    // Funcion para aplicar descuento
    fun calcularPrecioFinal(): Double {
        val factorDescuento = 1 - (descuento / 100)
        return (precio * factorDescuento).redondearADosDecimales()
    }

    // Funcion para redondear a 2 decimales
    private fun Double.redondearADosDecimales(): Double = String.format("%.2f", this).toDouble()

    // Imprimir producto con formato entendible
    override fun toString(): String {
        return "Producto(precio=$precio, descuento=$descuento%, precioFinal=${calcularPrecioFinal()})"
    }
}

// Driver code
fun main() {
    // Crear producto
    val producto = Producto(100.0, 20.0)
    println(producto)

    // Modificar producto
    producto.precio = 150.0
    producto.descuento = 10.0

    // Imprimir datos de producto
    println(producto)

    try {
        producto.precio = -50.0 // Esto lanzará una excepción de precio incorrectp
    } catch (e: IllegalArgumentException) {
        println("Error: ${e.message}")
    }

    try {
        producto.descuento = 110.0 // Esto lanzará una excepción de exceso de descuento
    } catch (e: IllegalArgumentException) {
        println("Error: ${e.message}")
    }
}
