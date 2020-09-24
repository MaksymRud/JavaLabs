import java.util.Arrays;
import java.util.Comparator;
public class Main {
    public int[][] arrSort(int[][] arr) {
        Arrays.sort(arr, new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                if (a.length == 0 && b.length == 0)
                    return 0;
                else if(a.length > 0 && b.length == 0)
                    return -1;
                else if(a.length == 0)
                    return 1;
                else if(a[0] > b[0])
                    return -1;
                else if(a[0] < b[0])
                    return 1;
                else{
                    if(a.length > 1 && b.length > 1)
                        return Integer.compare(a[1], b[1]);
                    else
                        return a.length - b.length;
                }
            }
        });
        return arr;
    }
    public static void main(String[] args) {
        int arr[][] = {{1, 3, 4} ,{2, 2, 4}, {3, 2}, {}, {}, {1, 4}, {3, 3}, {3}};
        Main m = new Main();
        arr = m.arrSort(arr);
        for (int[] row : arr){
            System.out.println("row");
            for(int element : row){
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}
