package br.com.zup.edu.comum.grpc

import br.com.zup.edu.KeyManagerCadastraServiceGrpc
import br.com.zup.edu.KeyManagerConsultaServiceGrpc
import br.com.zup.edu.KeyManagerListagemServiceGrpc
import br.com.zup.edu.KeyManagerRemoveServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class GrpcClientFactory(@GrpcChannel("key-manager") val channel: ManagedChannel) {

    @Singleton
    fun cadastraClientStub() = KeyManagerCadastraServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeClientStub() = KeyManagerRemoveServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaClientStub() = KeyManagerConsultaServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaClientStub() = KeyManagerListagemServiceGrpc.newBlockingStub(channel)

}