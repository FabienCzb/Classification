public class Main {

    public static void main(String[] args) {
        Matrice m1 = new Matrice(new double[][]{{1,0},{2,-1}});
        Matrice m2 = new Matrice(new double[][]{{3,4},{-2,-3}});

        m1.afficher();

        Matrice m3 = Matrice.multiply(m1,m2);
        m3.afficher();
    }
}
