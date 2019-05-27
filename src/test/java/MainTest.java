import no.nav.apiapp.ApiApp;
import no.nav.fo.nightking.config.ApplicationConfig;
import no.nav.testconfig.ApiAppTest;

import static java.lang.System.setProperty;
import static no.nav.fasit.FasitUtils.getWebServiceEndpoint;
import static no.nav.fo.nightking.config.ApplicationConfig.APPLICATION_NAME;
import static no.nav.fo.nightking.config.ArenaServiceConfig.VIRKSOMHET_OPPFOELGINGSSTATUS_V2_PROPERTY;
import static no.nav.testconfig.ApiAppTest.Config.builder;

public class MainTest {

    public static final String TEST_PORT = "8666";
    private static final String VIRKSOMHET_OPPFOELGINGSSTATUS_V2_ALIAS = "virksomhet:Oppfoelgingsstatus_v2";

    public static void main(String[] args) throws Exception {
        setProperty(VIRKSOMHET_OPPFOELGINGSSTATUS_V2_PROPERTY, getWebServiceEndpoint(VIRKSOMHET_OPPFOELGINGSSTATUS_V2_ALIAS).getUrl());

        ApiAppTest.setupTestContext(builder().applicationName(APPLICATION_NAME).build());
        TestContext.setup();

        String arguments[] = {TEST_PORT};
        ApiApp.runApp(ApplicationConfig.class, arguments);

    }

}
