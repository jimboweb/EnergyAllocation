//package oldenergyvalues;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.PriorityQueue;
//import java.util.Random;
//import java.util.Scanner;
//
//class Equation {
//    
//    
//    
//    public Equation(int size) {
//        this.a = new double[size][size];
//        this.b = new double[size];
//        this.size = size;
//        pivotRow = new int[a.length];
//        noPivotYet = new ArrayList<>();
//        notAPivot = new ArrayList<>();
//        for(int i=0; i< a.length; i++)
//            pivotRow[i] = -1;        
//        for(int i=0;i<size;i++)
//            notAPivot.add(i);
//        }
//    
//        public void setCell(double val, int row, int col){
//            a[row][col] = val;
//            if(pivotRow[col]==-1){
//                if(a[row][col]!=0 && notAPivot.contains(row)){
//                    pivotRow[col] = row;
//                    notAPivot.remove(notAPivot.indexOf(row));
//                    colsWithPivot++;
//                }
//                else{
//                    noPivotYet.add(col);
//                }
//            }
//
//        }
//
//        public void finishInput(){
//            for(Integer thisCol:noPivotYet){
//            int r = 0;
//            while(pivotRow[thisCol] == -1 && r<size){
//                if(a[r][thisCol]!=0){
//                    pivotRow[thisCol] = r;
//                }
//                r++;
//            }
//        }
//
//        }
//
//        public void setAns(double val, int row){
//            b[row] = val;
//        }
//    
//        void ProcessPivotElement(Position pivot_element) {
//            int size = a.length;
//            Double currentCell = a[pivot_element.row][pivot_element.column];
//            for(int i=0;i<size;i++){
//                a[pivot_element.row][i] = a[pivot_element.row][i]/currentCell;
//            }
//            b[pivot_element.row] = b[pivot_element.row]/currentCell;
//            for(int i=0;i<size;i++){
//                if(i!=pivot_element.row){
//                    double scale = a[i][pivot_element.column];
//                    for(int j=0;j<size;j++){
//                        a[i][j] -= a[pivot_element.row][j]*scale;
//                    }
//                int a = 0;
//                b[i] -= b[pivot_element.row]*scale;
//                int x = 0;
//                }
//            }
//        }
//
//    
//    double a[][];
//    double b[];
//    int[] pivotRow;
//    private ArrayList<Integer> noPivotYet;
//    private ArrayList<Integer> notAPivot;
//    int size;
//    int colsWithPivot = 0;
//}
//
//class Position {
//    Position(int column, int row) {
//        this.column = column;
//        this.row = row;
//    }
//
//    int column;
//    int row;
//}
//
//class EnergyValues2 {
//    static Equation ReadEquation() throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        int size = scanner.nextInt();
//        Equation e = new Equation(size);
//        for (int row = 0; row < size; ++row) {
//            for (int column = 0; column < size; ++column){
//                e.setCell(scanner.nextInt(), row, column);
//            }
//            e.setAns(scanner.nextInt(), row);
//        }
//        e.finishInput();
//        return e;
//    }
//
//
//    static double[] SolveEquation(Equation equation) {
//        double a[][] = equation.a;
//        double b[] = equation.b;
//        int size = a.length;
//        int nextRow = 0;
//        int nextCol = 0;       
//        for (int step = 0; step < size; ++step) {
//            Position pivot_element = new Position(equation.pivotRow[step], step);
//            equation.ProcessPivotElement(pivot_element);
//            int x = 0;
//       }
//        return b;
//    }
//
//    static void PrintColumn(double column[]) {
//        int size = column.length;
//        for (int row = 0; row < size; ++row)
//            System.out.printf("%.20f\n", column[row]);
//    }
//
//    public static void main(String[] args) throws IOException {
//        Equation equation = ReadEquation();
//        double[] solution = SolveEquation(equation);
//        PrintColumn(solution);
//            //unitTest();
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    public static void unitTest(){
//        int trials = 10;
//        for(int trial=0;trial<trials;trial++){
//            Random rnd = new Random();
//            int n = rnd.nextInt(19) + 1;
//            Equation e = new Equation(n);
//            double[] ans = new double[n];
//            for(int i=0;i<n;i++){
//                ans[i] = rnd.nextDouble()*2000 - 999;
//            }
//            int[] zerosInCol = new int[n];
//            for(int col:zerosInCol)
//                col=0;
//            int[] zerosInRow = new int[n];
//            for(int col:zerosInRow)
//                col=0;
//            for(int i=0;i<n;i++){
//                for(int j=0;j<n;j++){
//                    if(rnd.nextInt(7)==1 && zerosInCol[j]<n-1 && zerosInRow[i]<n-1){
//                        e.setCell(i, j, 0);
//                        zerosInRow[i]++;
//                        zerosInCol[j]++;
//                    }
//                    else{
//                        e.setCell((rnd.nextDouble() * 2000 - 999), i, j);
//                    }
//                }
//            }
//            double [][] aCopy = new double[n][n];
//            for (int i=0;i<n;i++){
//                aCopy[i] = Arrays.copyOf(e.a[i], e.a[i].length);
//            }
//            //[tk] not working
//            for(int i=0;i<n;i++){
//                double a = 0;
//                for(int j=0;j<n;j++){
//                    a+=e.a[i][j]*ans[i];
//                }
//                e.setAns(a, i);
//            }
//            double [] bCopy = Arrays.copyOf(e.b, e.b.length);
//            e.finishInput();
//            double[] solution = SolveEquation(e);
//            if(!Arrays.equals(solution, ans))
//            {
//                System.out.printf("Failed on trial %d%n", trial);
//                System.out.println("equation: ");
//                for(int i=0;i<n;i++){
//                    for(int j=0; j<n; j++){
//                        System.out.printf("%f ", aCopy[i][j]);
//                    }
//                    System.out.printf("%f %n", bCopy[i]);
//                }
//                System.out.println("Correct answer = ");
//                for(int i=0;i<n;i++){
//                    System.out.printf("%f ", ans[i]);
//                }
//                System.out.println();
//                System.out.println("Got answer ");
//                for(int i=0;i<n;i++){
//                    System.out.printf("%f %n", solution[i]);
//                }
//                System.out.println();
//                System.out.println();
//            }
//        }
//        
//    }
//}
