package br.com.alura.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.alura.financask.R
import br.com.alura.financask.model.TipoTransacao

class AdicionaTransacaoDialog(
        viewGroup: ViewGroup,
        context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Adicionar"

    override fun tituloPor(tipoTransacao: TipoTransacao): Int {
        if (tipoTransacao == TipoTransacao.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }
}