package br.com.zup.edu.chave

import br.com.zup.edu.*
import br.com.zup.edu.comum.grpc.GrpcClientFactory
import com.google.protobuf.Timestamp
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
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ConsultaChaveControllerTest(
    @Inject val grpcClientConsulta: KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub,
    @Inject @field:Client("/") var client: HttpClient
){

    companion object{
        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()
    }

    @Test
    fun `consulta chave com sucesso`(){

        Mockito.`when`(grpcClientConsulta.consulta(filtroByClient())).thenReturn(responseGrpc())

        val request = HttpRequest.GET<ConsultaResponse>("/api/clients/${clientId}/keys/${pixId}")

        val response = client.toBlocking().exchange(request, ConsultaResponse::class.java)

        with(response){
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
            assertEquals(responseGrpc().valorChave, body()?.valorChave)
            assertEquals(responseGrpc().tipoChave, body()?.tipoChave!!.convert())
        }
    }

    private fun filtroByClient(): ConsultaChaveRequest{
        return ConsultaChaveRequest
                    .newBuilder()
                    .setByClient(
                        ConsultaChaveRequest.ByClient
                            .newBuilder()
                            .setClientId(clientId)
                            .setPixId(pixId)
                            .build())
                    .build()
    }

    private fun responseGrpc():ConsultaChaveResponse{
        return ConsultaChaveResponse.newBuilder()
            .setClientId(clientId)
            .setPixId(pixId)
            .setValorChave("02467781054")
            .setTipoChave(TipoChave.CPF)
            .setTitular(br.com.zup.edu.TitularResponse.newBuilder()
                .setNome("Rafael M C Ponte")
                .setCpf("02467781054"))
            .setConta(br.com.zup.edu.ContaResponse.newBuilder()
                .setTipoConta(TipoConta.CONTA_POUPANCA)
                .setAgencia("0001")
                .setNumero("291900")
                .setTipoConta(TipoConta.CONTA_POUPANCA))
            .setCriadoEm(LocalDateTime.now().let{
                val createAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createAt.epochSecond)
                    .setNanos(createAt.nano)
                    .build()
            })
            .build()

    }

    @Factory
    @Replaces(factory = GrpcClientFactory::class)
    internal class Clients{
        @Singleton
        fun stubMock() = Mockito.mock(KeyManagerConsultaServiceGrpc.KeyManagerConsultaServiceBlockingStub::class.java)

    }
}