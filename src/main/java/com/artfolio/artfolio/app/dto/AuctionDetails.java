package com.artfolio.artfolio.app.dto;

import com.artfolio.artfolio.app.domain.ArtPiecePhoto;
import com.artfolio.artfolio.app.domain.Member;
import com.artfolio.artfolio.app.domain.redis.AuctionBidInfo;
import com.artfolio.artfolio.app.domain.redis.RealTimeAuctionInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class AuctionDetails {
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Res {
        private ArtistInfo artistInfo;
        private AuctionInfo auctionInfo;
        private List<BidderInfo> bidderInfos;

        public static Res of(RealTimeAuctionInfo realTimeAuctionInfo, List<AuctionBidInfo> bidInfo, List<ArtPiecePhoto> paths, Member artist) {
            ArtistInfo artistInfo = ArtistInfo.builder()
                    .id(artist.getId())
                    .like(artist.getLike())
                    .name(artist.getUsername())
                    .email(artist.getEmail())
                    .photoPath(artist.getProfilePhoto())
                    .build();

            List<String> photoPaths = paths.stream()
                    .map(ArtPiecePhoto::getFilePath)
                    .toList();

            AuctionInfo auctionInfo = AuctionInfo.builder()
                    .id(realTimeAuctionInfo.getId())
                    .title(realTimeAuctionInfo.getAuctionTitle())
                    .content(realTimeAuctionInfo.getAuctionContent())
                    .startPrice(realTimeAuctionInfo.getAuctionStartPrice())
                    .currentPrice(realTimeAuctionInfo.getAuctionCurrentPrice())
                    .like(realTimeAuctionInfo.getAuctionLike())
                    .createdAt(realTimeAuctionInfo.getCreatedAt())
                    .likeMembers(realTimeAuctionInfo.getLikeMembers())
                    .photoPaths(photoPaths)
                    .build();

            List<BidderInfo> bidderInfos = bidInfo.stream()
                    .map(BidderInfo::of)
                    .sorted((o1, o2) -> o2.getBidDate().compareTo(o1.getBidDate()))
                    .toList();

            return Res.builder()
                    .artistInfo(artistInfo)
                    .auctionInfo(auctionInfo)
                    .bidderInfos(bidderInfos)
                    .build();
        }
    }

    @Builder @Getter
    @AllArgsConstructor
    private static class ArtistInfo {
        private Long id;
        private Integer like;
        private String name;
        private String email;
        private String photoPath;
    }

    @Builder @Getter
    @AllArgsConstructor
    private static class AuctionInfo {
        private String id;
        private String title;
        private String content;
        private Long startPrice;
        private Long currentPrice;
        private Integer like;
        private LocalDateTime createdAt;
        private Set<Long> likeMembers;
        private List<String> photoPaths;
    }

    @Builder @Getter
    @AllArgsConstructor
    private static class BidderInfo {
        private Long id;
        private String name;
        private String email;
        private String photoPath;
        private Integer like;
        private Long bidPrice;
        private LocalDateTime bidDate;

        public static BidderInfo of(AuctionBidInfo info) {
            return BidderInfo.builder()
                    .id(info.getBidderId())
                    .name(info.getName())
                    .email(info.getEmail())
                    .photoPath(info.getProfilePhotoPath())
                    .like(info.getLike())
                    .bidPrice(info.getBidPrice())
                    .bidDate(info.getBidDate())
                    .build();
        }
    }
}
