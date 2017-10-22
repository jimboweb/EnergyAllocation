import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Equation {
    Equation(double a[][], double b[]) {
        this.a = a;
        this.b = b;
    }

    double a[][];
    double b[];
}

class Position {
    Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    int column;
    int row;
}

class EnergyValues {
    static Equation ReadEquation() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();

        double a[][] = new double[size][size];
        double b[] = new double[size];
        for (int row = 0; row < size; ++row) {
            for (int column = 0; column < size; ++column)
                a[row][column] = scanner.nextInt();
            b[row] = scanner.nextInt();
        }
        return new Equation(a, b);
    }

    static Position SelectPivotElement(double a[][], boolean used_rows[], boolean used_columns[]) {
        // This algorithm selects the first free element.
        // You'll need to improve it to pass the problem.
        Position pivot_element = new Position(0, 0);
        while (used_rows[pivot_element.row])
            ++pivot_element.row;
        while (used_columns[pivot_element.column])
            ++pivot_element.column;
        Position firstFree = pivot_element;
        while(a[pivot_element.row][pivot_element.column]==0){
            ++pivot_element.row;
            if(pivot_element.row>=a.length){
                pivot_element.row = firstFree.row;
                pivot_element.column++;
            }
                
        }
        return pivot_element;
    }

    static void SwapLines(double a[][], double b[], boolean used_rows[], Position pivot_element) {
        int size = a.length;

        for (int column = 0; column < size; ++column) {
            double tmpa = a[pivot_element.column][column];
            a[pivot_element.column][column] = a[pivot_element.row][column];
            a[pivot_element.row][column] = tmpa;
        }

        double tmpb = b[pivot_element.column];
        b[pivot_element.column] = b[pivot_element.row];
        b[pivot_element.row] = tmpb;

        boolean tmpu = used_rows[pivot_element.column];
        used_rows[pivot_element.column] = used_rows[pivot_element.row];
        used_rows[pivot_element.row] = tmpu;

        pivot_element.row = pivot_element.column;
    }

    static void ProcessPivotElement(double a[][], double b[], Position pivot_element) {
            int size = a.length;
            Double currentCell = a[pivot_element.row][pivot_element.column];
            for(int i=0;i<size;i++){
                a[pivot_element.row][i] = a[pivot_element.row][i]/currentCell;
            }
            b[pivot_element.row] = b[pivot_element.row]/currentCell;
            for(int i=0;i<size;i++){
                if(i!=pivot_element.row){
                    double scale = a[i][pivot_element.column];
                    for(int j=0;j<size;j++){
                        a[i][j] -= a[pivot_element.row][j]*scale;
                    }
                b[i] -= b[pivot_element.row]*scale;
                }
            }
    }

    static void MarkPivotElementUsed(Position pivot_element, boolean used_rows[], boolean used_columns[]) {
        used_rows[pivot_element.row] = true;
        used_columns[pivot_element.column] = true;
    }

    static double[] SolveEquation(Equation equation) {
        double a[][] = equation.a;
        double b[] = equation.b;
        int size = a.length;

        boolean[] used_columns = new boolean[size];
        boolean[] used_rows = new boolean[size];
        for (int step = 0; step < size; ++step) {
            Position pivot_element = SelectPivotElement(a, used_rows, used_columns);
            SwapLines(a, b, used_rows, pivot_element);
            ProcessPivotElement(a, b, pivot_element);
            MarkPivotElementUsed(pivot_element, used_rows, used_columns);
        }

        return b;
    }

    static void PrintColumn(double column[]) {
        int size = column.length;
        for (int row = 0; row < size; ++row)
            System.out.printf("%.20f\n", column[row]);
    }

    public static void main(String[] args) throws IOException {
        Equation equation = ReadEquation();
        double[] solution = SolveEquation(equation);
        PrintColumn(solution);
//            unitTest();
    }
    
    
    
    
    
    public static void unitTest(){
        int trials = 10;
        for(int trial=0;trial<trials;trial++){
            Random rnd = new Random();
            int n = rnd.nextInt(19) + 1;
            double[][] a = new double[n][n];
            double[] b = new double[n];
            double[] ans = new double[n];
            for(int i=0;i<n;i++){
                ans[i] = rnd.nextDouble()*2000 - 999;
            }
            int[] zerosInCol = new int[n];
            for(int col:zerosInCol)
                col=0;
            int[] zerosInRow = new int[n];
            for(int col:zerosInRow)
                col=0;
            for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                    if(rnd.nextInt(7)==1 && zerosInCol[j]<n-1 && zerosInRow[i]<n-1){
                        a[i][j]=0;
                        zerosInRow[i]++;
                        zerosInCol[j]++;
                    }
                    else{
                        a[i][j] = (rnd.nextDouble() * 2000 - 999);
                    }
                }
            }
            double [][] aCopy = new double[n][n];
            for (int i=0;i<n;i++){
                aCopy[i] = Arrays.copyOf(a[i], a[i].length);
            }
            //[tk] not working
            for(int i=0;i<n;i++){
                double an = 0;
                for(int j=0;j<n;j++){
                    an+=a[i][j]*ans[i];
                }
                b[i] = an;
            }
            double [] bCopy = Arrays.copyOf(b, b.length);
            Equation e = new Equation(a,b);
            double[] solution = SolveEquation(e);
            if(!Arrays.equals(solution, ans))
            {
                System.out.printf("Failed on trial %d%n", trial);
                System.out.println("equation: ");
                for(int i=0;i<n;i++){
                    for(int j=0; j<n; j++){
                        System.out.printf("%f ", aCopy[i][j]);
                    }
                    System.out.printf("%f %n", bCopy[i]);
                }
                System.out.println("Correct answer = ");
                for(int i=0;i<n;i++){
                    System.out.printf("%f ", ans[i]);
                }
                System.out.println();
                System.out.println("Got answer ");
                for(int i=0;i<n;i++){
                    System.out.printf("%f %n", solution[i]);
                }
                System.out.println();
                System.out.println();
            }
        }
        
    }

}
