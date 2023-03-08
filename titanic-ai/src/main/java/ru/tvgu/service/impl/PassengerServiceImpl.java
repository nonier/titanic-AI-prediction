package ru.tvgu.service.impl;

import org.springframework.stereotype.Service;
import ru.tvgu.dao.Passenger;
import ru.tvgu.service.PassengerService;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Override
    public Passenger buildPassenger(String passengerString) {
        return Passenger.builder()
                .cabinClass(Character.getNumericValue(passengerString.charAt(0)))
                .isAdult(Character.getNumericValue(passengerString.charAt(1)))
                .isMale(Character.getNumericValue(passengerString.charAt(2)))
                .isSurvived(Character.getNumericValue(passengerString.charAt(3)))
                .build();
    }
}