package com.example.ThrirdService.service;

import com.example.ThrirdService.model.Ship;
import com.example.ThrirdService.simple.SimpleUnloadStarter;
import com.example.ThrirdService.simple.SimpleUnloadingReport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Comparator;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private ObjectMapper objectMapper;
    private final String schedulesUrl = "http://localhost:8082/serialize";

    @SneakyThrows
    private List<Ship> downloadSchedules() {
        List<Ship> schedules = objectMapper.readValue(new URL(schedulesUrl), new TypeReference<List<Ship>>() {});
        schedules.sort(Comparator.comparing(Ship::getArrivalTime));

        return schedules;
    }

    @SneakyThrows
    public List<SimpleUnloadingReport> unloading() {
        List<Ship> schedules = downloadSchedules();
        List<SimpleUnloadingReport> reports = new SimpleUnloadStarter().execute(schedules);
        sendReport(reports);

        return reports;
    }

    @SneakyThrows
    private void sendReport(List<SimpleUnloadingReport> reports) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(reports), headers);
        restTemplate.postForObject(schedulesUrl, request, String.class);
    }
}
