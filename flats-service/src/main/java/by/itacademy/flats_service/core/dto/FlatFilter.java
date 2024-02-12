package by.itacademy.flats_service.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class FlatFilter {

    private Integer priceFrom;

    private Integer priceTo;

    private Integer bedroomsFrom;

    private Integer bedroomsTo;

    private Integer areaFrom;

    private Integer areaTo;

    private Integer[] floors;

    private Boolean photo;
}
