package com.artfolio.artfolio.controller;

import com.artfolio.artfolio.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auction")
@RequiredArgsConstructor
@RestController
public class AuctionController {
    private final AuctionService auctionService;

}
