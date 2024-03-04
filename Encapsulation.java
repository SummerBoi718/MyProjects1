
public class Encapsulation {
    public static void main(String x[]){
        Human obj=new Human("James",19,"Male");
        System.out.println(obj.getName());
        System.out.println(obj.getAge());
        System.out.println(obj.getSex());
        
        //System.out.println(obj.name); -> field name not visible
        //System.out.println(obj.age); -> field age not visible
        //System.out.println(obj.sex); -> field sex not visible
        
    }
}
class Human{
    private String name;
    private int age;
    private String sex;
    //
    Human(String nm, int ag, String sx){
        setName(nm);
        setAge(ag);
        setSex(sx);
    }
    public void setName(String n){
        this.name=n;
    }
    public String getName(){
        return this.name;
    }
    //
    public void setAge(int a){
        this.age=a;
    }
    public int getAge(){
        return this.age;
    }
    //
    public void setSex(String s){
        this.sex=s;
    }
    public String getSex(){
        return this.sex;
    }
}