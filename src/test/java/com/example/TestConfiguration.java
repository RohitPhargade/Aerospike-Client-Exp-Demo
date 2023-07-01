package com.example;

import com.google.gson.Gson;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;


@Factory
public class TestConfiguration {

    @Singleton
    public Gson gson() {
        return new Gson();
    }
}
