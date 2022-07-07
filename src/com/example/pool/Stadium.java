package com.example.pool;

import com.example.object.StadiumGate;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Stadium {
    private static ConcurrentLinkedQueue<StadiumGate> gatePool;
    private ScheduledExecutorService scheduledExecutorService;

    public Stadium(final int minObject) {
        initializeGatePool(minObject);
    }

    private Stadium() {

    }

    public static ConcurrentLinkedQueue<StadiumGate> getInstance() {
        synchronized (Stadium.class) {
            if (gatePool == null) {
                gatePool = new ConcurrentLinkedQueue<StadiumGate>();
            }
            return gatePool;
        }
    }

    protected abstract StadiumGate openGate();

    private void initializeGatePool(final int minObject) {
        gatePool = getInstance();
        for (int i = 0; i < minObject; i++) {
            gatePool.add(openGate());
        }
    }

    public StadiumGate borrowGate() {
        StadiumGate gate;
        if ((gate = gatePool.poll()) == null) {
            return null;
        }
        return gate;
    }

    public void returnGate(StadiumGate gate) {
        if (gate == null) {
            return;
        }
        this.gatePool.offer(gate);
    }

    public void closeStadium() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }

}
