package com.pure.service.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing FreeClassRecord.
 */
@RestController
@RequestMapping("/.well-known")
public class HttpDNSResource {

    @GetMapping("/pki-validation/fileauth.txt")
    public String createFreeClassRecord() {

        return "2018042802284407w6fjs9c14vdn9dvs8vdm53macxc12xplthosen2qo39sqtx1";
    }

}
