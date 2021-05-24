package br.com.zup.edu.chave

import br.com.zup.edu.ConsultaChaveResponse
import br.com.zup.edu.comum.grpc.toLocalDate
import java.time.LocalDateTime

class ConsultaResponse (
    val clientId: String,
    val pixId: String,
    val valorChave: String,
    val tipoChave: TipoDeChave,
    val titular: TitularResponse,
    val conta: ContaResponse,
    val criadoEm: LocalDateTime
    ){

    constructor(consultaChaveResponse: ConsultaChaveResponse):
            this(
                clientId = consultaChaveResponse.clientId,
                pixId = consultaChaveResponse.pixId,
                valorChave = consultaChaveResponse.valorChave,
                tipoChave = TipoDeChave.convert(consultaChaveResponse.tipoChave),
                titular = TitularResponse(consultaChaveResponse.titular),
                conta = ContaResponse(consultaChaveResponse.conta),
                criadoEm = consultaChaveResponse.criadoEm.toLocalDate()
            )

}

data class TitularResponse(
    val nome: String,
    val cpf: String
){
    constructor(titular: br.com.zup.edu.TitularResponse):
            this(
                nome = titular.nome,
                cpf = titular.cpf
            )
}


data class ContaResponse(
    val instituicao: String?,
    val agencia: String?,
    val numero: String?,
    val tipoConta: TipoDeConta?
){
    constructor(conta: br.com.zup.edu.ContaResponse):
            this(
                instituicao = conta.instituicao,
                agencia = conta.agencia,
                numero = conta.numero,
                tipoConta = TipoDeConta.convert(conta.tipoConta)
            )
}