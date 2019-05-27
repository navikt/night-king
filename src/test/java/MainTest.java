import no.nav.testconfig.ApiAppTest;

public class MainTest {

    public static final String TEST_PORT = "8666";

    public static void main(String[] args) throws Exception {
        ApiAppTest.setupTestContext(ApiAppTest.Config.builder()
                .applicationName("nightking")
                .build()
        );
        Main.main(TEST_PORT);
    }

}
