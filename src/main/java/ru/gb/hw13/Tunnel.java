package ru.gb.hw13;

import java.sql.Time;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private final Semaphore tunnelSemaphore;

    public Tunnel(Semaphore tunnelSemaphore) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.tunnelSemaphore = tunnelSemaphore;
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description+"("+System.currentTimeMillis()+")");
                tunnelSemaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description+"("+System.currentTimeMillis()+")");
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description+"("+System.currentTimeMillis()+")");
                tunnelSemaphore.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
