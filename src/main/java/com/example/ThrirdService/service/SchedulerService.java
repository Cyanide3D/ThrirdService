package com.example.ThrirdService.service;

import com.example.ThrirdService.Crane;
import com.example.ThrirdService.model.Report;
import com.example.ThrirdService.model.UnloadingReport;
import com.example.ThrirdService.model.Ship;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class SchedulerService {

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    private List<Ship> downloadSchedules() {
        List<Ship> schedules = objectMapper.readValue(new URL("http://localhost:8082/serialize"), new TypeReference<List<Ship>>() {});
        schedules.sort(Comparator.comparing(Ship::getArrivalTime));

        return schedules;
    }

    @SneakyThrows
    public Report unloading() {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<Ship> schedules = downloadSchedules();
        Report report = new Report();

        List<Ship> loose = schedules.stream().filter(ship -> ship.getCranePerformance() == 1).collect(Collectors.toList());
        List<Ship> liquid = schedules.stream().filter(ship -> ship.getCranePerformance() == 2).collect(Collectors.toList());
        List<Ship> container = schedules.stream().filter(ship -> ship.getCranePerformance() == 3).collect(Collectors.toList());

        unloadContainer(report, localDateTime, container);
        unloadLoose(report, localDateTime, loose);
        unloadLiquid(report, localDateTime, liquid);

        report.make();
        return report;
    }

    private void unloadLoose(Report report, LocalDateTime localDateTime, List<Ship> ships) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(CraneHolder.getLooseAmount());
        createReport(report, localDateTime, ships, executor);
    }

    private void unloadLiquid(Report report, LocalDateTime localDateTime, List<Ship> ships) throws ExecutionException, InterruptedException  {
        ExecutorService executor = Executors.newFixedThreadPool(CraneHolder.getLiquidAmount());
        createReport(report, localDateTime, ships, executor);
    }

    private void unloadContainer(Report report, LocalDateTime localDateTime, List<Ship> ships) throws ExecutionException, InterruptedException  {
        ExecutorService executor = Executors.newFixedThreadPool(CraneHolder.getContainerAmount());
        createReport(report, localDateTime, ships, executor);
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
