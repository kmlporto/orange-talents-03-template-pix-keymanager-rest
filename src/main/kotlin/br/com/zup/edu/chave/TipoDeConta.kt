package br.com.zup.edu.chave

import br.com.zup.edu.TipoConta

enum class TipoDeConta {
    CONTA_CORRENTE,
    CONTA_POUPANCA;

    fun toTipoConta(): TipoConta {
        return TipoConta.valueOf(name)
    }

    companion object{
        fun convert(tipoConta: TipoConta): TipoDeConta{
            return valueOf(tipoConta.name)
        }
    }

}