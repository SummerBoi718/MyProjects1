//2d array element replacement
public class testArrayd {
    public static void main(String x[]){
        String aa[][]=new String[10][10];
        String bb[][]={//the art
            {"w","t","f","x"},
            {"x","x","x","x"},
            {"b","r","u","h"},
            {"x","x","x","x"},
        };
        String cc[][]=new String[8][8];
        fill(aa, "0");//the backdrop
        fill(cc, " ");//the blank space
        //display(aa);
        
        replace(aa, cc, 1,1);
        replace(aa,bb,3,3);
        display(aa);
        
    }
    //standard method to display elements of 2d array
    static void display(String a[][]){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                System.out.print(a[i][j]+" ");
            }
            System.out.println();
        }
        //nextline for map discernability
        System.out.println();
    }
    //standard method to fill a 2d array with desired element
    static void fill(String a[][], String b){
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a[0].length;j++){
                a[i][j]=b;
            }
        }
    }
    //method to replace or overwrite elements in the target array in the target starting pos
    static void replace(String a[][],String b[][],int x,int y){
        //x and y value validation
        //array a is the primary array so check if array b and x smaller than a
        if(x >=0 && y >=0 && x + b[0].length <= a[0].length && y + b.length <= a.length){
            for(int i=0;i<b.length;i++){
                for(int j=0;j<b[0].length;j++){
                    a[x+i][y+j]=b[i][j];
                }
            }
        }else{
            System.out.println("Out of range");
        }
    }
}