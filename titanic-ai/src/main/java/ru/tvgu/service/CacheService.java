package ru.tvgu.service;

import ru.tvgu.dao.Passenger;

import java.io.IOException;
import java.util.List;

public interface CacheService {

    void initData() throws IOException;

    List<Passenger> getCache();
}
