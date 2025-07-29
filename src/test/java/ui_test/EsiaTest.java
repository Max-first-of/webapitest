package ui_test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import ui_page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EsiaTest {
    String host = "https://svcdev-beta.test.gosuslugi.ru/600426/1/form";
    String login, password;
    @Test
    @DisplayName("Успешная авторизация с логин паролем")
    public void LoginTest(){
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
    @Tag("")
    public void LoginFailureTest(){
        LoginPage loginPage = open(host, LoginPage.class);
        loginPage.authWithLoginPassword("+7(998)3819979","wrongPassword");
        assertThat(loginPage.hasTitle())
                .as("Заголовок содержит текст {}")
                .isTrue();
        //"Неверные логин или пароль"
    }
}
