package com.example.ThrirdService.service;

import org.springframework.stereotype.Service;

@Service
public class CraneHolder {

    private static int looseAmount = 1;
    private static int liquidAmount = 1;
    private static int containerAmount = 1;

    public static int getLooseAmount() {
        return looseAmount;
    }

    public static int getAllAmount() {
        return looseAmount + liquidAmount + containerAmount;
    }

    public static void setLooseAmount(int looseAmount) {
        CraneHolder.looseAmount = looseAmount;
    }

    public static int getLiquidAmount() {
        return liquidAmount;
    }

    public static void setLiquidAmount(int liquidAmount) {
        CraneHolder.liquidAmount = liquidAmount;
    }

    public static int getContainerAmount() {
        return containerAmount;
    }

    public static void setContainerAmount(int containerAmount) {
        CraneHolder.containerAmount = containerAmount;
    }
}
