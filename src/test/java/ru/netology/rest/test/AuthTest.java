package ru.netology.rest.test;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.rest.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.rest.data.DataGenerator.Registration.getUser;
import static ru.netology.rest.data.DataGenerator.getRandomPassword;
import static ru.netology.rest.data.DataGenerator.getRandomLogin;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;



public class AuthTest {

    @BeforeEach
    void setup() {open("http://localhost:9999");}


    @Test
    void ShouldSuccessfulLoginIfRegisteredUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет")).shouldBe((Condition.visible));

        }

    @Test
    void ShouldNotBeSuccessfulLoginIfNotRegisteredUser() {
        var User = getUser("active");
        $("[data-test-id='login'] input").setValue(User.getLogin());
        $("[data-test-id='password'] input").setValue(User.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));

    }

    @Test
    void ShouldNotBeSuccessfulLoginIfRegisteredUserBlocked() {
        var registeredUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Пользователь заблокирован"))
                .shouldBe((Condition.visible));

    }

    @Test
    void ShouldNotSuccessfulLoginIfRegisteredUserWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongpassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongpassword);
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }

    @Test
    void ShouldNotSuccessfulLoginIfRegisteredUserWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wronglogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wronglogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"))
                .shouldBe((Condition.visible));
    }

    @Test
    void ShouldNotSuccessfulLoginLoginEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue("");
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button.button").click();
        $("[data-test-id='login'] .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe((Condition.visible));
    }

    @Test
    void ShouldNotSuccessfulLoginPasswordEmpty() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue("");
        $("button.button").click();
        $("[data-test-id='password'] .input__sub")
                .shouldHave(Condition.text("Поле обязательно для заполнения"))
                .shouldBe((Condition.visible));
    }

}

