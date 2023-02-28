package ru.tvgu.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

    private CabinClass cabinClass;
    private Boolean isAdult;
    private Boolean isMale;
    private Boolean isSurvived;

    public int getCabinClass() {
        return cabinClass.ordinal();
    }

}
