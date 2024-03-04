
public class Polymorphism {
    public static void main(String args[]){
        Shape obj=new Square();
        Shape obj2=new Diamond();
        //displayForm method is invoked based on the type of the obj
        obj.displayForm();
        obj2.displayForm();
}
}
class Shape{
    String form[];
    String name;
    void displayForm(){
        System.out.println(form);
    }
}
class Square extends Shape{
    String form[]={
        "____________\n",
        "|           |\n",
        "|           |\n",
        "|           |\n",
        "|           |\n",
        "|___________|\n",
    };
    String name="Square";
    @Override
    void displayForm(){
        System.out.println("Shape name :"+this.name);
        for(int i=0;i<form.length;i++){
            System.out.print(form[i]);
        }
    }
}
class Diamond extends Shape{
    String form[] = {
    "    /\\\n",
    "   /  \\\n",
    "  /    \\\n",    
    "  \\    / \n",
    "   \\  / \n",
    "    \\/ \n"
};
    String name="Diamond";
    @Override
    void displayForm(){
        System.out.println("Shape name :"+this.name);
        for(int i=0;i<form.length;i++){
            System.out.print(form[i]);
        }
    }

}
