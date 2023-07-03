package ru.netology.web;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {

    }

    public static void sendRequest(RegistrationInfo user) {

        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(generatedJson(user)) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static void sendRequestFail(RegistrationInfo user) {

        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(generatedJson(user)) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(500); // код 200 OK
    }


    public static RegistrationInfo generatedByInfo(String status) {
        return new RegistrationInfo(
                faker.name().username(),
                faker.internet().password(),
                status);
    }

    public static RegistrationInfo generatedByInfoLogin(RegistrationInfo regInfo) {
        return new RegistrationInfo(
                regInfo.getLogin(),
                faker.internet().password(),
                regInfo.getStatus());
    }
    public static RegistrationInfo generatedByInfoPassword(RegistrationInfo regInfo) {
        return new RegistrationInfo(
                faker.name().username(),
                regInfo.getPassword(),
                regInfo.getStatus());
    }

    public static RegistrationInfo activatedUser(RegistrationInfo regInfo) {
        return new RegistrationInfo(
                regInfo.getLogin(),
                regInfo.getPassword(),
                "active");
    }

    public static RegistrationInfo deactivatedUser(RegistrationInfo regInfo) {
        return new RegistrationInfo(
                regInfo.getLogin(),
                regInfo.getPassword(),
                "blocked");
    }

    public static RegistrationInfo generatedByNullLogin(String status) {
        return new RegistrationInfo(
                "",
                faker.internet().password(),
                status);
    }

    public static RegistrationInfo generatedByNullPassword(String status) {
        return new RegistrationInfo(
                faker.name().username(),
                "",
                status);
    }

    public static String generatedJson(RegistrationInfo info) {
        Gson gson = new Gson();
        return gson.toJson(info);
    }
}