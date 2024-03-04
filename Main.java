
class Employee {
    protected int employeeID;
    protected String name;
    protected String contactNumber;
    
    public Employee(int employeeID, String name, String contactNumber){
        this.employeeID=employeeID;
        this.name=name;
        this.contactNumber=contactNumber;
    }
    String displayEmpInfo(){
        String info="EMPLOYEE'S INFORMATION \n";
        info+="Employee ID :"+employeeID +"\n";
        info+="Name :"+name+"\n";
        info+="Contact Number :"+contactNumber+"\n";
        return info;
    }
   
}
class Programmer extends Employee{
    private String programmingLanguage="C";
    
    public Programmer(int employeeID, String name, String contactNumber){
        super(employeeID, name, contactNumber);
    }
    @Override
    String displayEmpInfo(){
        String addInfo="Programming Language :" + programmingLanguage+"\n";
        return (super.displayEmpInfo()+addInfo);
    }
    public void writeCode(){
        System.out.println(displayEmpInfo());
        System.out.println(" I am developing a Unix-like system using "+ programmingLanguage+"\n ==========================================\n");
    }
}
/////////////////

class Manager extends Employee{
    
     private String team = "Alpha";

     public Manager(int employeeID, String name, String contactNumber){
        super (employeeID, name, contactNumber);
     }
    

    public void reportStatus(){
  
    System.out.println(displayEmpInfo());

    System.out.println(" I am managing our Team " + team +"\n ===================================\n");
    }
    
    @Override 
    String displayEmpInfo(){

    String addInfo = "Team Name:" + team + "\n";
        
        return(super.displayEmpInfo()+addInfo);
    }
}

public class Main {
    
    public static void main(String[] args) {
        
        Programmer emp1=new Programmer(87654321,"Rakesh Patel","098762117");
        emp1.writeCode();
        Manager emp2=new Manager(73827372, "Rowan Akinston","09626372821");
        emp2.reportStatus();
        
    }
    
}

