package br.com.alura.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import br.com.alura.financask.R
import br.com.alura.financask.delegate.TransacaoDelegate
import br.com.alura.financask.model.TipoTransacao
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import br.com.alura.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.alura.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        atualizaTransacoes()
    }

    fun adicionaReceita(view: View) {
        chamaDialogAdicao(TipoTransacao.RECEITA)
    }

    fun adicionaDespesa(view: View) {
        chamaDialogAdicao(TipoTransacao.DESPESA)
    }

    private fun chamaDialogAdicao(tipoTransacao: TipoTransacao) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                .chama(tipoTransacao, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        transacoes.add(transacao)
                        atualizaTransacoes()
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, window.decorView, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
        lista_transacoes_listview.setOnItemClickListener { parent, view, posicao, id ->
            val transacao = transacoes[posicao]
            AlteraTransacaoDialog(window.decorView as ViewGroup, this)
                    .chama(transacao, object : TransacaoDelegate {
                        override fun delegate(transacao: Transacao) {
                            transacoes[posicao] = transacao
                            atualizaTransacoes()
                        }
                    })
        }
    }
}