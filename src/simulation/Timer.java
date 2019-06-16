package simulation;

public class Timer {
    private long timePassedS = 0;

    public void iterate(int timeStep) {
        timePassedS += timeStep;
    }

    long timePassedS() {
        return timePassedS;
    }
}
