import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;

import java.util.ArrayList;

public class Kppv {

    public RealMatrix createCovMatrix(RealMatrix matrix){

        Covariance cov = new Covariance(matrix);
        return cov.getCovarianceMatrix();
    }

    public double square(double x){
        return x*x;
    }

    public double calculerDistance(double[] vecteur1,double[] vecteur2){
        double somme = 0;
        for(int i = 0 ; i < vecteur1.length ; i++){
            somme += square(vecteur1[i]-vecteur2[i]);
        }

        return Math.sqrt(somme);
    }






}
