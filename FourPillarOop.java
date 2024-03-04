/*
 Encapsulation ✓
 Inheritance ✓
 Polymorphism ✓
 Abstraction ✓
*/
import java.util.ArrayList;
// i chose ArrayList to add Human references into Human array so that i can iterate over them and invoke their methods easily sir.
public class FourPillarOop {
    //x as the identifier for positional variable is justified because in this code i wont be passing positional argument/cli arg and x as name is just for convention sir.
    public static void main(String x[]){
        Human human1=new Asian("James");
        Human human2=new American("Rose");
        Human human3=new Australian("Lily");
        Human.displayhumanCount();
        //for loop to make all Human references invoke sayName method
        for(int i=0;i<Human.humanArray.size();i++){
            //this is also abstraction as i use objects without specifying their specific class and only by their reference type which is Human class
            Human humanObjs=Human.humanArray.get(i);
            humanObjs.sayName();
        }
    }
}
//Abstraction
//Human class abstracts the common features of Asian, American, and Australian subclasses
abstract class Human{
    //Encapsulation of class members
    private String name;
    private static int humanObjCount;
    //the Arraylist to store Human references
    public static ArrayList<Human> humanArray=new ArrayList<Human>();
    Human(String n){
        this.name=n;
        this.humanObjCount++;
        //adding the current instance into the humanArray to use them later.
        humanArray.add(this);
    }
    public String getName(){
        return name;
    }
    public static int getHumanCount(){
        return humanObjCount;
    }
    //the abstract method to be implemented by the subclasses
    public abstract void sayName();
    
    public static void displayhumanCount(){
        System.out.println("Total Human Objects :"+getHumanCount());
    }
}
//Inheritance
class Asian extends Human{
    
    Asian(String n){
        //invoking the super constructor sir for reusability
        super(n);
    }
    /*I included @Override tokens for overridden methods
    in my Java code because im inspired by industry
    best practices observed in the code of
    experienced Java professionals on platforms
    like GitHub, where explicit usage of 
    @Override is a common and recommended practice
    for clarity sir.
    */
    
    
    //Polymorphism or runtime polymorphism
    @Override
    public void sayName(){
        System.out.println("Hi, my name is "+this.getName()+ ", and Im an Asian!");
    }
}
class American extends Human{
    
    American(String n){
        super(n);
    }
    @Override
    public void sayName(){
        System.out.println("Hi, my name is "+this.getName()+ ", and Im an American!");
    }
}
class Australian extends Human{
    
    Australian(String n){
        super(n);
    }
    @Override
    public void sayName(){
        System.out.println("Hi, my name is "+this.getName()+ ", and Im an Australian!");
    }
}