package niffler.jupiter.annotation;

import niffler.jupiter.extension.CreateUserViaDb;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(CreateUserViaDb.class)
public @interface CreateUser {

    String username();
    String password();
    boolean enabled();
    boolean accountNonExpired();
    boolean accountNonLocked();
    boolean credentialsNonExpired();
}
