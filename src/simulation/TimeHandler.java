package simulation;

public class TimeHandler {
    private long timePassedS = 0;
    private int timeStep;

    TimeHandler(int initialTimeStep) {
        timeStep = initialTimeStep;
    }

    void iterate() {
        timePassedS += timeStep;
    }

    int timeStep() {
        return timeStep;
    }

    long timePassedS() {
        return timePassedS;
    }
}
