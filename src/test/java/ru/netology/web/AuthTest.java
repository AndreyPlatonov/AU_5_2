package ru.netology.web;

import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;


public class AuthTest {

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }
    @Test
    void shouldBeSuccess() {
        // сам запрос

        DataGenerator userInfo = new DataGenerator();
        RegistrationInfo infoClient = userInfo.generatedByInfo();
        String userJson = userInfo.generatedJson(infoClient);

        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(userJson) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK

        $("[data-test-id=login] input").setValue(infoClient.getLogin());
        $("[data-test-id=password] input").setValue(infoClient.getPassword());
        $("[data-test-id=action-login].button").click();
        $(".heading").shouldHave(Condition.exactText("  Личный кабинет")).shouldBe(Condition.visible);
    }
}
