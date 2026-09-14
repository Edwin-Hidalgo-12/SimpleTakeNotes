package com.example.simpletakenotes

open class Componente(
    val marca: String,
    protected val priceBuy: Double,
    val priceSale: Double
) {
    open fun showInfo() {
        println("$marca $priceBuy $priceSale")
    }
}