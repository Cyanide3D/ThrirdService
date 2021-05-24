package com.example.ThrirdService.service;

import com.example.ThrirdService.simple.SimpleShipUnloadingReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ReportService {

    private static final ReportService instance = new ReportService();
    private final List<SimpleShipUnloadingReport> reports = Collections.synchronizedList(new ArrayList<>());

    public void add(SimpleShipUnloadingReport report) {
        reports.add(report);
    }

    public synchronized List<SimpleShipUnloadingReport> get() {
        List<SimpleShipUnloadingReport> simpleShipUnloadingReports = new ArrayList<>(reports);
        reports.clear();
        return simpleShipUnloadingReports;
    }

    public static ReportService getInstance() {
        return instance;
    }

}
