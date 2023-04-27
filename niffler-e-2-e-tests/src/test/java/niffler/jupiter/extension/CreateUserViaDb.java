package niffler.jupiter.extension;

import io.qameta.allure.AllureId;
import niffler.db.dao.NifflerUsersDAO;
import niffler.db.dao.NifflerUsersDAOJdbc;
import niffler.db.entity.Authority;
import niffler.db.entity.AuthorityEntity;
import niffler.db.entity.UserEntity;
import niffler.jupiter.annotation.CreateUser;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;
import java.util.Objects;

public class CreateUserViaDb  implements BeforeEachCallback, ParameterResolver {

    public static final ExtensionContext.Namespace CREATE_USER_NAMESPACE = ExtensionContext.Namespace.create(CreateUserViaDb.class);

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final String testId = getTestId(extensionContext);
        return extensionContext.getStore(CREATE_USER_NAMESPACE).get(testId);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        final String testId = getTestId(context);

        CreateUser createUserAnno = context.getRequiredTestMethod().getAnnotation(CreateUser.class);

        UserEntity user;

        if(createUserAnno != null) {
            NifflerUsersDAO usersDAO = new NifflerUsersDAOJdbc();
            user = new UserEntity();
            user.setUsername(createUserAnno.username());
            user.setPassword(createUserAnno.password());
            user.setEnabled(createUserAnno.enabled());
            user.setAccountNonExpired(createUserAnno.accountNonExpired());
            user.setAccountNonLocked(createUserAnno.accountNonLocked());
            user.setCredentialsNonExpired(createUserAnno.credentialsNonExpired());
            user.setAuthorities(Arrays.stream(Authority.values()).map(
                    a -> {
                        AuthorityEntity ae = new AuthorityEntity();
                        ae.setAuthority(a);
                        return ae;
                    }
            ).toList());
            usersDAO.createUser(user);

            context.getStore(CREATE_USER_NAMESPACE).put(testId, user);
        }
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }
}
