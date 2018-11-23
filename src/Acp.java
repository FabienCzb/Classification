//import fr.enseeiht.danck.voice_analyzer.Field;
//
//import java.awt.*;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Acp {
//
//	private String baseTest;
//	private Matrice MatriceBaseTest;
//	private Matrice MatriceDeGravite;
//	private Matrice MatriceCentreeReduite;
//	private Matrice MatriceVarianceCovariance;
//	private Matrice MatriceTroisPlusGrandeVP;
//	private Matrice MatriceACP;
//	private Point[] tableauPoint;
//
//	public Acp(String baseTest) {
//		this.baseTest = baseTest;
//	}
//
//	//Creer la matrice contenant la moyenne des MFCC de chaque fichier du dossier 'baseTest'
//	public Matrice creerMatriceBaseTest() throws IOException, InterruptedException{
//		File dossier = new File(baseTest);
//	    String[] fichiersCorpus = dossier.list();
//	    if (fichiersCorpus == null) {
//            System.err.println("Nom de repertoire invalide");
//        }
//
//	    List<Field> listeFichiers = new ArrayList<>();
//	    for(int i = 0; i < fichiersCorpus.length; i++){
//	    	listeFichiers.add(myDTWtest.creationField(baseTest + fichiersCorpus[i]));
//	    }
//
//		double[][] baseTest = new double[listeFichiers.size()][13];
//		for(int i = 0; i < listeFichiers.size(); i++) {
//			baseTest[i] = moyenneMFCCField(listeFichiers.get(i));
//		}
//
//		MatriceBaseTest = new Matrice(baseTest);
//		return MatriceBaseTest;
//	}
//
//	public Matrice creerMatriceDeGravite(){
//		//Tableau a deux dimensions pour pouvoir le transformer en Matrice
//		double[][] moyenne = new double[1][13];
//		moyenne[0] = moyenneMFCCMatrice(MatriceBaseTest);
//		MatriceDeGravite = new Matrice(moyenne);
//		return MatriceDeGravite;
//	}
//
//
//
//	public Matrice creerMatriceVarianceCovariance(){
//		Matrice transposeCentreeReduite = MatriceCentreeReduite.transpose();
//		System.out.println("Matrice transposée de Xc : ");
//		transposeCentreeReduite.afficher();
//
//		MatriceVarianceCovariance = Matrice.multiply(transposeCentreeReduite,MatriceCentreeReduite);
//		return MatriceVarianceCovariance;
//	}
//
//	//Creer une matrice contenant les 3 vecteurs propres li�s au 3 plus grandes valeurs propres
//	public Matrice troisPlusGrandsVecteursPropres(){
//		/*Matrice valeursPropres = MatriceVarianceCovariance.eig().getD();
//		int[] indexPlusGrandesVP = new int[3];
//		for(int i = 0; i < 3; i++){
//			int max = 0;
//			for(int j = 0; j < valeursPropres.getColumn(); j++){
//				if(valeursPropres.getValAt(j,j) > max){
//					max = j;
//				}
//			}
//			indexPlusGrandesVP[i] = max;
//			valeursPropres.setValAt(max, max, 0);
//		}
//
//		Arrays.sort(indexPlusGrandesVP);
//
//		Matrice vecteursPropres = MatriceVarianceCovariance.eig().getV();
//		double[][] troisPremiersVP = new double[13][3];
//		for(int j = 0; j < 3; j++) {
//			for(int i = 0; i < 13; i++) {
//				troisPremiersVP[i][j] = vecteursPropres.getValAt(i, indexPlusGrandesVP[j]);
//			}
//		}
//		MatriceTroisPlusGrandeVP = new Matrice(troisPremiersVP);
//		return MatriceTroisPlusGrandeVP; */
//	}
//
//	//Creer la matrice ACP li� � la matrice de base
////	public Matrice creerMatriceACP(){
////		MatriceACP = Matrice.multiply(MatriceCentreeReduite,MatriceTroisPlusGrandeVP);
////		return MatriceACP;
////	}
//
//	//Creer un tableau contenant la position de chaque instructions dans le plan
//	public Point[] creerTableauPoint(){
//		tableauPoint = new Point[MatriceBaseTest.getRow()];
//		for(int i = 0; i < tableauPoint.length ;i++){
//			tableauPoint[i] = new Point(i, MatriceACP.getValAt(i,0), MatriceACP.getValAt(i, 1), MatriceACP.getValAt(i, 2));
//		}
//		return tableauPoint;
//	}
//
//	//Calcul la moyenne de chaque colonne d'une matrice
//	public static double[] moyenneMFCCMatrice(Matrice matrice) {
//		//Creation de la matrice moyenne
//		double[] moyenne = new double[matrice.getColumn()];
//		for(int j = 0; j < moyenne.length; j++) {
//			double total = 0;
//			for(int i = 0; i < matrice.getRow(); i++) {
//				total += matrice.getValAt(i, j);
//			}
//			moyenne[j] = total/matrice.getRow();
//		}
//		return moyenne;
//	}
//
//	//Calcul la moyenne des colonnes de tout les MFCC pr�sent dans un field
//	public static double[] moyenneMFCCField(Field field) {
//		//Creation de la matrice contenant tout les MFCC
//		double[][] totalMFCC = new double[field.getLength()][13];
//		for(int i = 0; i < field.getLength(); i++) {
//			for(int j = 0; j < 13; j++) {
//				totalMFCC[i][j] = field.getMFCC(i).getCoef(j);
//			}
//		}
//		Matrice MatriceTotalMFCC = new Matrice(totalMFCC);
//		//Creation de la matrice moyenne
//		double[] moyenne = moyenneMFCCMatrice(MatriceTotalMFCC);
//		return moyenne;
//	}
//}
