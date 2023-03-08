package ru.tvgu.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.tvgu.dao.Passenger;
import ru.tvgu.service.StorageService;
import ru.tvgu.service.PassengerService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    @Value("${passengers-data-file-path}")
    private String passengersDataFilePath;
    private List<Passenger> passengers = new ArrayList<>();
    private final PassengerService passengerService;

    @PostConstruct
    public void initData() throws IOException {
        File passengerDataFile = ResourceUtils.getFile(passengersDataFilePath);
        for (String str : FileUtils.readLines(passengerDataFile, StandardCharsets.UTF_8)) {
            Passenger passenger = passengerService.buildPassenger(str.replaceAll("\s", ""));
            passengers.add(passenger);
        };
    }

    @Override
    public List<Passenger> getCache() {
        return passengers;
    }

    @Override
    public List<Passenger> getCacheForTeaching() {
        return passengers.subList(0, passengers.size() - 100);
    }

    @Override
    public List<Passenger> getCacheForTest(Integer count) {
        return passengers.subList(passengers.size() - count, passengers.size());
    }

    @Override
    public void shuffleCache() {
        Collections.shuffle(passengers);
    }
}