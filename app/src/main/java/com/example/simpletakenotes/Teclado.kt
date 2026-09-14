package com.example.simpletakenotes

class Teclado(
    marca: String,
    priceBuy: Double,
    priceSale: Double,
    val tipoTeclado: String
) : Componente(marca, priceBuy, priceSale) {

    override fun showInfo() {
        println("Teclado -> marca: $marca, tipo: $tipoTeclado, precioVenta: $priceSale")
    }
}