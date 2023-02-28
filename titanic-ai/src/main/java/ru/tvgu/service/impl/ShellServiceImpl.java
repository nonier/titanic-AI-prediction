package ru.tvgu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import ru.tvgu.dao.Passenger;
import ru.tvgu.service.CacheService;
import ru.tvgu.service.ShellService;

import java.io.IOException;
import java.util.List;

@Service
@ShellComponent
@RequiredArgsConstructor
public class ShellServiceImpl implements ShellService {

    private final CacheService cacheService;

    @ShellMethod(key = "init-cache", value = "load titanic passengers data in memory")
    public void initCache() throws IOException {
        cacheService.initData();
    }

    @ShellMethod(key = "get-cache", value = "show cache data")
    public List<Passenger> getCache() {
        return cacheService.getCache();
    }
}
