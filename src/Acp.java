import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.enseeiht.danck.voice_analyzer.Field;
import Jama.Matrix;

public class Acp {

	private String baseTest;
	private Matrix matrixBaseTest;
	private Matrix matrixDeGravite;
	private Matrix matrixCentreeReduite;
	private Matrix matrixVarianceCovariance;
	private Matrix matrixTroisPlusGrandeVP;
	private Matrix matrixACP;
	private Point[] tableauPoint;
	
	public Acp(String baseTest) {
		this.baseTest = baseTest;
	}
	
	//Créer la matrice contenant la moyenne des MFCC de chaque fichier du dossier 'baseTest'
	public Matrix creerMatriceBaseTest() throws IOException, InterruptedException{
		File dossier = new File(baseTest);
	    String[] fichiersCorpus = dossier.list();
	    if (fichiersCorpus == null) {         
            System.err.println("Nom de repertoire invalide");
        }

	    List<Field> listeFichiers = new ArrayList<>();
	    for(int i = 0; i < fichiersCorpus.length; i++){
	    	listeFichiers.add(myDTWtest.creationField(baseTest + fichiersCorpus[i]));
	    }
		
		double[][] baseTest = new double[listeFichiers.size()][13];
		for(int i = 0; i < listeFichiers.size(); i++) {
			baseTest[i] = moyenneMFCCField(listeFichiers.get(i));
		}
		
		matrixBaseTest = new Matrix(baseTest);
		return matrixBaseTest;
	}
	
	public Matrix creerMatriceDeGravite(){
		//Tableau a deux dimensions pour pouvoir le transformer en matrix
		double[][] moyenne = new double[1][13];
		moyenne[0] = moyenneMFCCMatrice(matrixBaseTest);
		matrixDeGravite = new Matrix(moyenne);
		return matrixDeGravite;
	}
		
	public Matrix creerMatriceCentreeReduite(){
		double[][] res = new double[matrixBaseTest.getRowDimension()][matrixBaseTest.getColumnDimension()];
		for(int j = 0; j < matrixBaseTest.getColumnDimension(); j++) {
			for(int i = 0; i < matrixBaseTest.getRowDimension(); i++) {
				res[i][j] = matrixBaseTest.get(i,j) - matrixDeGravite.get(0,j);
			}
		}
		matrixCentreeReduite = new Matrix(res);
		return matrixCentreeReduite;
	}
	
	public Matrix creerMatriceVarianceCovariance(){
		Matrix transposeCentreeReduite = matrixCentreeReduite.transpose();
		System.out.println("Matrice transposée de Xc : ");
		afficherMatrice(transposeCentreeReduite);
		
		matrixVarianceCovariance = transposeCentreeReduite.times(matrixCentreeReduite);
		return matrixVarianceCovariance;
	}
	
	//Creer une matrice contenant les 3 vecteurs propres liés au 3 plus grandes valeurs propres
	public Matrix troisPlusGrandsVecteursPropres(){
		Matrix valeursPropres = matrixVarianceCovariance.eig().getD();
		int[] indexPlusGrandesVP = new int[3];
		for(int i = 0; i < 3; i++){
			int max = 0;
			for(int j = 0; j < valeursPropres.getColumnDimension(); j++){
				if(valeursPropres.get(j,j) > max){
					max = j;
				}
			}
			indexPlusGrandesVP[i] = max;
			valeursPropres.set(max, max, 0);
		}
		
		Arrays.sort(indexPlusGrandesVP);
		
		Matrix vecteursPropres = matrixVarianceCovariance.eig().getV();
		double[][] troisPremiersVP = new double[13][3];
		for(int j = 0; j < 3; j++) {
			for(int i = 0; i < 13; i++) {
				troisPremiersVP[i][j] = vecteursPropres.get(i, indexPlusGrandesVP[j]);
			}
		}
		matrixTroisPlusGrandeVP = new Matrix(troisPremiersVP);
		return matrixTroisPlusGrandeVP;
	}
	
	//Creer la matrice ACP lié à la matrice de base
	public Matrix creerMatriceACP(){
		matrixACP = matrixCentreeReduite.times(matrixTroisPlusGrandeVP);
		return matrixACP;
	}
	
	//Creer un tableau contenant la position de chaque instructions dans le plan
	public Point[] creerTableauPoint(){
		tableauPoint = new Point[matrixBaseTest.getRowDimension()];
		for(int i = 0; i < tableauPoint.length ;i++){
			tableauPoint[i] = new Point(i, matrixACP.get(i, 0), matrixACP.get(i, 1), matrixACP.get(i, 2));
		}
		return tableauPoint;
	}
	
	//Calcul la moyenne de chaque colonne d'une matrice
	public static double[] moyenneMFCCMatrice(Matrix matrice) {
		//Creation de la matrice moyenne
		double[] moyenne = new double[matrice.getColumnDimension()];
		for(int j = 0; j < moyenne.length; j++) {
			double total = 0;
			for(int i = 0; i < matrice.getRowDimension(); i++) {
				total += matrice.get(i, j);;
			}
			moyenne[j] = total/matrice.getRowDimension();
		}
		return moyenne;
	}
	
	//Calcul la moyenne des colonnes de tout les MFCC présent dans un field
	public static double[] moyenneMFCCField(Field field) {
		//Creation de la matrice contenant tout les MFCC
		double[][] totalMFCC = new double[field.getLength()][13];
		for(int i = 0; i < field.getLength(); i++) {
			for(int j = 0; j < 13; j++) {
				totalMFCC[i][j] = field.getMFCC(i).getCoef(j);
			}
		}
		Matrix matrixTotalMFCC = new Matrix(totalMFCC);
		//Creation de la matrice moyenne
		double[] moyenne = moyenneMFCCMatrice(matrixTotalMFCC);
		return moyenne;
	}
	
	//Gere l'affichage d'une matrice
	public void afficherMatrice(Matrix matrix) {
		for(int i = 0; i < matrix.getRowDimension(); i++) {
			for(int j = 0; j < matrix.getColumnDimension(); j++) {
				System.out.print(matrix.get(i, j) + "    ");
			}
			System.out.println();
		}
		System.out.println();
	}	
}
