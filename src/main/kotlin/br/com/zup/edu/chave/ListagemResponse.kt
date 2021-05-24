package br.com.zup.edu.chave

import br.com.zup.edu.DetalheChaveResponse
import br.com.zup.edu.ListaChaveResponse
import br.com.zup.edu.comum.grpc.toLocalDate
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class ListagemResponse (
    @JsonInclude(JsonInclude.Include.ALWAYS)
    val chaves: List<DetailResponse>
){

    constructor(lista: ListaChaveResponse):
            this(
                chaves = lista.chavesList.map {
                        detalheChaveResponse -> DetailResponse(detalheChaveResponse)
                    }
            )
}

data class DetailResponse(
    val clientId: String,
    val pixId: String,
    val valorChave: String,
    val tipoChave: TipoDeChave,
    val tipoConta: TipoDeConta,
    val criadoEm: LocalDateTime
){
    constructor(detail: DetalheChaveResponse): this(
        detail.clientId,
        detail.pixId,
        detail.valorChave,
        TipoDeChave.convert(detail.tipoChave),
        TipoDeConta.convert(detail.tipoConta),
        detail.criadoEm.toLocalDate()
    )
}