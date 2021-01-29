class superclass {
    
    public static final int CONSTANT = 10;
    
    public void printNumber(int arr) {
        System.out.println("You can print the number: " + arr + " in superclass");
    }
    
}


public class Test4 extends superclass {
    
    public void printNumber(int arr) {
        System.out.println("You can print the number: " + arr + " in subclass");
    }
    
    public static void main(String[] args) {
        // Still call the method in subclass
        Test4 subclass_variable = new Test4();
        ((superclass)subclass_variable).printNumber(10);
        
        // You will get the run-time cast exception
        superclass superclass_variable = new superclass();
        ((Test4)superclass_variable).printNumber(10);
    }
    
}
