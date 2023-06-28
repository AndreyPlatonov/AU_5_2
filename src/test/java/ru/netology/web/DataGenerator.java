package ru.netology.web;

import com.github.javafaker.Faker;
import com.google.gson.Gson;

import java.util.Locale;

public class DataGenerator {

    public static RegistrationInfo generatedByInfo() {
        Faker faker = new Faker(new Locale(("ru")));
        return new RegistrationInfo(
                faker.name().username(),
                faker.internet().password(),
                "active");
    }

    public static String generatedJson(RegistrationInfo info) {
        Gson gson = new Gson();
        return gson.toJson(info);
    }
}