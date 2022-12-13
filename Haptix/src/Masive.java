public class Masive {
    public static void main(String[] args) {
        int[][] mat =  {{1,2,3,4,5,6,8},
                        {4,5,6},
                        {7,8,9}};

        for(int i = 0; i<mat.length; i++){
            for(int t = 0; t<mat[i].length; t++){
                System.out.print(mat[i][t] + " ");
            }
            System.out.println();
        }
    }
}
