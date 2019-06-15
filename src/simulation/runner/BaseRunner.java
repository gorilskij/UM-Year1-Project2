package simulation.runner;

import simulation.PlayPause;

public abstract class BaseRunner implements Runner {
    private volatile long minFrameTimeNs;
    private Thread runnerThread = null;

    BaseRunner(long minFrameTimeNs) {
        this.minFrameTimeNs = minFrameTimeNs;
    }

    public long minFrameTimeNs() {
        return minFrameTimeNs;
    }

    public void setMinFrameTime(long minFrameTimeNs) {
        this.minFrameTimeNs = minFrameTimeNs;
        restartIfRunning();
    }

    public void play() {
        final long localMinFrameTime = minFrameTimeNs();

        if (runnerThread != null) return;
        runnerThread = new Thread(() -> {
            try {
                while (true) {
                    long startTimeNs = System.nanoTime();
                    doFrame();
                    long endTimeNs = System.nanoTime();
                    long timeTakenNs = endTimeNs - startTimeNs;

                    // if execution was more than 10 ns faster than it should be
                    if (localMinFrameTime - timeTakenNs > 10)
                        Thread.sleep(localMinFrameTime - timeTakenNs);
                }
            } catch (InterruptedException ignored) {}
        });
    }

    public void pause() {
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
            pause();
            play();
        }
    }
}
