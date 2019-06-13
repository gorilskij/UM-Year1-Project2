package simulation.runner;

public interface Runner {
    void start(final long minFrameTimeNs);
    void stop();
    void restartIfRunning();
    void setMinFrameTime(long minFrameTimeNs);

    void doFrame();
}
