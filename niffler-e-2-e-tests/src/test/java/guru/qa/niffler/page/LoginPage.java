package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.model.UserJson;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage<LoginPage>{

    private final SelenideElement usernameField = $("input[name='username']"),
        goToLoginFormBtn = $("a[href*='redirect']"),
        passwordField = $("input[name='password']"),
        loginBtn = $("button[type='submit']"),
        header = $(".form__header");

    public void login(UserJson user) {
        clickLoginBtn()
            .enterUsername(user.getUsername())
            .enterPassword(user.getPassword())
            .clickSignInBtn();
    }

    @Step("Нажать на кнопку входа")
    public LoginPage clickLoginBtn() {
        loginBtn.click();
        return this;
    }

    @Step("Нажать на кнопку регистрации")
    public RegistrationPage clickRegisterBtn() {
        $(byText("Register")).click();
        return new RegistrationPage();
    }

    @Step("Ввести имя пользователя")
    public LoginPage enterUsername(String username) {
        usernameField.setValue(username);
        return this;
    }

    @Step("Ввести пароль")
    public LoginPage enterPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    @Step("Нажать на кнопку входа")
    public LoginPage clickSignInBtn() {
        $(byText("Sign In")).click();
        return this;
    }

    @Step("Нажать на кнопку выхода")
    public LoginPage clickSignUp() {
        $(byText("Sign up!")).click();
        return this;
    }

    @Override
    public LoginPage checkThatPageLoaded() {
        header.shouldHave(text("Welcome to Niffler. The coin keeper"));
        return this;
    }
}
