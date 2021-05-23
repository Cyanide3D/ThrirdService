package com.example.ThrirdService.model;

import com.example.ThrirdService.service.CraneHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Report {

    @JsonIgnore
    private List<UnloadingReport> reports;
    private int cranesQuantity;
    private int amountOfShips;
    private int avgAwaitTime;
    private int maxUnloadTime;
    private int avgUnloadTime;
    private int fine;
    private int minNeedfulLiquidCraneAmount;
    private int minNeedfulLooseCraneAmount;
    private int minNeedfulContainerCraneAmount;

    public Report() {
        reports = new ArrayList<>();
    }

    public void addReport(UnloadingReport report) {
        reports.add(report);
    }

    public void make() {
        amountOfShips = reports.size();
        cranesQuantity = CraneHolder.getAllAmount();

        for (UnloadingReport report : reports) {
            fine += report.getFine();
            avgAwaitTime += report.getAwaitTime();
            avgUnloadTime += report.getUnloadingTime();

            if (maxUnloadTime < report.getUnloadingTime()) {
                maxUnloadTime = report.getUnloadingTime();
            }
        }

        avgAwaitTime /= amountOfShips;
        avgUnloadTime /= amountOfShips;
    }

    public List<UnloadingReport> getReports() {
        return reports;
    }

    public void setReports(List<UnloadingReport> reports) {
        this.reports = reports;
    }

    public int getCranesQuantity() {
        return cranesQuantity;
    }

    public void setCranesQuantity(int cranesQuantity) {
        this.cranesQuantity = cranesQuantity;
    }

    public int getAmountOfShips() {
        return amountOfShips;
    }

    public void setAmountOfShips(int amountOfShips) {
        this.amountOfShips = amountOfShips;
    }

    public int getAvgAwaitTime() {
        return avgAwaitTime;
    }

    public void setAvgAwaitTime(int avgAwaitTime) {
        this.avgAwaitTime = avgAwaitTime;
    }

    public int getMaxUnloadTime() {
        return maxUnloadTime;
    }

    public void setMaxUnloadTime(int maxUnloadTime) {
        this.maxUnloadTime = maxUnloadTime;
    }

    public int getAvgUnloadTime() {
        return avgUnloadTime;
    }

    public void setAvgUnloadTime(int avgUnloadTime) {
        this.avgUnloadTime = avgUnloadTime;
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public int getMinNeedfulLiquidCraneAmount() {
        return minNeedfulLiquidCraneAmount;
    }

    public void setMinNeedfulLiquidCraneAmount(int minNeedfulLiquidCraneAmount) {
        this.minNeedfulLiquidCraneAmount = minNeedfulLiquidCraneAmount;
    }

    public int getMinNeedfulLooseCraneAmount() {
        return minNeedfulLooseCraneAmount;
    }

    public void setMinNeedfulLooseCraneAmount(int minNeedfulLooseCraneAmount) {
        this.minNeedfulLooseCraneAmount = minNeedfulLooseCraneAmount;
    }

    public int getMinNeedfulContainerCraneAmount() {
        return minNeedfulContainerCraneAmount;
    }

    public void setMinNeedfulContainerCraneAmount(int minNeedfulContainerCraneAmount) {
        this.minNeedfulContainerCraneAmount = minNeedfulContainerCraneAmount;
    }
}
