
import no.nav.apiapp.ApiApp;
import no.nav.brukerdialog.security.Constants;
import no.nav.fo.nightking.config.ApplicationConfig;

import static java.lang.System.setProperty;
import static no.nav.fo.nightking.config.ApplicationConfig.VEILARBLOGIN_REDIRECT_URL_URL_PROPERTY;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;

public class Main {

    public static void main(String... args) throws Exception {
        setProperty(Constants.OIDC_REDIRECT_URL_PROPERTY_NAME, getRequiredProperty(VEILARBLOGIN_REDIRECT_URL_URL_PROPERTY));

        ApiApp.runApp(ApplicationConfig.class, args);
    }
}
