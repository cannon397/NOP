package com.cannon.nop.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class TimeController {
    @GetMapping("time")
    public ResponseEntity<String> getCurrentServerTime() {
        Instant nowUtc = Instant.now();
        ZonedDateTime nowKst = nowUtc.atZone(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ResponseEntity.ok(nowKst.format(formatter));
    }
}
