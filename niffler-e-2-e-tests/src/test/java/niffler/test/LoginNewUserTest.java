package niffler.test;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import niffler.db.dao.DAOTYPE;
import niffler.db.dao.NifflerUsersDAO;
import niffler.db.dao.NifflerUsersDAOHibernate;
import niffler.db.entity.UserEntity;
import niffler.jupiter.annotation.CreateUser;
import niffler.utils.RANDOMUSERDATA;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginNewUserTest extends BaseWebTest {

  @AllureId("264")
  @Test
  @CreateUser(userDao = DAOTYPE.JDBC, isDelete = false)
  void loginTest(UserEntity user) {
    Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
    $("a[href*='redirect']").click();
    $("input[name='username']").setValue(user.getUsername());
    $("input[name='password']").setValue(user.getPassword());
    $("button[type='submit']").click();
    $("a[href*='friends']").click();
    $(".header").should(visible).shouldHave(text("Niffler. The coin keeper."));
  }

  @AllureId("265")
  @Test
  void updateUser() {

    final NifflerUsersDAO usersDAO = DAOTYPE.HIBERNATE.getDao();

    UserEntity user = usersDAO.getUser("Kacy");
    user.setUsername("Kacy Dao");
    user.setPassword(usersDAO.pe.encode(RANDOMUSERDATA.FAKE.getPass()));
    user.setEnabled(true);
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);

    usersDAO.updateUser(user);
  }
}
