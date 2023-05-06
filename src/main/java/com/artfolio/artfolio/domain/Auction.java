package com.artfolio.artfolio.domain;

import com.artfolio.artfolio.domain.audit.AuditingFields;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Auction extends AuditingFields {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long startPrice;

    @Column(nullable = false)
    private Long nowPrice;

    @Column(nullable = false, updatable = false)
    private Long finalPrice;

    @Column(nullable = false, name = "auction_like")
    private Long like;

    @Column(nullable = false)
    private Boolean isSold;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bidder_id")
    private Member bidder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_piece_id", nullable = false)
    private ArtPiece artPiece;

    @OneToMany(mappedBy = "auction")
    private final List<MemberAuction> memberAuctions = new ArrayList<>();

    @Builder
    public Auction(Long startPrice, Long nowPrice, Long finalPrice, Long like, Boolean isSold, Member bidder, ArtPiece artPiece) {
        this.startPrice = startPrice;
        this.nowPrice = nowPrice;
        this.finalPrice = finalPrice;
        this.like = like;
        this.isSold = isSold;
        this.bidder = bidder;
        this.artPiece = artPiece;
    }
}
