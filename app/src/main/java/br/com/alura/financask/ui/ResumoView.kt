package br.com.alura.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Resumo
import br.com.alura.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val context: Context,
                 private val view: View,
                 private val transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)

    fun atualiza() {
        adicionaReceita()
        adicionaDespesa()
        adicionaTotal()
    }

    private fun adicionaReceita () {
        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = resumo.receita.formataParaBrasileiro()
        }
    }

    private fun adicionaDespesa () {
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = resumo.despesa.formataParaBrasileiro()
        }
    }

    private fun adicionaTotal() {
        with(view.resumo_card_total) {
            if (resumo.total >= BigDecimal.ZERO) {
                setTextColor(corReceita)
            } else {
                setTextColor(corDespesa)
            }
            text = resumo.total.formataParaBrasileiro()
        }
    }
}