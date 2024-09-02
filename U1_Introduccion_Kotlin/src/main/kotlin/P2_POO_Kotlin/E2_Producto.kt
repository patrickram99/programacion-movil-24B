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


