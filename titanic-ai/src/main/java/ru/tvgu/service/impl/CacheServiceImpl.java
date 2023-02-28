package ru.tvgu.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import ru.tvgu.dao.Passenger;
import ru.tvgu.service.CacheService;
import ru.tvgu.service.PassengerService;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    @Value("${passengers-data-file-path}")
    private String passengersDataFilePath;
    private List<Passenger> passengers = new ArrayList<>();
    private final PassengerService passengerService;


    @Override
    public void initData() throws IOException {
        File file = ResourceUtils.getFile(passengersDataFilePath);
        passengers = FileUtils.readLines(file, StandardCharsets.UTF_8)
                .stream()
                .map(s -> s.replaceAll("\s", ""))
                .map(passengerService::buildPassenger)
                .toList();
    }

    @Override
    public List<Passenger> getCache() {
        return passengers;
    }
}