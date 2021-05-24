package br.com.zup.edu.comum.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StatusRuntimeExceptionHandlerTest{

    val requestGenerica = HttpRequest.GET<Any>("/")

    @Test
    fun `deve retornar error 400 quando statusException for failed_precondition`(){
        val mensagem = "conta não existe"

        val notFoundException = StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription(mensagem))

        val resposta = StatusRuntimeExceptionHandler().handle(requestGenerica, notFoundException)

        with(resposta){
            assertEquals(HttpStatus.BAD_REQUEST.code, status.code)
            assertNotNull(body())
            assertEquals(mensagem, (body() as JsonError).message )
        }
    }

    @Test
    fun `deve retornar error 404 quando statusException for not_found`(){
        val mensagem = "não encontrado"

        val notFoundException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

        val resposta = StatusRuntimeExceptionHandler().handle(requestGenerica, notFoundException)

        with(resposta){
            assertEquals(HttpStatus.NOT_FOUND.code, status.code)
            assertNotNull(body())
            assertEquals(mensagem, (body() as JsonError).message )
        }
    }

    @Test
    fun `deve retornar error 422 quando statusException for already_exists`(){
        val mensagem = "chave já existente"

        val notFoundException = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(mensagem))

        val resposta = StatusRuntimeExceptionHandler().handle(requestGenerica, notFoundException)

        with(resposta){
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.code, status.code)
            assertNotNull(body())
            assertEquals(mensagem, (body() as JsonError).message )
        }
    }

    @Test
    fun `deve retornar error 500 quando statusException for outro`(){
        val mensagem = "Não foi possível completar o cadastro"
        val notFoundException = StatusRuntimeException(Status.UNKNOWN)

        val resposta = StatusRuntimeExceptionHandler().handle(requestGenerica, notFoundException)

        with(resposta){
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.code, status.code)
            assertNotNull(body())
            assertEquals(mensagem, (body() as JsonError).message )
        }
    }
}