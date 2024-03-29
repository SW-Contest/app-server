package com.artfolio.artfolio.business.domain;

import lombok.Getter;

@Getter
public enum SearchType {
    CREATED_AT("created_at"), LIKE("like"), CURRENT_PRICE("current_price");

    private final String searchType;

    SearchType(String searchType) {
        this.searchType = searchType;
    }
}
