package hr.fer.zemris.vhdllab.entity.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.validator.ValidatorClass;

@Documented
@ValidatorClass(NameFormatConstraintValidator.class)
@Target( { TYPE })
@Retention(RUNTIME)
public @interface NameFormatConstraint {
    String message() default "{validator.nameFormatConstraint}";
}
