package ui_page;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage extends BasePage{

    public void authWithLoginPassword(String login, String password){
        $(By.id("login")).setValue(login);
        $(By.id("password")).setValue(password).pressEnter();
    }
    public boolean hasTitle(){
        return false;
    }
}
