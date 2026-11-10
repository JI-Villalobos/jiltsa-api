package com.jiltsa.admin.files.domain;

/** The entities files can be attached to. Each value names the key prefix its objects live under. */
public enum FileOwnerType {
    PROMOTION_ITEM("promotion-items"),
    /** Identity documents of an employee; every file must say its documentType. */
    SELLER_PROFILE("seller-profiles");

    private final String keyPrefix;

    FileOwnerType(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String keyPrefix() {
        return keyPrefix;
    }
}
