package general_support;

import java.util.List;

public class Numerical {

    //TODO: This method is probably not needed. Might de-implement eventually.
    public static double TrapezoidIntegral(List<Double> f){
        int size = f.size();
        double result = 0;
        for(int i=0; i<size; i++){
            if(i==0 || i ==size)
                result += f.get(i)/2;
            else
                result += f.get(i);
        }

        return result;
    }

    /**
     *
     * @param error error at t
     * @param lastError error at t-1
     * @param currentIntegral integral up until t-1
     * @return updated integral up until t
     */
    public static double updateTrapezoidIntegral(double error, double lastError, double currentIntegral){
        return currentIntegral + lastError/2 + error/2;
    }

    /**
     *
     * @param f list of previous function values
     * @return Estimate of the derivative at last recorded point
     */
    public static double TwoPointDerivativeBackWard(List<Double> f, double h){
        return (f.get(f.size()-1) - f.get(f.size()-2))/h;
    }

    /**
     *
     * @param f
     * @return
     */
    public static double ThreePointDerivativeBackWard(List<Double> f, double h){
        return (3*f.get(f.size()-1) - 4*f.get(f.size()-2) + f.get(f.size()-3))/h;
    }

    public static double FivePointDerivativeBackWard(List<Double> f, double h){
        return (25*f.get(f.size()-1) - 48*f.get(f.size()-2) + 36*f.get(f.size()-3) - 16*f.get(f.size()-4) + 3*f.get(f.size()-5))/h;
    }
}
