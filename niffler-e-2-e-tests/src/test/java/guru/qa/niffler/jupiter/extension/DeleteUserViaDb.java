package guru.qa.niffler.jupiter.extension;

import io.qameta.allure.AllureId;
import guru.qa.niffler.db.dao.NifflerUsersDAO;
import guru.qa.niffler.db.dao.NifflerUsersDAOJdbc;
import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.jupiter.annotation.DeleteUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.*;

import java.util.Objects;

import static guru.qa.niffler.jupiter.extension.CreateUserViaDb.CREATE_USER_NAMESPACE;

public class DeleteUserViaDb implements AfterEachCallback {
    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        final String testId = getTestId(context);

        boolean isAnnotateForDelete = context.getRequiredTestMethod().isAnnotationPresent(DeleteUser.class);

        int executeUpdate = 0;

        if(isAnnotateForDelete) {
            NifflerUsersDAO usersDAO = new NifflerUsersDAOJdbc();
            executeUpdate = usersDAO.removeUser(((UserEntity) context.getStore(CREATE_USER_NAMESPACE).get(testId)));
            if(executeUpdate > 0) {
                context.getStore(CREATE_USER_NAMESPACE).remove(testId);
            }
        }
    }

    private String getTestId(ExtensionContext context) {
        return Objects
                .requireNonNull(context.getRequiredTestMethod().getAnnotation(AllureId.class))
                .value();
    }
}