package ru.tvgu.service.impl;

import org.springframework.stereotype.Service;
import ru.tvgu.dao.CabinClass;
import ru.tvgu.dao.Passenger;
import ru.tvgu.service.PassengerService;

@Service
public class PassengerServiceImpl implements PassengerService {

    @Override
    public Passenger buildPassenger(String passengerString) {
        return Passenger.builder()
                .cabinClass(CabinClass.ofOrdinal(Character.getNumericValue(passengerString.charAt(0))))
                .isAdult(1 == Character.getNumericValue(passengerString.charAt(1)))
                .isMale(1 == Character.getNumericValue(passengerString.charAt(2)))
                .isSurvived(1 == Character.getNumericValue(passengerString.charAt(3)))
                .build();
    }
}