package se.b3.healthtech.blackbird.blbcontent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@Service
public class ContentUtil {

    public static long setCreatedTime() {
        ZoneId zone = ZoneId.of("Europe/Stockholm");
        ZonedDateTime date = ZonedDateTime.now(zone);
        log.info("ZonedDateTime with zone : {}", date);
        return date.toInstant().toEpochMilli();
    }
}
