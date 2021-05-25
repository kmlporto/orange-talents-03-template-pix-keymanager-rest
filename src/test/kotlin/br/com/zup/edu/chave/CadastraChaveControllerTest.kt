package br.com.zup.edu.chave

import br.com.zup.edu.KeyManagerCadastraServiceGrpc
import br.com.zup.edu.NovaChaveResponse
import br.com.zup.edu.comum.grpc.GrpcClientFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
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
internal class CadastraChaveControllerTest(
    @Inject var grpcClientCadastro: KeyManagerCadastraServiceGrpc.KeyManagerCadastraServiceBlockingStub,
    @Inject @field:Client("/") var client: HttpClient
){

    companion object{
        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
    }

    @Test
    fun `cadastra chave com sucesso`(){
        Mockito.`when`(grpcClientCadastro.cadastra(Mockito.any())).thenReturn(responseGrpc())

        val request = HttpRequest.POST("/api/clients/${clientId}/keys", cadastraRequest())

        val response = client.toBlocking().exchange(request, CadastraRequest::class.java)

        with(response){
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("Location"))
            assertTrue(header("Location")!!.contains(clientId))
            assertTrue(header("Location")!!.contains(pixId))
        }
    }

    private fun responseGrpc(): NovaChaveResponse{
        return NovaChaveResponse.newBuilder()
            .setPixId(pixId)
            .build()
    }

    private fun cadastraRequest(): CadastraRequest{
        return CadastraRequest(
            tipoConta = TipoDeConta.CONTA_CORRENTE,
            chave = "teste@teste.com.br",
            tipoChave = TipoDeChave.EMAIL)
    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class Clients{
        @Singleton
        fun stubMock() = Mockito.mock(KeyManagerCadastraServiceGrpc.KeyManagerCadastraServiceBlockingStub::class.java)

    }
}
