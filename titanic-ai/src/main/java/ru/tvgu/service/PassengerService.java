package ru.tvgu.service;

import ru.tvgu.dao.Passenger;

public interface PassengerService {

    Passenger buildPassenger(String passengerString);
}
