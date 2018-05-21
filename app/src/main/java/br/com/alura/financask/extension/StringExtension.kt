package br.com.alura.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caractetes: Int) : String{
    if (this.length > caractetes) {
        val primeiroCaractere = 0
        return "${this.substring(primeiroCaractere, caractetes)}..."
    }
    return this
}

fun String.converteParaCalendar(): Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataFormatada = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataFormatada
    return data
}