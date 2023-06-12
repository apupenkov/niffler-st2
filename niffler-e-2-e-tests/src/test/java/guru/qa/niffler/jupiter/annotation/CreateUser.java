package guru.qa.niffler.jupiter.annotation;

import guru.qa.niffler.db.dao.DAOTYPE;
import guru.qa.niffler.jupiter.extension.CreateUserViaDb;
import guru.qa.niffler.utils.RANDOMUSERDATA;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(CreateUserViaDb.class)
public @interface CreateUser {

    DAOTYPE userDao() default DAOTYPE.HIBERNATE;
    RANDOMUSERDATA username() default RANDOMUSERDATA.FAKE;
    RANDOMUSERDATA password() default RANDOMUSERDATA.FAKE;
    boolean enabled() default true;
    boolean accountNonExpired() default true;
    boolean accountNonLocked() default true;
    boolean credentialsNonExpired() default true;
    boolean isDelete() default true;
}