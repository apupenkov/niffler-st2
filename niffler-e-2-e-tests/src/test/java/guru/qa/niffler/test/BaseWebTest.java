package guru.qa.niffler.test;


import com.codeborne.selenide.Configuration;
import guru.qa.niffler.jupiter.annotation.WebTest;
import guru.qa.niffler.ui.InvitationTableForm;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.ui.NavigationPanel;

@WebTest
public abstract class BaseWebTest {

  static {
    Configuration.browser = "firefox";
    Configuration.browserSize = "1920x1080";
    Configuration.timeout = 10000;
  }

  LoginPage loginPage = new LoginPage();
  NavigationPanel navigationPanel = new NavigationPanel();
  InvitationTableForm invitationTableForm = new InvitationTableForm();

}
