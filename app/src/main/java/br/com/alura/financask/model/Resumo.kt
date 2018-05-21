package br.com.alura.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    val receita get() = somaPor(TransacaoReceita::class)

    val despesa get() = somaPor(TransacaoDespesa::class)

    val total get() = receita.subtract(despesa)

    private fun somaPor(tipoTransacao: Any?): BigDecimal {
        val somaTransacoesPorTipo: Double = transacoes
                .filter {it::class == tipoTransacao}
                .sumByDouble {it.valor.toDouble()}
        return BigDecimal(somaTransacoesPorTipo)
    }
}