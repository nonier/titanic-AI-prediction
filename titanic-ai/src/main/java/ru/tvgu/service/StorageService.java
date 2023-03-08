package ru.tvgu.service;

import ru.tvgu.dao.Passenger;

import java.util.List;

public interface StorageService {

    List<Passenger> getCache();

    List<Passenger> getCacheForTeaching();

    List<Passenger> getCacheForTest(Integer count);

    void shuffleCache();
}
