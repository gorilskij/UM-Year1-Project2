package simulation;

public interface Simulation {
    /**
     *
     * @return current time
     */
    long timePassedS();

    void startPhysics();
    void stopPhysics();
}