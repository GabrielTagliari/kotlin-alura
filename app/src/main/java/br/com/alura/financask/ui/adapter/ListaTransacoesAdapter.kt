package br.com.alura.financask.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.alura.financask.R
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.extension.limitaEmAte
import br.com.alura.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val context: Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    @SuppressLint("ViewHolder")
    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada: View = LayoutInflater.from(context)
                .inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[posicao]

        adicionaValor(viewCriada, transacao)
        adicionaIcone(viewCriada, transacao)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaData(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcone(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_icone.setBackgroundResource(transacao.icone)
    }

    private fun adicionaValor(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_valor.setTextColor(ContextCompat.getColor(context, transacao.cor))
        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro()
    }

    override fun getItem(posicao: Int): Transacao {
        return transacoes[posicao]
    }

    override fun getItemId(posicao: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }

}