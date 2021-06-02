package br.com.zup.edu.chave

import br.com.zup.edu.*
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListagemChaveControllerTest(){

    @Inject lateinit var grpcClientListagem: KeyManagerListagemServiceGrpc.KeyManagerListagemServiceBlockingStub

    @Inject @field:Client("/") lateinit var client: HttpClient


    companion object{
        val clientId = UUID.randomUUID().toString()
        val chave1 = UUID.randomUUID().toString()
    }

    @Test
    fun `lista chaves com sucesso`(){
        Mockito.`when`(grpcClientListagem.listagem(requestGrpc())).thenReturn(responseGrpc())

        val request = HttpRequest.GET<ListagemResponse>("/api/clients/${clientId}/keys")

        val response = client.toBlocking().exchange(request, ListagemResponse::class.java)
        with(response){
            assertNotNull(body())
            assertEquals(2, body().chaves.size)
            assertEquals(chave1, body().chaves.get(0).valorChave)
            assertEquals("teste@teste.com.br", body().chaves.get(1).valorChave)
        }
    }

    @Test
    fun `lista chaves com sucesso com lista vazia`(){
        Mockito.`when`(grpcClientListagem.listagem(requestGrpc())).thenReturn(ListaChaveResponse.newBuilder().build())

        val request = HttpRequest.GET<ListagemResponse>("/api/clients/${clientId}/keys")

        val response = client.toBlocking().exchange(request, ListagemResponse::class.java)
        with(response){
            assertNotNull(body())
            assertEquals(0, body().chaves.size)
        }
    }

    private fun requestGrpc(): ListaChaveRequest{
        return ListaChaveRequest
            .newBuilder()
            .setClientId(clientId)
            .build()
    }

    private fun responseGrpc(): ListaChaveResponse {
        return ListaChaveResponse
            .newBuilder()
            .addChaves(DetalheChaveResponse
                            .newBuilder()
                            .setClientId(clientId)
                            .setPixId(UUID.randomUUID().toString())
                            .setValorChave(chave1)
                            .setTipoChave(TipoChave.ALEATORIA)
                            .setTipoConta(TipoConta.CONTA_POUPANCA)
                            .setCriadoEm(Timestamp.getDefaultInstance())
                            .build())
            .addChaves(DetalheChaveResponse
                            .newBuilder()
                            .setClientId(clientId)
                            .setPixId(UUID.randomUUID().toString())
                            .setValorChave("teste@teste.com.br")
                            .setTipoChave(TipoChave.EMAIL)
                            .setTipoConta(TipoConta.CONTA_POUPANCA)
                            .setCriadoEm(Timestamp.getDefaultInstance())
                            .build())
            .build()
    }
    @Replaces(KeyManagerListagemServiceGrpc.KeyManagerListagemServiceBlockingStub::class)
    @Singleton
    fun stubMock() = Mockito.mock(KeyManagerListagemServiceGrpc.KeyManagerListagemServiceBlockingStub::class.java)

}