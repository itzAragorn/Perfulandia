package com.prueba_fs.cl.Prueba_fs.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Prueba FS API")
                        .version("1.0.0")
                        .description("Documentación de la API de Prueba FS"));
    }
}
