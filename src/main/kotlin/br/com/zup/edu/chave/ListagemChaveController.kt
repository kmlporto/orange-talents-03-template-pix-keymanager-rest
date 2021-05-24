package br.com.zup.edu.chave

import br.com.zup.edu.KeyManagerListagemServiceGrpc
import br.com.zup.edu.ListaChaveRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*
import javax.inject.Inject

@Controller("/api/clients/{clientId}/keys")
class ListagemChaveController (@Inject val grpcClient: KeyManagerListagemServiceGrpc.KeyManagerListagemServiceBlockingStub){

    @Get
    fun consulta(clientId: UUID): HttpResponse<Any> {
        val listaChaveResponse = grpcClient.listagem(
            ListaChaveRequest
                .newBuilder()
                .setClientId(clientId.toString())
                .build()
        )

        return HttpResponse.ok(ListagemResponse(listaChaveResponse))
    }

}