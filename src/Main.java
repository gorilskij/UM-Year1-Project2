import body.Planet;
import body.SpaceShip;
import controllers.LaunchController;
import controllers.PIDController;
import simulation.Simulation;
import simulation.SimulationImpl;
import simulation.universe.Universe;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class ShipInfo {
    long time;

    double[] PID1;
    double closest1;

    double[] PID2;

    ShipInfo(long time, double[] PID1, double[] PID2) {
        this.time = Math.max(time, 0);
        this.PID1 = PID1;
        closest1 = 1e10;
        this.PID2 = PID2;
    }

    void addLaunch(Simulation simulation, String name, Map<String, Double> map) {
        SpaceShip spaceShip = new SpaceShip(name, Color.WHITE, 10_000, simulation);
        spaceShip.setMap(map);

        simulation.addLaunch(
                spaceShip, time,
                new LaunchController(
                        simulation.universe(), spaceShip, (Planet) simulation.universe().getBodyByName("earth")
                ),
                new PIDController(
                        simulation.universe(), spaceShip, simulation.universe().getBodyByName("titan"),
                        PID1[0], PID1[1], PID1[2], closest1
                ),
                new PIDController(
                        simulation.universe(), spaceShip, simulation.universe().getBodyByName("titan"),
                        PID2[0], PID2[1], PID2[2], 0
                )
        );
    }

    private double[][] spreadPID(double[] PID, double PIDDelta) {
        double[][] PIDs = new double[27][3];
        int[] coeff = {-1, -1, -1};

        for (int i = 0; i < PIDs.length; i++) {
            PIDs[i] = new double[] {
                    PID[0] + coeff[0] * PIDDelta,
                    PID[1] + coeff[1] * PIDDelta,
                    PID[2] + coeff[2] * PIDDelta
            };

            for (int c = 0; c < coeff.length; c++) {
                coeff[c]++;
                if (coeff[c] > 1) {
                    if (c == coeff.length - 1)
                        return PIDs;
                    else {
                        coeff[c] = -1;
                    }
                } else break;
            }
        }

        return PIDs;
    }

    ShipInfo[] spread(long timeDelta, double PIDDelta) {
        long[] times = {time - timeDelta, time, time + timeDelta};
        double[][] PID1s = spreadPID(PID1, PIDDelta);
        double[][] PID2s = spreadPID(PID2, PIDDelta);

        ShipInfo[] infos = new ShipInfo[times.length * PID1s.length * PID2s.length];
        int index = 0;
        for (long time : times)
            for (double[] newPID1 : PID1s)
                for (double[] newPID2 : PID2s)
                    infos[index++] = new ShipInfo(time, newPID1, newPID2);

        return infos;
    }

    ShipInfo[] spreadRand(int selectN, long timeDelta, double PIDDelta) {
        ShipInfo[] spread = spread(timeDelta, PIDDelta);
        List<ShipInfo> selected = new ArrayList<>();
        for (int i = 0; i < selectN; i++) {
            ShipInfo info = spread[(int) (Math.random() * spread.length)];

            if (!selected.contains(info))
                selected.add(info);
        }
        ShipInfo[] arr = new ShipInfo[selected.size()];
        for (int i = 0; i < arr.length; i++)
            arr[i] = selected.get(i);
        return arr;
    }
}

public class Main {

    private static final int TIME_STEP = 100;
    private static final int GUI_FPS = 60;

    private static ShipInfo iterate(Simulation simulation, ShipInfo[] generation) {
        Map<String, Double> minDistanceMap = new HashMap<>();

        for (int i = 0; i < generation.length; i++) {
            String name = "ship " + i;
            generation[i].addLaunch(simulation, name, minDistanceMap);
            minDistanceMap.put(name, Double.POSITIVE_INFINITY);
        }

        try {
            System.out.println("---START---");
            long start = System.nanoTime();
            simulation.play();
            Thread.sleep(10 * 1000);
            System.out.println("ran for: " + (System.nanoTime() - start) / 1e9 + "s");
            throw new InterruptedException();
        } catch (InterruptedException e) {
            int bestI = -1;
            double best = Double.POSITIVE_INFINITY;
            for (int i = 0; i < generation.length; i++) {
                if (minDistanceMap.get("ship " + i) < best) {
                    best = minDistanceMap.get("ship " + i);
                    bestI = i;
                }
            }
            System.out.println("best: " + best);
            return generation[bestI];
        }
    }

    public static void main(String[] args) {
        ShipInfo[] generation = {new ShipInfo(
                        0,
                        new double[] {0, 0, 0},
                        new double[] {0, 0, 0}
                )};

        Simulation simulation = new SimulationImpl(TIME_STEP, GUI_FPS);

        for (int i = 0; i < 10; i++) {
            ShipInfo best = iterate(simulation, generation);
            generation = best.spreadRand(10, 1000, 1e-10);
            simulation = new SimulationImpl(TIME_STEP, GUI_FPS, simulation);
        }
    }






























    public static void other(String[] args) {
        SimulationImpl simulation = new SimulationImpl(100, 60);
        Universe universe = simulation.universe();

        SpaceShip choochoo = new SpaceShip("choo choo", Color.WHITE, 10_000, simulation);

        LaunchController launch = new LaunchController(
                universe,
                choochoo,
                (Planet) universe.getBodyByName("earth")
        );
        PIDController pid1 = new PIDController(
                universe,
                choochoo,
                universe.getBodyByName("titan"),
                1E-11, 5E-17, 1E-4,
                7E10
        );
        PIDController pid2 = new PIDController(
                universe,
                choochoo,
                universe.getBodyByName("titan"),
                4E-14, 0, 0.0005,
                0
        );

        simulation.addLaunch(choochoo, 6879100,
                launch,
                pid1,
                pid2
        );
    }
}
