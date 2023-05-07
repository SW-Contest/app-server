package com.artfolio.artfolio.controller;

import com.artfolio.artfolio.dto.RealTimeAuctionInfo;
import com.artfolio.artfolio.service.RealTimeAuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/** 실시간 경매 정보 처리를 위한 컨트롤러 (redis 연동) */
@RequestMapping("/rt_auction")
@RequiredArgsConstructor
@RestController
public class RealTimeAuctionController {

    private final RealTimeAuctionService redisService;

    /* 경매 생성 메서드 */
    @PostMapping("/create")
    public ResponseEntity<Long> createAuction(@RequestBody RealTimeAuctionInfo auctionInfo) {
        Long key = redisService.createAuction(auctionInfo);
        return new ResponseEntity<>(key, HttpStatus.CREATED);
    }

    /* 단일 경매 정보를 불러오는 메서드 */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAuction(@PathVariable("id") Long auctionKey) {
        Object auctionInfo = redisService.getAuction(auctionKey);
        return ResponseEntity.ok(auctionInfo);
    }

    /* 경매 삭제 메서드 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAuction(@PathVariable("id") Long auctionKey) {
        Long res = redisService.deleteAuction(auctionKey);
        return ResponseEntity.ok(res);
    }

    /* 경매 종료 메서드 */
    @PostMapping("/finish")
    public ResponseEntity<Long> finishAuction(
            @RequestParam("auctionKey") Long auctionKey,
            @RequestParam("isSold") Long isSold
    ) {
        Long result = redisService.finishAuction(auctionKey, isSold == 1);
        return ResponseEntity.ok(result);
    }

    /* 경매 즉시 낙찰 메서드 */
    @PostMapping("/bid")
    public ResponseEntity<Long> finishAuctionWithBidder(
            @RequestParam("auctionKey") Long auctionKey,
            @RequestParam("bidderId") Long bidderId,
            @RequestParam("finalPrice") Long finalPrice
    ) {
        Long result = redisService.finishAuctionWithBidder(auctionKey, bidderId, finalPrice);
        return ResponseEntity.ok(result);
    }

    /* 경매 현재가 업데이트 메서드 */
    @PatchMapping("/update_price")
    public ResponseEntity<Long> updatePrice(
            @RequestParam("auctionKey") Long auctionKey,
            @RequestParam("price") Long price
    ) {
        Long result = redisService.updatePrice(auctionKey, price);
        return ResponseEntity.ok(result);
    }

    /* 경매 좋아요 +1 메서드 */
    @PatchMapping("/update_like")
    public ResponseEntity<Long> updateLike(
            @RequestParam("auctionKey") Long auctionKey,
            @RequestParam("memberId") Long memberId
    ) {
        Long result = redisService.updateLike(auctionKey, memberId);
        return ResponseEntity.ok(result);
    }
}
