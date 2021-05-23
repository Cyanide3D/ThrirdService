package com.example.ThrirdService.model;

import java.time.LocalDateTime;

public class UnloadingReport {

    private String shipName;
    private int fine;
    private int arrivalTime;
    private LocalDateTime startUnloadingTime;
    private int unloadingTime;
    private int awaitTime;
    private int cargoType;

    public UnloadingReport() {
    }

    public UnloadingReport(String shipName, int fine, int arrivalTime, LocalDateTime startUnloadingTime, int unloadingTime, int awaitTime, int cargoType) {
        this.shipName = shipName;
        this.fine = fine;
        this.arrivalTime = arrivalTime;
        this.startUnloadingTime = startUnloadingTime;
        this.unloadingTime = unloadingTime;
        this.awaitTime = awaitTime;
        this.cargoType = cargoType;
    }

    public String getShipName() {
        return shipName;
    }

    public int getCargoType() {
        return cargoType;
    }

    public int getFine() {
        return fine;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getStartUnloadingTime() {
        return startUnloadingTime;
    }

    public int getUnloadingTime() {
        return unloadingTime;
    }

    public int getAwaitTime() {
        return awaitTime;
    }

    public ReportBuilder builder() {
        return new ReportBuilder();
    }

    public static class ReportBuilder {
        private String shipName;
        private int fine;
        private int arrivalTime;
        private LocalDateTime startUnloadingTime;
        private int unloadingTime;
        private int awaitTime;
        private int cargoType;

        public ReportBuilder setCargoType(int cargoType) {
            this.cargoType = cargoType;
            return this;
        }

        public ReportBuilder setShipName(String shipName) {
            this.shipName = shipName;
            return this;
        }

        public ReportBuilder setFine(int fine) {
            this.fine = fine;
            return this;
        }

        public ReportBuilder setArrivalTime(int arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public ReportBuilder setStartUnloadingTime(LocalDateTime startUnloadingTime) {
            this.startUnloadingTime = startUnloadingTime;
            return this;
        }

        public ReportBuilder setUnloadingTime(int unloadingTime) {
            this.unloadingTime = unloadingTime;
            return this;
        }

        public ReportBuilder setAwaitTime(int awaitTime) {
            this.awaitTime = awaitTime;
            return this;
        }

        public UnloadingReport build() {
            return new UnloadingReport(shipName, fine, arrivalTime, startUnloadingTime, unloadingTime, awaitTime, cargoType);
        }
    }
}
