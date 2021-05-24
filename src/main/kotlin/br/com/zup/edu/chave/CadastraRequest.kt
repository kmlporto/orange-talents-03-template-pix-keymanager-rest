package br.com.zup.edu.chave

import br.com.zup.edu.NovaChaveRequest
import br.com.zup.edu.comum.annotation.KeyValid
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@KeyValid
data class CadastraRequest(
    @field:NotNull
    val tipoConta: TipoDeConta,
    @field:Size(max=77)
    val chave: String?,
    @field:NotNull
    val tipoChave: TipoDeChave
){
    fun paraModeloGrpc(clientId: UUID): NovaChaveRequest{
        return NovaChaveRequest.newBuilder()
            .setClientId(clientId.toString())
            .setTipoConta(tipoConta.toTipoConta())
            .setChave(chave ?: "")
            .setTipoChave(tipoChave.convert())
            .build()
    }
}
