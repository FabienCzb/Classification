import fr.enseeiht.danck.voice_analyzer.Extractor;
import fr.enseeiht.danck.voice_analyzer.MFCC;
import fr.enseeiht.danck.voice_analyzer.WindowMaker;
import hu.kazocsaba.math.matrix.Matrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.linear.EigenDecomposition;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ACP {

    public RealMatrix createMatriceACP(String folder) throws IOException, InterruptedException {
        int MFCCLength;
        Extractor extractor = Extractor.getExtractor();

        // Etape 1. Lecture de Alpha
        File dir = new File(folder);
        assert(dir.length() != 0);

        String[] sFiles = dir.list();
        int c = 0;
        RealMatrix m = MatrixUtils.createRealMatrix(new double[sFiles.length][13]);
        for (String file : sFiles) {


            WindowMaker windowMaker = new MultipleFileWindowMaker(Collections.singleton(file));

            // Etape 2. Recuperation des MFCC du mot Alpha
            MFCCLength = FieldLength(file);
            MFCC[] mfccs = new MFCC[MFCCLength];
            double mean = 0;
            for (int i = 0; i < mfccs.length; i++) {
                mfccs[i] = extractor.nextMFCC(windowMaker);
                int sum = 0;
                for (int j = 0; j < mfccs[i].getLength(); j++) {
                    sum += mfccs[j].getCoef(j);
                }
                mean = ((double)sum/(double)mfccs.length);
                m.setEntry(c,i,mean);
            }
            c++;
        }
        return m;
    }

    private int FieldLength(String fileName) throws IOException {
        int counter = 0;
        File file = new File(System.getProperty("user.dir") + fileName);
        for (String line : Files.readAllLines(file.toPath(), Charset.defaultCharset())) {
            counter++;
        }
        return 2 * Math.floorDiv(counter, 512);
    }

    public RealMatrix createCovMatrix(RealMatrix matrix){
        Covariance cov = new Covariance(matrix);
        return cov.getCovarianceMatrix();
    }

    public RealMatrix vecPropresAssocies(Matrix matrice){
        EigenDecomposition e = new EigenDecomposition((RealMatrix) matrice,0);
        Matrix valPropres = (Matrix) e.getD();
        double[][] temp = new double[1][3];
        Matrix vecPropres = (Matrix) e.getV();
        int[] indexMax = new int[3];
        double max;
        int index;

        for(int i = 0; i < 3; i++){
            max = vecPropres.get(0,0);
            index = 0;
            for(int j = 0; j < 13; i++){
                if((max < valPropres.get(j,0))){
                    max = valPropres.get(j,0);
                    index = j;
                }
                valPropres.set(0,index,Double.MIN_VALUE);
                indexMax[i] = index;
            }
        }

        for(int k = 0; k < 3; k++){
            temp[0][k] = vecPropres.get(0,indexMax[k]);
        }
        RealMatrix plusGrandes = MatrixUtils.createRealMatrix(temp);
        return plusGrandes;

    }

}
