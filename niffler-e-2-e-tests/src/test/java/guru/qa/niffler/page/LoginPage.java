package guru.qa.niffler.page;

import com.codeborne.selenide.Conditional;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage>{

    private final SelenideElement username = $("input[name='username']");
    private final SelenideElement goToLoginFormBtn = $("a[href*='redirect']");
    private final SelenideElement password = $("input[name='password']");
    private final SelenideElement loginBtn = $("button[type='submit']");
    private final SelenideElement header = $(".form__header");

    public void login(UserJson user) {
        goToLoginFormBtn.click();
        username.setValue(user.getUsername());
        password.setValue(user.getPassword());
        loginBtn.click();
    }

    @Override
    public LoginPage checkThatPageLoaded() {
        header.shouldHave(text("Welcome to Niffler. The coin keeper"));
        return this;
    }
}
