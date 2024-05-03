package com.funds;

import java.awt.image.BufferedImage;
import java.time.Instant;

public class FundsGlobe {
    private int currentBalance;
    private int totalBalance;
    private int size;
    private Instant time;
    private BufferedImage icon;

    public FundsGlobe(int currentBalance, int totalBalance, int size, Instant time, BufferedImage icon) {
        this.currentBalance = currentBalance;
        this.totalBalance = totalBalance;
        this.size = size;
        this.time = time;
        this.icon = icon;
    }

    public int getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    public int getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(int totalBalance) {
        this.totalBalance = totalBalance;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public BufferedImage getIcon() {
        return icon;
    }

    public void setIcon(BufferedImage icon) {
        this.icon = icon;
    }
}
