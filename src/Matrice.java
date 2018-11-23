public class Matrice {

    private int row;
    private int column;
    private double[][] mat;

    public Matrice(double[][] mat){
        this.mat = mat;

    }

    public Matrice(int row, int column){
        this.row = row;
        this.column = column;
    }

    public static Matrice multiply(Matrice mat1, Matrice mat2) {
        Matrice mat = new Matrice(mat1.getRow(),mat2.getColumn());
        double val;
        for(int i = 0; i < mat2.getColumn(); i++){
            for(int j = 0; j < mat1.getRow(); j++){
                val = 0;
                for(int k = 0; k < mat1.getColumn(); k++){
                    val += (mat1.getValAt(i,j) * mat2.getValAt(j,i));
                }
                mat.setValAt(i,j,val);
            }
        }
        return mat;
    }


    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return  this.column;
    }

    public double getValAt(int i, int j) {
        return mat[i][j];
    }

    public void setValAt(int i, int j, double val) {
        mat[i][j] = val;
    }

    // Affiche une matrice
    public void afficher() {
        for(int i = 0; i < this.getRow(); i++) {
            for(int j = 0; j < this.getColumn(); j++) {
                System.out.print(this.getValAt(i,j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Calcul de la transposée d'une matrice
    public Matrice transpose(){
        Matrice temp = new Matrice(this.getRow(),this.getColumn());
        for(int i = 0 ; i < this.getColumn(); i++){
            for(int j = 0; j < this.getRow(); j++){
                temp.setValAt(j,i,this.getValAt(i,j));
            }
        }

        return temp;
    }


    public int moyenne(){
        int moy = 0;
        for(int i = 0; i < this.getColumn(); i++){
            moy += this.getValAt(0,i);
        }

        return moy;
    }

}
