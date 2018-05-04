package br.com.alura.financask.ui

import android.view.View
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.Resumo
import br.com.alura.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*

class ResumoView(private val view: View,
                 private val transacoes: List<Transacao>) {

    private val resumo: Resumo = Resumo(transacoes)

    fun adicionaReceita () {
        view.resumo_card_receita.text = resumo.receita().formataParaBrasileiro()
    }

    fun adicionaDespesa () {
        view.resumo_card_despesa.text = resumo.despesa().formataParaBrasileiro()
    }

    fun adicionaTotal() {
        view.resumo_card_total.text = resumo.total().formataParaBrasileiro()
    }
}