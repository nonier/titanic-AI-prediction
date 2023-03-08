package ru.tvgu.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;
import ru.tvgu.dao.Passenger;
import ru.tvgu.service.PerceptronService;
import ru.tvgu.service.StorageService;
import ru.tvgu.service.ShellService;

import java.util.List;

@Service
@ShellComponent
@RequiredArgsConstructor
public class ShellServiceImpl implements ShellService {

    private final StorageService storageService;
    private final PerceptronService perceptronService;

    @ShellMethod(key = "get-cache", value = "show cache data")
    public List<Passenger> getCache() {
        return storageService.getCache();
    }

    @ShellMethod(key = "teach", value = "teaching ai")
    public void teach() {
        perceptronService.teach();
    }

    @ShellMethod(key = "test", value = "test ai")
    public void test(@ShellOption(defaultValue = "10") String count) {
        perceptronService.test(Integer.valueOf(count));
    }

    @ShellMethod(key = "shuffle")
    public void shuffleCache() {
        storageService.shuffleCache();
    }
}
