package br.com.alura.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import br.com.alura.financask.R
import br.com.alura.financask.dao.TransacaoDAO
import br.com.alura.financask.model.TipoTransacao
import br.com.alura.financask.model.Transacao
import br.com.alura.financask.ui.ResumoView
import br.com.alura.financask.ui.adapter.ListaTransacoesAdapter
import br.com.alura.financask.ui.dialog.AdicionaTransacaoDialog
import br.com.alura.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDAO()

    private val transacoes = dao.transacoes

    private val viewDaActivity by  lazy {
        window.decorView
    }

    private val viewGroupDaActivity by  lazy {
        viewDaActivity as ViewGroup
    }

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
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .chama(tipoTransacao, {
                    adiciona(it)
                    lista_transacoes_adiciona_menu.close(true)
                })
    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        with (lista_transacoes_listview) {
            adapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)
            setOnItemClickListener { _, _, posicao, id ->
                val transacao = transacoes[posicao]
                chamaDialogAlteracao(transacao, posicao)
            }
            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idMenu = item?.itemId
        if (idMenu == 1) {
            val adapterMenuInfo =
                    item.menuInfo as AdapterView.AdapterContextMenuInfo
            remove(adapterMenuInfo.position)
        }
        return super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }

    private fun chamaDialogAlteracao(transacao: Transacao, posicao: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .chama(transacao, {
                        altera(it, posicao)
                })
    }

    private fun altera(transacao: Transacao, posicao: Int) {
        dao.altera(transacao, posicao)
        atualizaTransacoes()
    }
}