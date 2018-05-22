package br.com.alura.financask.model

import br.com.alura.financask.R
import java.math.BigDecimal
import java.util.Calendar

class TransacaoReceita(valor: BigDecimal,
                       categoria: String = "Indefinida",
                       data: Calendar = Calendar.getInstance(),
                       cor: Int = R.color.receita,
                       icone: Int = R.drawable.icone_transacao_item_receita,
                       tipo: TipoTransacao = TipoTransacao.RECEITA)
    : Transacao(valor, categoria, data, cor, icone, tipo)