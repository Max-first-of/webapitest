package ui_test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import model.BuildTags;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import ui_page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DisplayName("Тестирование авторизации ЕСИА")
public class EsiaTest {
    String host = "https://svcdev-beta.test.gosuslugi.ru/600426/1/form";
    String login, password;
    @BeforeAll
    public void setUp(){

    }
    @Test
    @DisplayName("Успешная авторизация с логин паролем")
    @Tag(BuildTags.UI)
    public void LoginTest(){
        ;
        login = "+7(998)3819979";
        password = "O9Nh4Z";
        LoginPage loginPage = open(host, LoginPage.class);
        loginPage.authWithLoginPassword(login,password);
        assertThat(loginPage.hasTitle())
                .as("Заголовок содержит текст {}")
                .isTrue();
    }
    @Test
    @DisplayName("Ввод некорректного логин/пароля")
    @Tag(BuildTags.UI)
    public void LoginFailureTest(){
        LoginPage loginPage = open(host, LoginPage.class);
        loginPage.authWithLoginPassword("+7(998)3819979","wrongPassword");
        assertThat(loginPage.hasTitle())
                .as("Заголовок содержит текст {}")
                .isTrue();
        //"Неверные логин или пароль"
    }
}
