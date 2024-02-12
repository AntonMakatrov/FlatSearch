package by.itacademy.flats_service.core.entity;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(schema = "flats", name = "flats")
public class Flat extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    private String description;

    private Integer bedrooms;

    private Integer area;

    private Integer price;

    private Integer floor;

    @Type(StringArrayType.class)
    @Column(name = "photo_urls", columnDefinition = "text[]")
    private String[] photoUrls;

    private String originalUrl;

    public Flat() {
    }

    public Flat(OfferType offerType,
                String description,
                Integer bedrooms,
                Integer area,
                Integer price,
                Integer floor,
                String[] photoUrls,
                String originalUrl) {
        this.offerType = offerType;
        this.description = description;
        this.bedrooms = bedrooms;
        this.area = area;
        this.price = price;
        this.floor = floor;
        this.photoUrls = photoUrls;
        this.originalUrl = originalUrl;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public Flat setOfferType(OfferType offerType) {
        this.offerType = offerType;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Flat setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public Flat setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
        return this;
    }

    public Integer getArea() {
        return area;
    }

    public Flat setArea(Integer area) {
        this.area = area;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public Flat setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public Integer getFloor() {
        return floor;
    }

    public Flat setFloor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public Flat setPhotoUrls(String[] photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Flat setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
        return this;
    }
}
