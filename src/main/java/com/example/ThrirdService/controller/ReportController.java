package com.example.ThrirdService.controller;

import com.example.ThrirdService.model.Report;
import com.example.ThrirdService.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private SchedulerService schedulerService;

    @GetMapping
    public Report report() {
        return schedulerService.unloading();
    }

}
