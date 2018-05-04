package br.com.alura.financask.model

import java.math.BigDecimal
import java.util.Calendar

abstract class Transacao(val valor: BigDecimal,
                         val categoria: String,
                         val data: Calendar,
                         val cor: Int,
                         val icone: Int)