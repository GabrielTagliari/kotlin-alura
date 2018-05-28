package br.com.alura.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.TipoTransacao
import br.com.alura.financask.model.Transacao

class AlteraTransacaoDialog(
        viewGroup: ViewGroup,
        private val context: Context) : FormularioTransacaoDialog(context, viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Alterar"


    fun chama(transacao: Transacao, delegate: (transacao: Transacao) -> Unit) {
        val tipoTransacao = transacao.tipo

        super.chama(tipoTransacao, delegate)

        inicializaCampos(transacao)
    }

    override fun tituloPor(tipoTransacao: TipoTransacao): Int {
        if (tipoTransacao == TipoTransacao.RECEITA) {
            return R.string.altera_receita
        }
        return R.string.altera_despesa
    }

    private fun inicializaCampos(transacao: Transacao) {
        inicializaCampoValor(transacao)
        inicializaCampoData(transacao)
        inicializaCampoCategoria(transacao)
    }

    private fun inicializaCampoCategoria(transacao: Transacao) {
        val categoriasRetornadas = context.resources.getStringArray(categoriaPor(transacao.tipo))
        val posicaoCategoria = categoriasRetornadas.indexOf(transacao.categoria)

        categoria.setSelection(posicaoCategoria, true)
    }

    private fun inicializaCampoData(transacao: Transacao) {
        data.setText(transacao.data.formataParaBrasileiro())
    }

    private fun inicializaCampoValor(transacao: Transacao) {
        valor.setText(transacao.valor.toString())
    }

}