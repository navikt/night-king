package no.nav.fo.nightking.config;

import no.nav.apiapp.security.PepClient;
import no.nav.fo.nightking.resources.ArenaOppfolgingResource;
import no.nav.fo.nightking.service.ArenaOppfolgingService;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.binding.OppfoelgingsstatusV2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

@Configuration
public class ServiceConfig {
    @Bean
    ArenaOppfolgingService arenaOppfolgingService(OppfoelgingsstatusV2 oppfoelgingsstatusV2) {

        return new ArenaOppfolgingService(oppfoelgingsstatusV2);
    }

    @Bean
    ArenaOppfolgingResource arenaOppfolgingResource(PepClient pepClient,
                                                    ArenaOppfolgingService arenaOppfolgingService,
                                                    Provider<HttpServletRequest> provider){
        return new ArenaOppfolgingResource(pepClient, arenaOppfolgingService, provider);
    }

}
