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

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
    }

    long timePassedS() {
        return timePassedS;
    }
}
