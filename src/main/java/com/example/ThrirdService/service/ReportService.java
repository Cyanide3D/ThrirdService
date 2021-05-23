package com.example.ThrirdService.service;

import com.example.ThrirdService.model.Report;
import com.example.ThrirdService.simple.SimpleShipUnloadingReport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class ReportService {

    private static final ReportService instance = new ReportService();
    private final List<SimpleShipUnloadingReport> reports = new ArrayList<>();

    public void add(SimpleShipUnloadingReport report) {
        reports.add(report);
    }

    public synchronized List<SimpleShipUnloadingReport> get() {
        ArrayList<SimpleShipUnloadingReport> simpleShipUnloadingReports = new ArrayList<>(reports);
        reports.clear();
        return simpleShipUnloadingReports;
    }

    public void clear() {
        reports.clear();
    }

    public static ReportService getInstance() {
        return instance;
    }

}