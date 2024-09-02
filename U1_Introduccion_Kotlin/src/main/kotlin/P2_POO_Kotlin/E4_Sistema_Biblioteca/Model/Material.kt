package P2_POO_Kotlin.E4_Sistema_Biblioteca.Model

// Clase base abstracta Material
abstract class Material(
    val titulo: String,
    val autor: String,
    val anioPublicacion: Int
) {
    abstract fun mostrarDetalles(): String
}