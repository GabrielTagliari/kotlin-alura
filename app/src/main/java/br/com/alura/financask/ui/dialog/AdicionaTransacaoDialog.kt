package br.com.alura.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import br.com.alura.financask.R
import br.com.alura.financask.delegate.TransacaoDelegate
import br.com.alura.financask.extension.converteParaCalendar
import br.com.alura.financask.extension.formataParaBrasileiro
import br.com.alura.financask.model.TipoTransacao
import br.com.alura.financask.model.TransacaoDespesa
import br.com.alura.financask.model.TransacaoReceita
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val viewGroup: ViewGroup,
                              private val context: Context) {

    private val viewCriada = criaLayout()
    private val valor = viewCriada.form_transacao_valor
    private val data = viewCriada.form_transacao_data
    private val categoria = viewCriada.form_transacao_categoria

    fun chama(tipoTransacao: TipoTransacao, transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria(tipoTransacao)
        configuraFormulario(tipoTransacao, transacaoDelegate)
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context).inflate(R.layout.form_transacao, viewGroup, false)
    }

    private fun configuraFormulario(tipoTransacao: TipoTransacao, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipoTransacao)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton("Adicionar",
                        { _, _ ->
                            val valorTexto = valor.text.toString()
                            val dataTexto = data.text.toString()
                            val categoriaTexto = categoria.selectedItem.toString()

                            val data = dataTexto.converteParaCalendar()

                            if (tipoTransacao == TipoTransacao.RECEITA) {
                                val transacaoCriada = TransacaoReceita(BigDecimal(valorTexto), categoriaTexto, data)
                                transacaoDelegate.delegate(transacaoCriada)
                            } else {
                                val transacaoCriada = TransacaoDespesa(BigDecimal(valorTexto), categoriaTexto, data)
                                transacaoDelegate.delegate(transacaoCriada)
                            }
                        })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun tituloPor(tipoTransacao: TipoTransacao): Int {
        if (tipoTransacao == TipoTransacao.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }

    private fun categoriaPor(tipoTransacao: TipoTransacao): Int {
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