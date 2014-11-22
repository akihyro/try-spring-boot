package akihyro.tryspringboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorPathExtension(true)
            .favorParameter(true)
            .parameterName("_format")
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML)
            .mediaType("json", MediaType.APPLICATION_JSON);
    }

}
