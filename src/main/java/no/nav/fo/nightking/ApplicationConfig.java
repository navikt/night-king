package no.nav.fo.nightking;

import no.nav.apiapp.ApiApplication;
import no.nav.apiapp.config.ApiAppConfigurator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({

})
public class ApplicationConfig implements ApiApplication {

    @Override
    public String getContextPath() {
        return "/nightking";
    }

    @Override
    public void configure(ApiAppConfigurator apiAppConfigurator) {
    }

}
