package com.ch2.rater.controller;

import com.ch2.rater.service.LinkService;
import com.ch2.rater.dto.LinkBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @PostMapping
    public void addLink(@RequestBody LinkBody linkBody) {
        linkService.addLink(linkBody.url());
    }
}
