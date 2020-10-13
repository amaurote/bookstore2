package com.amaurote.bookstore.domain.enums;

// Used ISO 639-1 codes
public enum Language {
    EN("English"),
    SK("Slovak"),
    CZ("Czech"),
    DE("German"),
    FI("Finnish");

    private final String codeName;

    Language(String language) {
        codeName = language;
    }

    public String getCodeName() {
        return codeName;
    }
}
