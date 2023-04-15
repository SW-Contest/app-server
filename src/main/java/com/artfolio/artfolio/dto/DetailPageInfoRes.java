package com.artfolio.artfolio.dto;

import com.artfolio.artfolio.domain.ArtPiece;
import com.artfolio.artfolio.domain.ArtPiecePhoto;
import com.artfolio.artfolio.domain.Auction;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@JsonPropertyOrder({
        "creatorName",        /* 작가 이름 */
        "artPieceTitle",      /* 작품 제목 */
        "artPieceContent",    /* 작품 소개글 */
        "artPieceLike",       /* 작품 좋아요 개수 */
        "auctionStartPrice",  /* 경매 시작가 */
        "auctionStartTime",   /* 경매 시작 시간 */
        "photoLength",        /* 작품 사진 목록 개수 */
        "artPiecePhotoPaths"  /* 작품 사진 경로 목록 */
})
public record DetailPageInfoRes(
    @JsonProperty("photoPaths")
    Set<String> paths,
    Integer photoLength,
    String creatorName,
    String artPieceTitle,
    String artPieceContent,
    Long artPieceLike,
    Long auctionStartPrice,
    LocalDateTime auctionStartTime
) {
    public static DetailPageInfoRes of(Auction auction) {
        ArtPiece piece = auction.getArtPiece();

        return new DetailPageInfoRes(
                piece.getArtPiecePhotos()
                        .stream()
                        .map(ArtPiecePhoto::getFilePath)
                        .collect(Collectors.toSet()),
                piece.getArtPiecePhotos().size(),
                piece.getCreator().getName(),
                piece.getTitle(),
                piece.getContent(),
                piece.getLike(),
                auction.getStartPrice(),
                auction.getCreatedAt()
        );
    }
}
