package br.com.alura.financask.extension

fun String.limitaEmAte(caractetes: Int) : String{
    if (this.length > caractetes) {
        val primeiroCaractere = 0
        return "${this.substring(primeiroCaractere, caractetes)}..."
    }
    return this
}