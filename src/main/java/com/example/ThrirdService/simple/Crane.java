package com.example.ThrirdService.simple;

import com.example.ThrirdService.model.Ship;
import com.example.ThrirdService.service.ReportService;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Crane implements Callable<SimpleShipUnloadingReport> {

    private int craneFine = 0;
    private final ConcurrentLinkedQueue<Ship> ships;
    private int size = 0;
    public int sizeOfQueue = 0;
    public static int staticDelay = 0;
    public static int staticSizeOfQueue = 0;
    private int maxDelay = 0;
    private int totalDelay = 0;
    private int delayShipsCount;
    public static int staticAverageDelay;
    ReportService reportService = ReportService.getInstance();

    public int getCraneFine() {
        return craneFine;
    }

    public Crane(ConcurrentLinkedQueue<Ship> ships) {
        this.ships = ships;
        size += ships.size();
    }

    public int getSize() {
        return size;
    }

    public SimpleShipUnloadingReport call() throws InterruptedException {
        SimpleShipUnloadingReport report = new SimpleShipUnloadingReport();
        int currentTime = -43200;
        staticDelay++;
        int smeshenie = 0;
        Ship firstShip = ships.peek();
        if (firstShip != null) {
            System.out.println("-----------------------\nName: " + firstShip.getName() + "\nCargo type: " +
                    firstShip.cargo.getCargoType() + "\nArrival time in minutes: " + firstShip.getArrivalTime() + "\nMonth: "
                    + firstShip.getMonth() + "\nDay: " + firstShip.getDay() + "\nHours: " + firstShip.getH() + "\nMinutes: "
                    + firstShip.getM() + "\nDelay: " + 0 + "\nReal begin time: " + firstShip.getArrivalTime() + "\nUnloading time: " + firstShip.getUnloadingTime());
            report.setName(firstShip.getName());
            report.setCargoType(firstShip.cargo.getCargoType());
            report.setArrivalTime(firstShip.getArrivalTime());
            report.setMonth(firstShip.getMonth());
            report.setDay(firstShip.getDay());
            report.setHour(firstShip.getH());
            report.setMinute(firstShip.getM());
            report.setDelay(0);
            report.setBeginTime(firstShip.getArrivalTime());
            report.setUnloadingTime(firstShip.getUnloadingTime());
            reportService.add(report);
        }
        while (!ships.isEmpty()) {
            Ship currentShip = ships.peek();
            ships.remove(currentShip);
            currentTime = Math.max(currentTime, currentShip.getArrivalTime());
            currentTime += currentShip.getUnloadingTime();
            Ship nextShip = ships.peek();
            if (nextShip == null) {
                break;
            }
            if (nextShip.getArrivalTime() < currentTime) {
                delayShipsCount++;
                if ((currentTime - nextShip.getArrivalTime() % 60) == 0) {
                    craneFine += 100 * ((currentTime - nextShip.getArrivalTime()) / 60);
                }
                craneFine += 100 * ((currentTime - nextShip.getArrivalTime()) / 60 + 1);
                System.out.println("-----------------------\nName: " + nextShip.getName() + "\nCargo type: " +
                        nextShip.cargo.getCargoType() + "\nArrival time in minutes: " + nextShip.getArrivalTime() +
                        "\nMonth: " + nextShip.getMonth() + "\nDay: " + nextShip.getDay() + "\nHours: " + nextShip.getH()
                        + "\nMinutes: " + nextShip.getM() + "\nDelay: " + (smeshenie + ((currentShip.getArrivalTime() + currentShip.getUnloadingTime())
                        - nextShip.getArrivalTime())) + "\nReal begin time: " + (smeshenie + (currentShip.getArrivalTime() + currentShip.getUnloadingTime())) +
                        "\nUnloading time: " + nextShip.getUnloadingTime());
                report.setName(nextShip.getName());
                report.setCargoType(nextShip.cargo.getCargoType());
                report.setArrivalTime(nextShip.getArrivalTime());
                report.setMonth(nextShip.getMonth());
                report.setDay(nextShip.getDay());
                report.setHour(nextShip.getH());
                report.setMinute(nextShip.getM());
                report.setDelay((smeshenie + ((currentShip.getArrivalTime() + currentShip.getUnloadingTime()) - nextShip.getArrivalTime())));
                report.setBeginTime((smeshenie + (currentShip.getArrivalTime() + currentShip.getUnloadingTime())));
                report.setUnloadingTime(nextShip.getUnloadingTime());
                reportService.add(report);
                maxDelay = (smeshenie + ((currentShip.getArrivalTime() + currentShip.getUnloadingTime())
                        - nextShip.getArrivalTime()));
                totalDelay += (smeshenie + ((currentShip.getArrivalTime() + currentShip.getUnloadingTime())
                        - nextShip.getArrivalTime()));
                smeshenie += (currentShip.getArrivalTime() + currentShip.getUnloadingTime()) - nextShip.getArrivalTime();
                sizeOfQueue += 1;

            } else {
                System.out.println("-----------------------\nName: " + nextShip.getName() + "\nCargo type: " +
                        nextShip.cargo.getCargoType() + "\nArrival time in minutes: " + nextShip.getArrivalTime() +
                        "\nMonth: " + nextShip.getMonth() + "\nDay: " + nextShip.getDay() + "\nHours: " + nextShip.getH()
                        + "\nMinutes: " + nextShip.getM() + "\nDelay: " + 0 + "\nReal begin time: " + nextShip.getArrivalTime() +
                        "\nUnloading time: " + nextShip.getUnloadingTime());
//                smeshenie = 0;
                report.setName(nextShip.getName());
                report.setCargoType(nextShip.cargo.getCargoType());
                report.setArrivalTime(nextShip.getArrivalTime());
                report.setMonth(nextShip.getMonth());
                report.setDay(nextShip.getDay());
                report.setHour(nextShip.getH());
                report.setMinute(nextShip.getM());
                report.setDelay(0);
                report.setBeginTime(nextShip.getArrivalTime());
                report.setUnloadingTime(nextShip.getUnloadingTime());
                reportService.add(report);
            }
            Thread.sleep(1);
        }
        staticSizeOfQueue = sizeOfQueue;
        staticDelay = maxDelay;
        if (delayShipsCount > 0) {
            staticAverageDelay = totalDelay / delayShipsCount;
        }
        return report;
    }
}

