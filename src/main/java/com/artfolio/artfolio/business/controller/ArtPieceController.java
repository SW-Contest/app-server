package com.artfolio.artfolio.business.controller;

import com.artfolio.artfolio.business.dto.ArtPieceDto;
import com.artfolio.artfolio.business.dto.ImageDto;
import com.artfolio.artfolio.business.service.ArtPieceService;
import com.artfolio.artfolio.business.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/art_piece")
@RequiredArgsConstructor
@RestController
public class ArtPieceController {
    private final ArtPieceService artPieceService;
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<Long> createArtPiece(@RequestBody ArtPieceDto.CreationReq req) {
        return new ResponseEntity<>(artPieceService.createArtPiece(req), HttpStatus.CREATED);
    }

    @GetMapping("/{artPieceId}")
    public ResponseEntity<ArtPieceDto.ArtPieceInfoRes> getArtPiece(@PathVariable("artPieceId") Long artPieceId) {
        return ResponseEntity.ok(artPieceService.getArtPiece(artPieceId));
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteArtPiece(@RequestBody ArtPieceDto.DeletionReq req) {
        return ResponseEntity.ok(artPieceService.deleteArtPiece(req));
    }

    @PatchMapping("/title")
    public ResponseEntity<Long> updateArtPieceTitle(@RequestBody ArtPieceDto.UpdateTitleReq req) {
        return ResponseEntity.ok(artPieceService.updateTitle(req));
    }

    @PatchMapping("/content")
    public ResponseEntity<Long> updateArtPieceContent(@RequestBody ArtPieceDto.UpdateContentReq req) {
        return ResponseEntity.ok(artPieceService.updateContent(req));
    }

    @PatchMapping("/like")
    public ResponseEntity<Integer> updateArtPieceLike(@RequestBody ArtPieceDto.UpdateLikeReq req) {
        return ResponseEntity.ok(artPieceService.updateLike(req));
    }

    @PostMapping("/image")
    public ResponseEntity<Long> uploadArtPiecePhoto(
            @RequestParam("artistId") Long artistId,
            @RequestParam("artPieceId") Long artPieceId,
            @RequestParam("files") MultipartFile[] files
    ) {
        Long result = imageService.uploadImage(artistId, artPieceId, files);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/image")
    public ResponseEntity<Long> deleteArtPiecePhoto(@RequestBody ImageDto.DeleteReq req) {
        Long result = imageService.deleteFile(req);
        return ResponseEntity.ok(result);
    }
}
