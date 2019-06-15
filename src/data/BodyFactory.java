package data;

import body.Planet;
import body.Star;
import body.interfaces.Body;
import general_support.Vector;

import java.awt.*;

public class BodyFactory {
    private static final Body[] prototypes = {
            new Star(
                    "sun", 1.9891e30, 6.957e8,
                    Color.YELLOW
            ),
            new Planet(
                    "mercury", 3.285e23, 2.44e6,
                    new Vector(-4.714853635016740e10, -4.782573126195183e10, 4.173036297464184e8),
                    new Vector(2.477850573900118e4, -3.197716276204995e4, -4.886091527329508e3),
                    Color.LIGHT_GRAY
            ),
            new Planet(
                    "venus", 4.867e24, 6.0518e6,
                    new Vector(-8.226808599082340e10, 6.894270717278056e10, 5.693478998464912e9),
                    new Vector(-2.262543987898001e4, -2.702071614681663e4, 9.349045184478229e2),
                    new Color(255, 255, 194)
            ),
            new Planet(
                    "earth", 5.972e24, 6.37e6,
                    new Vector(-2.554468314683728e10, 1.448663382530419e11, -6.921329808697104e6),
                    new Vector(-2.982969883069769e4, -5.276272683935622e3, 5.779415470863469e-1),
                    Color.BLUE
            ),
            new Planet(
                    "mars", 6.39e23, 3.39e6,
                    new Vector(1.630936983917932e11, 1.438056922687930e11, -9.886542104934454e8),
                    new Vector(-1.510397968673562e4, 2.024123263670411e4, 7.947472796474360e2),
                    Color.RED
            ),
            new Planet(
                    "jupiter", 1.898e27, 6.99e7,
                    new Vector(-3.192855545995132e11, -7.338194085688783e11, 1.019200106751004e10),
                    new Vector(1.183328580657644e4, -4.600362449100212e3, -2.456574660642572e2),
                    Color.ORANGE
            ),
            new Planet(
                    "saturn", 5.683e26, 5.82e7,
                    new Vector(2.931488045558453e11, -1.476100690277258e12, 1.399016902350026e10),
                    new Vector(8.955095224904190e3, 1.849362728683380e3, -3.889742857125925e2),
                    new Color(150, 100, 30)
            ),
            new Planet(
                    "uranus", 8.681e25, 2.54e7,
                    new Vector(2.545567233428805e12, 1.532346050403908e12, -2.727281639089733e10),
                    new Vector(-3.550484828766487e3, 5.512396230447346e3, 6.622395084734878e1),
                    new Color(18, 143, 145)
            ),
            new Planet(
                    "neptune", 1.024e26, 2.46e7,
                    new Vector(4.335704143193155e12, -1.120284132989365e12, -7.686225510909849e10),
                    new Vector(1.336208377346853e3, 5.291072248378655e3, -1.405551000735563e2),
                    new Color(35, 9, 130)
            ),

            // moon positions relative to their parent planet
            new Planet(
                    "moon", 7.345e22, 1.737e6,
                    new Vector(-2.860800707081836e8, -2.584807922023280e8, 3.428195728332768e7),
                    new Vector(6.428910257645484e2, -7.838605185111597e2, -2.059513938200092e1),
                    Color.DARK_GRAY
            ),
            new Planet(
                    "titan", 1.345e23, 2.575e6,
                    new Vector(-1.086926303697304e9, -4.913589335304970e8, 3.613191079302459e8),
                    new Vector(2.726239958838512e3, -4.311977032775533e3, 1.951359435279031e3),
                    new Color(200, 180, 100)
            )
    };

    public static Body[] createSolarSystem() {
        Body[] copies = new Body[prototypes.length];
        for (int i = 0; i < prototypes.length; i++)
            copies[i] = prototypes[i].copy();
        return copies;
    }
}