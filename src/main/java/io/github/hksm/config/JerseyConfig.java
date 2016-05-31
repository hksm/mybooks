package io.github.hksm.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import io.github.hksm.controller.AuthorController;
import io.github.hksm.controller.BookController;
import io.github.hksm.controller.CountryController;
import io.github.hksm.controller.GenreController;
import io.github.hksm.controller.LanguageController;
import io.github.hksm.controller.AuthController;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
    	register(new JsonMapperContextResolver());
    	
        register(AuthorController.class);
        register(BookController.class);
        register(CountryController.class);
        register(GenreController.class);
        register(LanguageController.class);
        register(AuthController.class);
    }

}