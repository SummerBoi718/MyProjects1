
public class Inheritance {
    public static void main(String x[]){
        Parent obj1=new Parent();
        obj1.say();
        Child obj2=new Child();
        obj2.say();
    }
}
class Parent{
    String ID="Parent Obj";
    
    void say(){
        System.out.println("Parent method invoked by :"+this.ID);
    }
    
}
class Child extends Parent{
    String ID="Child Obj";
    @Override
    void say(){
        System.out.println("Parent method invoked by :"+this.ID);
    }
}