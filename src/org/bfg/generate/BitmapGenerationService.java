package org.bfg.generate;

import java.awt.*;

public final class BitmapGenerationService extends Thread implements IWork {

    private static class DummyRunner implements Runnable {

        final Object lock;
        final long timeout;
        int step;
        boolean shouldRun;

        DummyRunner(long timeout) {
            this.lock = new Object();
            this.shouldRun = true;

            synchronized (this.lock) {
                this.timeout = timeout;
                this.step = 0;
            }
        }

        @Override
        public void run() {
            while (this.shouldRun) {
                synchronized (this.lock) {
                    this.step++;
                }

                try {
                    Thread.sleep(this.timeout);
                } catch (InterruptedException e) {

                }
            }
        }

        int fetchStep() {
            synchronized (this.lock) {
                return this.step;
            }
        }
    };

    public static BitmapGenerationService create(Font font) {
        return new BitmapGenerationService(new DummyRunner(1000));
    }

    private final DummyRunner dummyRunner;

    private BitmapGenerationService(DummyRunner dummyRunner) {
        super(dummyRunner);
        this.dummyRunner = dummyRunner;
    }

    @Override
    public int getTaskCount() {
        return 10;
    }

    @Override
    public int getFinishedTaskCount() {
        return dummyRunner.fetchStep();
    }

    @Override
    public boolean hasFinished() {
        final boolean finished = dummyRunner.fetchStep() >= 10;

        if (finished)
            dummyRunner.shouldRun = false;

        return finished;
    }
}
