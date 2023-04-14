package com.kd.assignment.controller;

import com.kd.assignment.dto.DataRequest;
import com.kd.assignment.service.DataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
@Validated
public class DataController {
    private final DataService dataService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createData(@Valid @RequestBody DataRequest request) {
        dataService.setData(request);
    }

    @GetMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    public String getValue(@PathVariable String key) {
        return dataService.getValue(key);
    }

    @DeleteMapping("/{key}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteValue(@PathVariable("key") String key) {
        dataService.removeValue(key);
    }

    @GetMapping("/{key}/count")
    @ResponseStatus(HttpStatus.OK)
    public long getCount(@PathVariable("key") String key) {
        return dataService.countData(key);
    }
}
