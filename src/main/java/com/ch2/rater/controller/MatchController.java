package com.ch2.rater.controller;

import com.ch2.rater.dto.MatchResponse;
import com.ch2.rater.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;

    @GetMapping
    public MatchResponse getMatches() {
        return new MatchResponse(matchService.getMatches());
    }
}
