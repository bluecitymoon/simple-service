package com.pure.service.web.rest;

import com.pure.service.service.dto.Sequence;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;


@RestController
@RequestMapping("/api")
public class SequenceNumberResource {

    @PostMapping("/sequence/get")
    public ResponseEntity<Sequence> createFreeClassRecord() {

        Long currentInstant = Instant.now().getEpochSecond();

        String text = "" + currentInstant + RandomStringUtils.randomNumeric(6);

        Sequence sequence = new Sequence();
        sequence.setNumber(text);

        return ResponseEntity.ok(sequence);
    }

}
