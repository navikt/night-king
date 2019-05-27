package no.nav.fo.nightking.mapper;


import no.nav.fo.nightking.domain.ArenaOppfolging;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Optional;

public class ArenaOppfolgingMapper {

    public static LocalDate xmlGregorianCalendarToLocalDate(XMLGregorianCalendar inaktiveringsdato) {
        return Optional.ofNullable(inaktiveringsdato)
                .map(XMLGregorianCalendar::toGregorianCalendar)
                .map(GregorianCalendar::toZonedDateTime)
                .map(ZonedDateTime::toLocalDate).orElse(null);
    }


    public static ArenaOppfolging mapTilArenaOppfolgingsstatusV2(no.nav.tjeneste.virksomhet.oppfoelgingsstatus.v2.meldinger.HentOppfoelgingsstatusResponse response) {
        return new ArenaOppfolging()
                .setOppfolgingsenhet(response.getNavOppfoelgingsenhet())
                .setRettighetsgruppe(response.getRettighetsgruppeKode().getValue())
                .setFormidlingsgruppe(response.getFormidlingsgruppeKode().getValue())
                .setServicegruppe(response.getServicegruppeKode().getValue())
                .setInaktiveringsdato(xmlGregorianCalendarToLocalDate(response.getInaktiveringsdato()))
                .setKanEnkeltReaktiveres(response.isKanEnkeltReaktiveres());
    }
}
