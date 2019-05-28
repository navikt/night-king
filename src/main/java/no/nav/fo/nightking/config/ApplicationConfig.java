package no.nav.fo.nightking.config;

import no.nav.apiapp.ApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        CacheConfig.class,
        PepConfig.class,
        ArenaServiceConfig.class,
        ServiceConfig.class,
})
public class ApplicationConfig implements ApiApplication {
    public static final String APPLICATION_NAME = "nightking";
    public static final String VEILARBLOGIN_REDIRECT_URL_URL_PROPERTY = "VEILARBLOGIN_REDIRECT_URL_URL";

    @Override
    public void configure(ApiAppConfigurator apiAppConfigurator) {
        apiAppConfigurator
                .sts()
                .issoLogin();
    }

}
