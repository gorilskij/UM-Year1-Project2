package simulation.runner;

public abstract class BaseRunner implements Runner {
    private volatile long minFrameTimeNs;
    private Thread runnerThread = null;

    public BaseRunner(long minFrameTimeNs) {
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
        if (runnerThread != null) return;

        final long localMinFrameTime = minFrameTimeNs();

        runnerThread = new Thread(() -> {
            try {
                while (!runnerThread.isInterrupted()) {
                    long startTimeNs = System.nanoTime();
                    doFrame();
                    long endTimeNs = System.nanoTime();
                    long timeTakenNs = endTimeNs - startTimeNs;

                    if (localMinFrameTime > timeTakenNs)
                        Thread.sleep((localMinFrameTime - timeTakenNs) / 1_000_000);
                }
            } catch (InterruptedException ignored) {
            }
        });
        runnerThread.start();
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
