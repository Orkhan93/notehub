package az.spring.notehub.constraint.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Size(min = 6)
@NotBlank(message = "Password must not be blank")
public @interface Password {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}