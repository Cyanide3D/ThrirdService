package com.example.ThrirdService;

import com.example.ThrirdService.model.Ship;
import com.example.ThrirdService.model.UnloadingReport;

import java.time.LocalDateTime;
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
        LocalDateTime arrivalTime = currentDate.plusMinutes(ship.getArrivalTime());

        return new UnloadingReport().builder()
                .setArrivalTime(ship.getArrivalTime())
                .setShipName(ship.getName())
                .setStartUnloadingTime(currentDate)
                .setAwaitTime(100)
                .setUnloadingTime(ship.getUnloadingTime())
                .setFine((100/60) * 100)
                .setCargoType(ship.getCranePerformance())
                .build();
    }

}
