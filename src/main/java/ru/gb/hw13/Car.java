package ru.gb.hw13;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Car implements Runnable {
    private final CyclicBarrier startBarrier;
    private final CountDownLatch countDownLatch;
    private final  CyclicBarrier finishBarrier;

    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier startBarrier, CountDownLatch countDownLatch, CyclicBarrier finishBarrier) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.startBarrier = startBarrier;
        this.countDownLatch = countDownLatch;
        this.finishBarrier = finishBarrier;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            startBarrier.await(10, TimeUnit.SECONDS);//максимальное время ожидания других участников 10 секунд
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (countDownLatch.getCount()==MainClass.CARS_COUNT){ //первый?
            System.out.println("Победил "+name+" ");
        }

        countDownLatch.countDown();

        try {
            finishBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}