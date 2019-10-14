package simulation;

public class Timer {
    private double timePassedS = 0;

    public void iterate(double timeStep) {
        timePassedS += timeStep;
    }

    long timePassedS() {
        return (long) timePassedS;
    }
}
