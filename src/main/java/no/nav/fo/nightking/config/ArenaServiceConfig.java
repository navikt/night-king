package no.nav.fo.nightking.config;


import no.nav.sbl.dialogarena.common.cxf.CXFClient;
import no.nav.sbl.dialogarena.types.Pingable;
import no.nav.sbl.dialogarena.types.Pingable.Ping.PingMetadata;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.binding.OppfoelgingsstatusV2;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

import static no.nav.sbl.dialogarena.common.cxf.InstanceSwitcher.createMetricsProxyWithInstanceSwitcher;
import static no.nav.sbl.dialogarena.types.Pingable.Ping.feilet;
import static no.nav.sbl.dialogarena.types.Pingable.Ping.lyktes;
import static no.nav.sbl.util.EnvironmentUtils.getRequiredProperty;
import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class ArenaServiceConfig {
    public static final String VIRKSOMHET_OPPFOELGINGSSTATUS_V2_PROPERTY = "VIRKSOMHET_OPPFOELGINGSSTATUS_V2_ENDPOINTURL";

    private static final Logger LOG = getLogger(ArenaServiceConfig.class);


    @Bean
    public OppfoelgingsstatusV2 oppfoelgingsstatusV2() {
        OppfoelgingsstatusV2 prod = oppfoelgingstatusV2PortType()
                .configureStsForSubject()
                .build();
        return prod;
    }

    public static CXFClient<OppfoelgingsstatusV2> oppfoelgingstatusV2PortType() {
        final String url = getRequiredProperty(VIRKSOMHET_OPPFOELGINGSSTATUS_V2_PROPERTY);
        LOG.info("URL for OppfoelgingStatus_V2 er {}", url);
        return new CXFClient<>(OppfoelgingsstatusV2.class)
                .withOutInterceptor(new LoggingOutInterceptor())
                .address(url);
    }

    @Bean
    Pingable oppfoelgingstatusV2Ping() {
        final String url = getRequiredProperty(VIRKSOMHET_OPPFOELGINGSSTATUS_V2_PROPERTY);
        LOG.info("URL for Oppfoelgingstatus_V2 er {}", url);
        OppfoelgingsstatusV2 oppfoelgingsstatusV2 = oppfoelgingstatusV2PortType()
                .configureStsForSystemUser()
                .build();


        PingMetadata metadata = new PingMetadata(UUID.randomUUID().toString(),
                "OPPFOELGINGSTATUS_V2 via " + getRequiredProperty(VIRKSOMHET_OPPFOELGINGSSTATUS_V2_PROPERTY),
                "Ping av oppfolgingstatus_v2. Henter informasjon om oppfølgingsstatus fra arena.",
                true
        );

        return () -> {
            try {
                oppfoelgingsstatusV2.ping();
                return lyktes(metadata);
            } catch (Exception e) {
                return feilet(metadata, e);
            }
        };
    }
}
