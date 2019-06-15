package simulation.runner;

public interface Runner {
    void start();
    void stop();
    void restartIfRunning();
    void setMinFrameTime(long minFrameTimeNs);

    void doFrame();
}
