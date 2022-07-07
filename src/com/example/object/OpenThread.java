package com.example.object;

import com.example.pool.Stadium;

public class OpenThread extends Thread {

    private Stadium pool;
    private int threadNumber;

    public OpenThread(Stadium pool, int threadNumber) {
        this.pool = pool;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        StadiumGate gate = pool.borrowGate();
        while (gate == null) {
            try {
                OpenThread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gate = pool.borrowGate();
        }
        System.out.println("Gate(object) opened : " + gate.getGateNo() + " \t Gate process(thread) no : " + threadNumber + " was borrowed " + "hash" + gate.hashCode());

//        System.out.println("Fans enter the stadium through gate : " + gate.getGateNo());

        pool.returnGate(gate);
        System.out.println("Gate(object) closed : " + gate.getGateNo() + " \t Gate process(thread) no : " + threadNumber + " was returned " + "hash" +gate.hashCode());
    }
}
