package com.example.ThrirdService.simple;

import com.example.ThrirdService.model.Cargo;
import com.example.ThrirdService.model.Ship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SimpleUnloadStarter {

    public List<SimpleUnloadingReport> execute(List<Ship> shipListFromSchedule) {
        List<SimpleUnloadingReport> reports = Collections.synchronizedList(new ArrayList<>());
        int threadsNumber = 3;
        Cargo.CargoType types[] = Cargo.CargoType.values();
        List<Unloading> unloadSchedule = new ArrayList<>(3);
        for (int i = 0; i < threadsNumber; i++) {
            List<Ship> cargos = new ArrayList<>();
            for (Ship ship : shipListFromSchedule) {
                if (ship.getCargo().getCargoType() == types[i]) {
                    cargos.add(ship);
                }
            }
            Unloading cargosUnloading = new Unloading(cargos);
            unloadSchedule.add(cargosUnloading);
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
        try {
            List<Future<SimpleUnloadingReport>> result = executor.invokeAll(unloadSchedule);
            Thread.sleep(2000);
            for (Future<SimpleUnloadingReport> simpleUnloadingReportFuture : result) {
                reports.add(simpleUnloadingReportFuture.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();

        return reports;
    }

}
