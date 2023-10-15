import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
public class AsciiGame {
    public static void main(String args[]){
        Scanner input=new Scanner(System.in);
        Player player=new Player();
        Draw draw=new Draw();
        //first display of the map
        //displays the player at the starting position
        draw.display(player.getXpos());
        // variable hold value to continue the game
        boolean condition=true;
        // arrays containing the wall indeces
        //wall_1 contains indeces for \n||
        int wall_1[]=draw.findWalls_1(draw.map);
        //wall_2 contains indeces for ||
        int wall_2[]=draw.findWalls_1(draw.map);
        //prints out the indeces containing \n||
        for (int index : draw.findWalls_1(draw.map)){
            if(index>0){
            System.out.println(index+"");
            }
        }
        //prints out the indeces contqining ||
        for (int index : draw.findWalls_2(draw.map)){
            if(index>0){
                System.out.println(index+"");
            }
        }
        
        
        //main loop
        while (condition==true){
            
            //Ask for player input: xPos
            System.out.println("Steps to be taken--->");
            int xposition=input.nextInt();
            //con to find if the player xpos is same as wall indeces
            if (Arrays.asList(wall_1).contains(xposition)){
                xposition+=1;
                draw.display(xposition);
            }else if(Arrays.asList(wall_2).contains(xposition)){
                xposition+=2;
                draw.display(xposition);
            }else{
            //display map and player
            draw.display(xposition);
            }
            
        }
    }
}
class Player{
    //player position
    private static int xpos=1;
    public int getXpos(){
        return xpos;
    }
    public void setXpos(int a){
        xpos=a;
    }
    
    
}
class Draw{
    
    //the map
    static String map[]={
        "\n||","0","0","0","0","0","||",
        "\n||","0","0","0","0","0","||",
        "\n||","0","0","0","0","0","||",
        "\n||","0","0","0","0","0","||",
        "\n||","0","0","0","0","0","||",
        "\n||","0","0","0","0","0","||",
        "\n||","0","0","0","0","0","||",
        "\n||","0","0","0","0","0","||\n"
    };
    //for referencing and displaying the map
    //on console as passing it to the
    //println method will print its memory
    //location rather than its values
    static List<String> mapList=Arrays.asList(map);
    // Forbidden numbers index which is || characters
    //static Integer walls_1[]={0,6,7,13,14,20,21,27,28};
    //Walls indeces in map array
    //sanitized wall array without zeroes
    int[] notfinalWalls_1=(Draw.findWalls_1(map));
    int[] finalWall={};
    // method fot displaying map conviently
    static void display(int a){
        map[a]="#";
        System.out.println(mapList);
        //revert the changed "0" value 
        //with "0" again so no duplicating
        map[a]="0";
    }
    //method to find the indeces containing
    // \n|| and || string
    //method to find indeces containing \n|| string
    static int[] findWalls_1(String[] a){
        int [] results= new int[map.length];
        for(int i=0;i<map.length;i++){
            if(map[i].equals("\n||")){
                results[i]=i;
            }
        }
        return results;
    }

    
    static int[] findWalls_2(String[] a){
        int [] results=new int[map.length];
        for(int i=0;i<map.length;i++){
            if(map[i].equals("||")){
                results[i]=i;
            }
        }
        return results;
    }
    
}

