package ru.tvgu.dao;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

    private Integer cabinClass;
    private Integer isAdult;
    private Integer isMale;
    private Integer isSurvived;
}