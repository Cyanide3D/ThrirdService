package com.example.ThrirdService;

import com.example.ThrirdService.model.UnloadingReport;
import com.example.ThrirdService.model.Ship;

import java.time.*;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.Callable;

public class Crane implements Callable<UnloadingReport> {

    private final Ship ship;
    private final LocalDateTime currentDate;

    public Crane(Ship ship, LocalDateTime currentDate) {
        this.currentDate = currentDate;
        this.ship = ship;
    }

    @Override
    public UnloadingReport call() {
        int awaitTime = Math.abs(currentDate.getHour() - ship.getArrivalTime());

        return new UnloadingReport().builder()
                .setArrivalTime(ship.getArrivalTime())
                .setShipName(ship.getName())
                .setStartUnloadingTime(currentDate)
                .setAwaitTime(awaitTime)
                .setUnloadingTime(ship.getUnloadingTime())
                .setFine(awaitTime * 100)
                .setCargoType(ship.getCranePerformance())
                .build();
    }

}
