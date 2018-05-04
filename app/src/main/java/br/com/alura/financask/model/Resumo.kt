package br.com.alura.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    fun receita(): BigDecimal {
        var valorReceita = BigDecimal.ZERO

        for (transacao in transacoes) {
            if (transacao is TransacaoReceita) {
                valorReceita = valorReceita.plus(transacao.valor)
            }
        }

        return valorReceita
    }

    fun despesa(): BigDecimal {
        var valorDespesa = BigDecimal.ZERO

        for (transacao in transacoes) {
            if (transacao is TransacaoDespesa) {
                valorDespesa = valorDespesa.plus(transacao.valor)
            }
        }

        return valorDespesa
    }

    fun total(): BigDecimal {
        return receita().subtract(despesa())
    }
}