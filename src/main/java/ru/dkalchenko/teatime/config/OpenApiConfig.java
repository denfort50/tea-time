package ru.dkalchenko.teatime.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Tea time",
                description = "Scheduled reminder", version = "1.0.0",
                contact = @Contact(
                        name = "Denis Kalchenko",
                        email = "denfort50@yandex.ru",
                        url = "https://github.com/denfort50"
                )
        )
)
public class OpenApiConfig {
}
