package simulation.runner;

public abstract class BaseRunner implements Runner {
    private volatile long minFrameTimeNs;
    private Thread runnerThread = null;

    BaseRunner(long minFrameTimeNs) {
        this.minFrameTimeNs = minFrameTimeNs;
    }

    public long minFrameTimeNs() {
        return minFrameTimeNs;
    }

    @Override
    public void setMinFrameTime(long minFrameTimeNs) {
        this.minFrameTimeNs = minFrameTimeNs;
        restartIfRunning();
    }

    @Override
    public void start() {
        start(minFrameTimeNs);
    }

    private void start(final long minFrameTimeNs) {
        if (runnerThread != null) return;
        runnerThread = new Thread(() -> {
            try {
                while (true) {
                    long startTimeNs = System.nanoTime();
                    doFrame();
                    long endTimeNs = System.nanoTime();
                    long timeTakenNs = endTimeNs - startTimeNs;

                    // if execution was more than 10 ns faster than it should be
                    if (minFrameTimeNs - timeTakenNs > 10)
                        Thread.sleep(minFrameTimeNs - timeTakenNs);
                }
            } catch (InterruptedException ignored) {}
        });
    }

    @Override
    public void stop() {
        if (runnerThread == null) return;
        runnerThread.interrupt();

        // wait for thread to finish
        try {
            runnerThread.join();
        } catch (InterruptedException e) {
            System.out.println("unexpected interrupt");
            throw new IllegalStateException(e);
        }

        runnerThread = null;
    }

    @Override
    public void restartIfRunning() {
        if (runnerThread != null) {
            stop();
            start(minFrameTimeNs);
        }
    }
}
