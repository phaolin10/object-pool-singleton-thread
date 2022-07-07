package com.example.pool;

import com.example.object.OpenThread;
import com.example.object.StadiumGate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StadiumDemo {
    private Stadium pool;
    private AtomicLong processNo = new AtomicLong(0);

    public void setUp() {
        pool = new Stadium(10) {
            @Override
            protected StadiumGate openGate() {
                return new StadiumGate(processNo.incrementAndGet());
            }
        };
    }

    public void tearDown() {
        pool.closeStadium();
    }

    public void testStadium() {
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        for (int i = 1; i <= 15; i++) {
            executorService.execute(new OpenThread(pool, i));
        }

         executorService.shutdown();

        try {
            executorService.awaitTermination(50, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
