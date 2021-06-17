package ru.gb.hw13;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            System.out.println(c.getName() + " начал этап: " + description+"("+System.currentTimeMillis()+")");
            Thread.sleep(length / c.getSpeed() * 1000);
            System.out.println(c.getName() + " закончил этап: " + description+"("+System.currentTimeMillis()+")");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}