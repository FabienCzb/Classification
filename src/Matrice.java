public class Matrice {

    private int row;
    private int column;
    private double[][] mat;

    public Matrice(double[][] mat, int column, int row){
        this.mat = mat;
        this.column = column;
        this.row = row;
    }


    public Matrice multiply(Matrice mat) {
        Matrice ret = new Matrice(
                new double[this.getRow()][mat.getColumn()],
                this.getColumn(),
                mat.getRow());

        double val;
        for(int i = 0; i < mat.getColumn(); i++){
            for(int j = 0; j < this.getRow(); j++){
                val = 0;
                for(int k = 0; k < this.getColumn(); k++){
                    val += (this.getValAt(i,k) * mat.getValAt(k,j));
                }
                ret.setValAt(i,j,val);
            }
        }
        return ret;
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
                System.out.print(this.getValAt(i,j) + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Calcul de la transposée d'une matrice
    public Matrice transpose(){

        Matrice temp = new Matrice(new double[][]{{}},this.getRow(),this.getColumn());
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
