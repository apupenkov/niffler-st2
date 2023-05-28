package guru.qa.niffler.test;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.db.dao.DAOTYPE;
import guru.qa.niffler.db.dao.NifflerUsersDAO;
import guru.qa.niffler.db.entity.Authority;
import guru.qa.niffler.db.entity.AuthorityEntity;
import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.jupiter.annotation.CreateUser;
import guru.qa.niffler.utils.RANDOMUSERDATA;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginNewUserTest extends BaseWebTest {

  @AllureId("264")
  @Test
  @CreateUser(userDao = DAOTYPE.HIBERNATE, isDelete = false)
  void loginTest(UserEntity user) {
    System.out.println(user.getUsername());
    System.out.println(user.getPassword());
//    Allure.step("open page", () -> Selenide.open("http://127.0.0.1:3000/main"));
//    $("a[href*='redirect']").click();
//    $("input[name='username']").setValue(user.getUsername());
//    $("input[name='password']").setValue(user.getPassword());
//    $("button[type='submit']").click();
//    $("a[href*='friends']").click();
//    $(".header").should(visible).shouldHave(text("Niffler. The coin keeper."));
  }

  @AllureId("265")
  @Test
  void updateUser() {

    final NifflerUsersDAO usersDAO = DAOTYPE.HIBERNATE.getDao();

//    UserEntity user = usersDAO.getUser("Kacy");
    UserEntity user = new UserEntity();
    user.setUsername("Kacy Dao2");
//    user.setPassword(usersDAO.pe.encode(RANDOMUSERDATA.FAKE.getPass()));
    user.setPassword(RANDOMUSERDATA.FAKE.getPass());
    user.setEnabled(true);
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setAuthorities(Arrays.stream(Authority.values()).map(
            a -> {
              AuthorityEntity ae = new AuthorityEntity();
              ae.setAuthority(a);
              return ae;
            }
    ).toList());

    usersDAO.createUser(user);
  }
}
