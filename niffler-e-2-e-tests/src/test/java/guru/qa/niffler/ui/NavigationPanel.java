package guru.qa.niffler.ui;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.page.LoginPage;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class NavigationPanel {

    private final SelenideElement friendsTab = $("a[href*='friends']");
    private final SelenideElement peoplesTab = $(".header__navigation-item img[src*='globe']");
    private final SelenideElement logoutBtn = $(".header__logout .button-icon_type_logout");

    public NavigationPanel openTabFriends() {
        friendsTab.click();
        return this;
    }

    public NavigationPanel openTabPeoples() {
        peoplesTab.click();
        return this;
    }

    public NavigationPanel addFriend(String username) {
        $(By.xpath("//main//div[@class='people-content']//tbody//tr//td[contains(text(), '"
                + username + "')]//following-sibling::td//button")).click();
        return this;
    }

    public LoginPage logout() {
        logoutBtn.click();
        return new LoginPage();
    }
}