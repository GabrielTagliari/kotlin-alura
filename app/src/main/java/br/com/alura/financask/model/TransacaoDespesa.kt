package br.com.alura.financask.model

import br.com.alura.financask.R
import java.math.BigDecimal
import java.util.Calendar

class TransacaoDespesa(valor: BigDecimal,
                       categoria: String = "Indefinida",
                       data: Calendar = Calendar.getInstance(),
                       cor: Int = R.color.despesa,
                       icone: Int = R.drawable.icone_transacao_item_despesa)
    : Transacao(valor, categoria, data, cor, icone)