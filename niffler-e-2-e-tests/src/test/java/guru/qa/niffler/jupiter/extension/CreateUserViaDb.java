package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.db.dao.NifflerUsersDAO;
import guru.qa.niffler.db.entity.Authority;
import guru.qa.niffler.db.entity.AuthorityEntity;
import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.jupiter.annotation.CreateUser;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;
import java.util.Objects;

public class CreateUserViaDb  implements BeforeEachCallback, AfterEachCallback, ParameterResolver {

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
            NifflerUsersDAO usersDAO = createUserAnno.userDao().getDao();
            user = new UserEntity();
            user.setUsername(createUserAnno.username().getName());
            user.setPassword(usersDAO.pe.encode(createUserAnno.password().getPass()));
            user.setEnabled(createUserAnno.enabled());
            user.setAccountNonExpired(createUserAnno.accountNonExpired());
            user.setAccountNonLocked(createUserAnno.accountNonLocked());
            user.setCredentialsNonExpired(createUserAnno.credentialsNonExpired());
            user.setAuthorities(Arrays.stream(Authority.values()).map(
                    a -> {
                        AuthorityEntity ae = new AuthorityEntity();
                        ae.setAuthority(a);
                        ae.setUser(user);
                        return ae;
                    }
            ).toList());
            int i = usersDAO.createUser(user);

            context.getStore(CREATE_USER_NAMESPACE).put(testId, user);
        }
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        final String testId = getTestId(context);

        CreateUser createUserAnno = context.getRequiredTestMethod().getAnnotation(CreateUser.class);

        int executeUpdate = 0;

        if(createUserAnno != null && createUserAnno.isDelete()) {
            NifflerUsersDAO usersDAO = createUserAnno.userDao().getDao();
            executeUpdate = usersDAO.removeUser(((UserEntity) context.getStore(CREATE_USER_NAMESPACE).get(testId)));
            if(executeUpdate > 0) {
                context.getStore(CREATE_USER_NAMESPACE).remove(testId);
            }
        }
    }
}
