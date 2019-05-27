package no.nav.fo.nightking.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import no.nav.apiapp.security.PepClient;
import no.nav.fo.nightking.domain.ArenaOppfolging;
import no.nav.fo.nightking.service.ArenaOppfolgingService;
import org.springframework.stereotype.Component;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Component
@Path("/oppfolging")
@Produces("application/json")
@Api(value = "ArenaOppfolgingResource", description = "Henter oppfolging dataen i Arena")
public class ArenaOppfolgingResource {
    private final PepClient pepClient;
    private final ArenaOppfolgingService arenaOppfolgingService;
    private final Provider<HttpServletRequest> provider;

    public ArenaOppfolgingResource(
            PepClient pepClient,
            ArenaOppfolgingService arenaOppfolgingService,
            Provider<HttpServletRequest> provider
            ) {

        this.provider = provider;
        this.pepClient = pepClient;
        this.arenaOppfolgingService = arenaOppfolgingService;
    }

    public String getFnrFromUrl() {
        return provider.get().getParameter("fnr");
    }


    @POST
    @Path("/")
    public ArenaOppfolging hent() {
        pepClient.sjekkLeseTilgangTilFnr(getFnrFromUrl());
        return arenaOppfolgingService.hentArenaOppfolging(getFnrFromUrl());

    }
}
