package by.itacademy.flats_service.core.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(schema = "flats", name = "dead_flats")
public class DeadFlat extends BaseEntity{

    private String originalUrl;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    private String exceptionMessage;

    private String rawHtml;

    public DeadFlat() {
    }

    public DeadFlat(String originalUrl, OfferType offerType, String exceptionMessage, String rawHtml) {
        this.originalUrl = originalUrl;
        this.offerType = offerType;
        this.exceptionMessage = exceptionMessage;
        this.rawHtml = rawHtml;
    }

    public DeadFlat(String originalUrl, String exceptionMessage, String rawHtml) {
        this.originalUrl = originalUrl;
        this.exceptionMessage = exceptionMessage;
        this.rawHtml = rawHtml;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public DeadFlat setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public DeadFlat setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public String getRawHtml() {
        return rawHtml;
    }

    public DeadFlat setRawHtml(String rawHtml) {
        this.rawHtml = rawHtml;
        return this;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public DeadFlat setOfferType(OfferType offerType) {
        this.offerType = offerType;
        return this;
    }
}
