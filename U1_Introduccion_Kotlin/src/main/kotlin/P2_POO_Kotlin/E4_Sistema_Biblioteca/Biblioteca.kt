/*
Biblioteca:
Una implementacion ligera de un sistema de biblioteca con usuarios, libros y revistas, permite
ver prestamos y usuarioos actuales

Autor                   : Patrick Ramirez
Fecha de creacion       : Domingo 01 de septiembre
Fecha de creacion       : Lunes 02 de septiembre

*/

package P2_POO_Kotlin.E4_Sistema_Biblioteca

import P2_POO_Kotlin.E4_Sistema_Biblioteca.Model.Libro
import P2_POO_Kotlin.E4_Sistema_Biblioteca.Model.Material
import P2_POO_Kotlin.E4_Sistema_Biblioteca.Model.Revista

// Data class Usuario
data class Usuario(val nombre: String, val apellido: String, val edad: Int)

// Interfaz IBiblioteca
interface IBiblioteca {
    fun registrarMaterial(material: Material)
    fun registrarUsuario(usuario: Usuario)
    fun prestamo(usuario: Usuario, material: Material)
    fun devolucion(usuario: Usuario, material: Material)
    fun mostrarMaterialesDisponibles()
    fun mostrarMaterialesReservadosPorUsuario(usuario: Usuario)
}

// Clase Biblioteca
class Biblioteca : IBiblioteca {
    private val materialesDisponibles = mutableListOf<Material>()
    private val usuariosPrestamos = mutableMapOf<Usuario, MutableList<Material>>()

    override fun registrarMaterial(material: Material) {
        materialesDisponibles.add(material)
        println("Material registrado: ${material.mostrarDetalles()}")
    }

    override fun registrarUsuario(usuario: Usuario) {
        if (!usuariosPrestamos.containsKey(usuario)) {
            usuariosPrestamos[usuario] = mutableListOf()
            println("Usuario registrado: $usuario")
        } else {
            println("El usuario ya está registrado")
        }
    }

    override fun prestamo(usuario: Usuario, material: Material) {
        if (material in materialesDisponibles) {
            materialesDisponibles.remove(material)
            usuariosPrestamos.getOrPut(usuario) { mutableListOf() }.add(material)
            println("Préstamo realizado: ${material.titulo} a ${usuario.nombre}")
        } else {
            println("El material no está disponible")
        }
    }

    override fun devolucion(usuario: Usuario, material: Material) {
        val materialesUsuario = usuariosPrestamos[usuario]
        if (materialesUsuario != null && material in materialesUsuario) {
            materialesUsuario.remove(material)
            materialesDisponibles.add(material)
            println("Devolución realizada: ${material.titulo} por ${usuario.nombre}")
        } else {
            println("El usuario no tiene este material en préstamo")
        }
    }

    override fun mostrarMaterialesDisponibles() {
        println("Materiales disponibles:")
        materialesDisponibles.forEach { println(it.mostrarDetalles()) }
    }

    override fun mostrarMaterialesReservadosPorUsuario(usuario: Usuario) {
        println("Materiales reservados por ${usuario.nombre}:")
        usuariosPrestamos[usuario]?.forEach { println(it.mostrarDetalles()) }
    }
}

fun main() {
    val biblioteca = Biblioteca()

    // Crear materiales
    val libro1 = Libro("1984", "George Orwell", 1949, "Ficción distópica", 328)
    val libro2 = Libro("Cien años de soledad", "Gabriel García Márquez", 1967, "Realismo mágico", 432)
    val revista1 = Revista("National Geographic", "Varios", 2023, "0027-9358", 243, 6, "National Geographic Society")

    // Registrar materiales
    biblioteca.registrarMaterial(libro1)
    biblioteca.registrarMaterial(libro2)
    biblioteca.registrarMaterial(revista1)

    // Crear usuarios
    val usuario1 = Usuario("Juan", "Pérez", 30)
    val usuario2 = Usuario("María", "García", 25)

    // Registrar usuarios
    biblioteca.registrarUsuario(usuario1)
    biblioteca.registrarUsuario(usuario2)

    // Realizar préstamos
    biblioteca.prestamo(usuario1, libro1)
    biblioteca.prestamo(usuario2, revista1)

    // Mostrar materiales disponibles
    biblioteca.mostrarMaterialesDisponibles()

    // Mostrar materiales reservados por usuario
    biblioteca.mostrarMaterialesReservadosPorUsuario(usuario1)
    biblioteca.mostrarMaterialesReservadosPorUsuario(usuario2)

    // Realizar devolución
    biblioteca.devolucion(usuario1, libro1)

    // Mostrar materiales disponibles después de la devolución
    biblioteca.mostrarMaterialesDisponibles()
}