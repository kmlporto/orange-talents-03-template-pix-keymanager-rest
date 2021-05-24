package br.com.zup.edu.comum.annotation

import br.com.zup.edu.chave.CadastraRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [PixKeyValidator::class])
annotation class KeyValid(
    val message: String = "chave Pix inválida (\${validatedValue.tipoChave})",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
)

@Singleton
class PixKeyValidator: ConstraintValidator<KeyValid, CadastraRequest> {
    override fun isValid(
        value: CadastraRequest?,
        annotationMetadata: AnnotationValue<KeyValid>,
        context: ConstraintValidatorContext
    ): Boolean {
        if(value?.tipoChave == null){
            return false
        }

        return value.tipoChave.valida(value.chave)
    }

}