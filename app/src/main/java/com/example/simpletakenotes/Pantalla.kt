package com.example.simpletakenotes

class Pantalla(
    marca: String,
    priceBuy: Double,
    priceSale: Double,
    val tamañoPulgadas: Double,
    val id: Int
) : Componente(marca, priceBuy, priceSale) {

    override fun showInfo() {
        println("Pantalla -> id: $id, marca: $marca, tamaño: $tamañoPulgadas, precioVenta: $priceSale")
    }
}