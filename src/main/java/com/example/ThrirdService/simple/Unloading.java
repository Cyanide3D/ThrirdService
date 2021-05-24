package com.example.ThrirdService.simple;

import com.example.ThrirdService.model.Cargo;
import com.example.ThrirdService.model.Ship;
import com.example.ThrirdService.service.ReportService;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Unloading implements Callable<SimpleUnloadingReport> {
    public static int totalFine = 0;
    public static int averageDelay = 0;
    public static int delay = 0;
    private int fine = 0;
    private List<Ship> ships;
    private List<Crane> cranes;
    private Cargo.CargoType cargoType;
    private static int amountOfShips = 0;
    private int Avg = 0;
    private static int signal = 0;
    //    public int sizeOfQueue = 0;
    private int cranesQuantity = 0;

    public int getTotalFine() {
        return totalFine;
    }

    public Unloading(List<Ship> ships) {
        this.ships = ships;
        amountOfShips += ships.size();
    }

    public int getCranesQuantity() {
        return cranesQuantity;
    }

    public int getFine() {
        return fine;
    }

    @SneakyThrows
    @Override
    public SimpleUnloadingReport call() {
        SimpleUnloadingReport report = new SimpleUnloadingReport();
        int cranePrice = 30000;
        while (fine >= cranePrice * cranesQuantity) {
//            if (cranesQuantity == 1) break;
            ConcurrentLinkedQueue<Ship> queueOfShips = new ConcurrentLinkedQueue<>(ships);
            fine = 0;
            cranesQuantity++;

            Ship ship = queueOfShips.peek();
            if (ship != null) {
                cargoType = ship.cargo.getCargoType();
                cranes = new ArrayList<>(cranesQuantity);

                ExecutorService executor = Executors.newFixedThreadPool(cranesQuantity);
                for (int i = 0; i < cranesQuantity; i++) {
                    Crane crane = new Crane(queueOfShips);
                    cranes.add(crane);
                }
                try {
                    List<Future<SimpleShipUnloadingReport>> result = executor.invokeAll(cranes);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                executor.shutdown();

                for (Crane crane : cranes) {
                    fine += crane.getCraneFine();
                }

                Avg = Crane.staticSizeOfQueue / cranes.size();
            }
        }
        totalFine += fine;


        System.out.println("====================\n" + cargoType + "\n" + fine + "\nAmount of cranes: " + cranesQuantity
                + "\nTotal fine: " + totalFine + "\nAverage Size Of Queue: " + Crane.staticSizeOfQueue / cranes.size()
                + "\nAmount Of Ships: " + amountOfShips + "\nMax delay: " + Crane.staticDelay + "\nAverage delay " + Crane.staticAverageDelay + "\n====================");
        ReportService reportService = ReportService.getInstance();
        report.setCargoType(cargoType);
        report.setCranesQuantity(cranesQuantity);
        report.setTotalFine(totalFine);
        report.setQueueSize(Crane.staticSizeOfQueue / cranes.size());
        report.setAmountOfShips(amountOfShips);
        report.setStaticDelay(Crane.staticDelay);
        report.setStaticAverageDelay(Crane.staticAverageDelay);
        report.setReports(reportService.get());

        return report;
    }
}