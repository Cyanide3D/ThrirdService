package com.example.ThrirdService.service;

import com.example.ThrirdService.Crane;
import com.example.ThrirdService.model.Report;
import com.example.ThrirdService.model.Ship;
import com.example.ThrirdService.model.UnloadingReport;
import com.example.ThrirdService.simple.SimpleUnloadStarter;
import com.example.ThrirdService.simple.SimpleUnloadingReport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class SchedulerService {

    volatile LocalDateTime localDateTime = LocalDateTime.of(2021, 6, 1, 0, 0, 0, 1);

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
        Report report = new Report();
//        int fine = 50000;
//        int cranesQuantity = 0;
//
//        while (fine >= 30000 * cranesQuantity) {
//            cranesQuantity++;
//            ExecutorService executor = Executors.newFixedThreadPool(cranesQuantity);
//            createReport(report, localDateTime, schedules, executor);
//            report.make();
//            fine = report.getFine();
//            executor.shutdown();
//        }
//        report.setMinNeedfulCraneAmount(cranesQuantity);
//        report.setCranesQuantity(cranesQuantity);
//
//        sendReport(report);


        return new SimpleUnloadStarter().execute(schedules);
    }

    @SneakyThrows
    private void sendReport(Report report) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(report), headers);
        restTemplate.postForObject(schedulesUrl, request, String.class);
    }

    private synchronized void createReport(Report report, LocalDateTime localDateTime, List<Ship> ships, ExecutorService executor) throws InterruptedException, ExecutionException {
        for (Ship schedule : ships) {
            Future<UnloadingReport> result = executor.submit(new Crane(schedule, localDateTime));
            UnloadingReport unloadingReport = result.get();
            localDateTime = increaseCurrentDate(localDateTime, unloadingReport);

            report.addReport(unloadingReport);
        }
    }

    private LocalDateTime increaseCurrentDate(LocalDateTime localDateTime, UnloadingReport unloadingReport) {
        LocalDateTime awaitTime = localDateTime.plusMinutes(unloadingReport.getAwaitTime());

        return localDateTime
                .plusDays(awaitTime.getDayOfMonth())
                .plusHours(awaitTime.getHour())
                .plusMinutes(awaitTime.getMinute());
    }

}
