package no.nav.fo.nightking.service;

import io.micrometer.core.instrument.Counter;
import no.nav.fo.nightking.domain.ArenaOppfolging;
import no.nav.fo.nightking.mapper.ArenaOppfolgingMapper;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.binding.HentOppfoelgingsstatusPersonIkkeFunnet;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.binding.HentOppfoelgingsstatusSikkerhetsbegrensning;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.binding.HentOppfoelgingsstatusUgyldigInput;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.binding.OppfoelgingsstatusV2;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.meldinger.HentOppfoelgingsstatusRequest;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.meldinger.HentOppfoelgingsstatusResponse;
import no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.informasjon.Person;
import org.slf4j.Logger;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.xml.datatype.XMLGregorianCalendar;

import static no.nav.metrics.MetricsFactory.getMeterRegistry;
import static org.slf4j.LoggerFactory.getLogger;

public class ArenaOppfolgingService {

    private static final Logger LOG = getLogger(ArenaOppfolgingService.class);
    private OppfoelgingsstatusV2 oppfoelgingsstatusV2Service;
    private Counter counter;

    public ArenaOppfolgingService(OppfoelgingsstatusV2 oppfoelgingsstatusV2Service) {
        this.oppfoelgingsstatusV2Service = oppfoelgingsstatusV2Service;
        counter = Counter.builder("nightking.kall_mot_arena_oppfolging").register(getMeterRegistry());
    }

    public ArenaOppfolging hentArenaOppfolging(String identifikator) {
        return getArenaOppfolgingsstatus(identifikator);
    }

    private ArenaOppfolging getArenaOppfolgingsstatus(String identifikator) {

        counter.increment();

        Person person = new Person();
        person.setIdent(identifikator);

        HentOppfoelgingsstatusRequest request = new HentOppfoelgingsstatusRequest();
        request.setBruker(person);
        try {
            HentOppfoelgingsstatusResponse hentOppfoelgingsstatusResponse = oppfoelgingsstatusV2Service.hentOppfoelgingsstatus(request);
            return ArenaOppfolgingMapper.mapTilArenaOppfolgingsstatusV2(hentOppfoelgingsstatusResponse);
        } catch (java.lang.reflect.UndeclaredThrowableException e) {
            Throwable undeclared = e.getUndeclaredThrowable();
            throw undeclared != null  && undeclared.getCause() instanceof HentOppfoelgingsstatusPersonIkkeFunnet 
                    ? notFound(identifikator, undeclared.getCause()) 
                    : e;
        } catch (HentOppfoelgingsstatusPersonIkkeFunnet e) {
            throw notFound(identifikator, e);
        } catch (HentOppfoelgingsstatusSikkerhetsbegrensning e) {
            throw new ForbiddenException("Ikke tilgang til bruker " + identifikator, e);
        } catch (HentOppfoelgingsstatusUgyldigInput e) {
            throw new BadRequestException("Ugyldig bruker identifikator: " + identifikator, e);
        }
    }

    private NotFoundException notFound(String identifikator, Throwable t) {
        return new NotFoundException("Fant ikke bruker: " + identifikator, t);
    }

}
