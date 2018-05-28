package br.com.alura.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.alura.financask.R
import br.com.alura.financask.extension.converteParaCalendar
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.TipoTransacao
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.model.TransacaoDespesa
import br.com.alura.financask.model.TransacaoReceita
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
        private val context: Context,
        private val viewGroup: ViewGroup) {

    private val viewCriada = criaLayout()
    protected val valor = viewCriada.form_transacao_valor
    protected val data = viewCriada.form_transacao_data
    protected val categoria = viewCriada.form_transacao_categoria

    protected abstract val tituloBotaoPositivo: String

    fun chama(tipoTransacao: TipoTransacao, delegate: (transacao: Transacao) -> Unit) {
        configuraCampoData()
        configuraCampoCategoria(tipoTransacao)
        configuraFormulario(tipoTransacao, delegate)
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)
    }

    private fun configuraFormulario(tipoTransacao: TipoTransacao,
                                    delegate: (transacao: Transacao) -> Unit) {

        val titulo = tituloPor(tipoTransacao)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloBotaoPositivo,
                        { _, _ ->
                            val valorTexto = valor.text.toString()
                            val dataTexto = data.text.toString()
                            val categoriaTexto = categoria.selectedItem.toString()

                            val data = dataTexto.converteParaCalendar()

                            if (tipoTransacao == TipoTransacao.RECEITA) {
                                val transacaoCriada = TransacaoReceita(BigDecimal(valorTexto), categoriaTexto, data)
                                delegate(transacaoCriada)
                            } else {
                                val transacaoCriada = TransacaoDespesa(BigDecimal(valorTexto), categoriaTexto, data)
                                delegate(transacaoCriada)
                            }
                        })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    protected abstract fun tituloPor(tipoTransacao: TipoTransacao): Int

    protected fun categoriaPor(tipoTransacao: TipoTransacao): Int {
        if (tipoTransacao == TipoTransacao.RECEITA) {
            return R.array.categorias_de_receita
        }
        return R.array.categorias_de_despesa
    }

    private fun configuraCampoCategoria(tipoTransacao: TipoTransacao) {

        val categorias = categoriaPor(tipoTransacao)

        val adapter = ArrayAdapter
                .createFromResource(
                        context,
                        categorias,
                        android.R.layout.simple_spinner_dropdown_item)

        categoria.adapter = adapter
    }

    private fun configuraCampoData() {

        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        data.setText(Calendar.getInstance().formataParaBrasileiro());

        data.setOnClickListener {
            DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, ano, mes, dia ->
                        val dataSelecionada = hoje
                        dataSelecionada.set(ano, mes, dia)
                        data.setText(dataSelecionada.formataParaBrasileiro())
                    },
                    ano, mes, dia)
                    .show()
        }
    }
}