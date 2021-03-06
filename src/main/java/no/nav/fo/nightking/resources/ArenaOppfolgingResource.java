package no.nav.fo.nightking.resources;

import io.micrometer.core.instrument.Counter;
import io.swagger.annotations.Api;
import no.nav.apiapp.security.PepClient;
import no.nav.fo.nightking.domain.ArenaOppfolging;
import no.nav.fo.nightking.service.ArenaOppfolgingService;
import org.springframework.stereotype.Component;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static no.nav.metrics.MetricsFactory.getMeterRegistry;

@Component
@Path("/oppfolging")
@Produces("application/json")
@Api(value = "ArenaOppfolgingResource", description = "Henter oppfolging dataen i Arena")
public class ArenaOppfolgingResource {
    private final PepClient pepClient;
    private final ArenaOppfolgingService arenaOppfolgingService;
    private final Provider<HttpServletRequest> provider;
    private Counter counter;

    public ArenaOppfolgingResource(
            PepClient pepClient,
            ArenaOppfolgingService arenaOppfolgingService,
            Provider<HttpServletRequest> provider
            ) {

        this.provider = provider;
        this.pepClient = pepClient;
        this.arenaOppfolgingService = arenaOppfolgingService;
        counter = Counter.builder("nightking.antall_kall").register(getMeterRegistry());
    }

    private String getFnrFromUrl() {
        return provider.get().getParameter("fnr");
    }

    @GET
    @Path("/")
    public ArenaOppfolging hent() {
        counter.increment();
        pepClient.sjekkLeseTilgangTilFnr(getFnrFromUrl());
        return arenaOppfolgingService.hentArenaOppfolging(getFnrFromUrl());

    }
}
