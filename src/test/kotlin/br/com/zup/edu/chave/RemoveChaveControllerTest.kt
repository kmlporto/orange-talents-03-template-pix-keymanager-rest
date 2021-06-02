package br.com.zup.edu.chave

import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import br.com.zup.edu.RemoveChaveResponse
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
internal class RemoveChaveControllerTest(){

    @Inject lateinit var grpcClientRemove: KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub

    @Inject @field:Client("/") lateinit var client: HttpClient

    companion object{
        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
    }

    @Test
    fun `remove chave com sucesso`(){
        Mockito.`when`(grpcClientRemove.remove(Mockito.any())).thenReturn(RemoveChaveResponse.newBuilder()
                                                                    .setClientId(clientId)
                                                                    .setPixId(pixId)
                                                                    .build())

        val request = HttpRequest.DELETE<Any>("/api/clients/${clientId}/keys/${pixId}")

        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
    }

    @Replaces(KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub::class)
    @Singleton
    fun stubMock() = Mockito.mock(KeyManagerRemoveServiceGrpc.KeyManagerRemoveServiceBlockingStub::class.java)

}