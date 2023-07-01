package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class AuthTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldBeSuccess() {

        var infoClient = DataGenerator.generatedByInfo("active");
        DataGenerator.sendRequest(infoClient);

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $(".heading").shouldHave(Condition.exactText("  Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    void shouldBeSameLogin() {

        var infoClient = DataGenerator.generatedByInfo("active");
        DataGenerator.sendRequest(infoClient);

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $(".heading").shouldHave(Condition.exactText("  Личный кабинет")).shouldBe(Condition.visible);

        var infoClientSame = DataGenerator.generatedByInfoLogin(infoClient);
        DataGenerator.sendRequest(infoClientSame);

        open("http://localhost:9999/");

        $("[data-test-id=login] input").setValue(infoClientSame.getLogin());
        $("[data-test-id=password] input").setValue(infoClientSame.getPassword());
        $("[data-test-id=action-login].button").click();
        $(".heading").shouldHave(Condition.exactText("  Личный кабинет")).shouldBe(Condition.visible);

    }

    @Test
    void shouldBeBlockedUser() {

        var infoClient = DataGenerator.generatedByInfo("blocked");
        DataGenerator.sendRequest(infoClient);

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $("[data-test-id=error-notification].notification .notification__content").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(5)).shouldBe(Condition.visible);
    }

    @Test
    void shouldBeFailedStatus() {

        var infoClient = DataGenerator.generatedByInfo("AAAAA");
        DataGenerator.sendRequestFail(infoClient);

    }

    @Test
    void shouldBeNullPassword() {

        var infoClient = DataGenerator.generatedByNullPassword("active");
        DataGenerator.sendRequest(infoClient);

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $("[data-test-id=password].input .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldBeNullLogin() {

        var infoClient = DataGenerator.generatedByNullLogin("active");
        DataGenerator.sendRequest(infoClient);

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $("[data-test-id=login].input .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldBeFromActiveToBlocked() {

        var infoClient = DataGenerator.generatedByInfo("active");
        DataGenerator.sendRequest(infoClient);

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $(".heading").shouldHave(Condition.exactText("  Личный кабинет")).shouldBe(Condition.visible);


        infoClient = DataGenerator.deactivatedUser(infoClient);
        DataGenerator.sendRequest(infoClient);

        open("http://localhost:9999/");

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $("[data-test-id=error-notification].notification .notification__content").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(5)).shouldBe(Condition.visible);

    }

    @Test
    void shouldBeFromBlockedToActive() {

        var infoClient = DataGenerator.generatedByInfo("blocked");
        DataGenerator.sendRequest(infoClient);

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $("[data-test-id=error-notification].notification .notification__content").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(5)).shouldBe(Condition.visible);

        infoClient = DataGenerator.activatedUser(infoClient);
        DataGenerator.sendRequest(infoClient);

        open("http://localhost:9999/");

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $(".heading").shouldHave(Condition.exactText("  Личный кабинет")).shouldBe(Condition.visible);
    }
}
