package com.cannon.nop.interfaces;

import com.cannon.nop.interfaces.config.ApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(ApiPaths.BASE_URL)
public class TimeController {
    @GetMapping("time")
    public ResponseEntity<String> getCurrentServerTime() {
        Instant nowUtc = Instant.now();
        ZonedDateTime nowKst = nowUtc.atZone(ZoneId.of("Asia/Seoul"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ResponseEntity.ok(nowKst.format(formatter));
    }
}
