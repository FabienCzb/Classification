public class Main {

    public static void main(String[] args) {
        double[][] d1 = new double[][]{{1,0},{2,-1}};
        double[][] d2 = new double[][]{{3,4},{-2,-3}};
        Matrice m1 = new Matrice(d1, 2, 2);
        Matrice m2 = new Matrice(d2, 2, 2);

        //m1.afficher();

        Matrice m3 = m1.multiply(m2);
        m3.afficher();

        (m1.transpose()).afficher();
    }
}
