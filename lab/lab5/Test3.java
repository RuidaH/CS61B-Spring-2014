abstract class superclass {
    
    public static final int CONSTANT = 10;
    
    public abstract void printNumber(int new_arr);
    
}

interface Interface {
    
    public static final int CONSTANT = 12;
    
    public void printNumber(int arr);
    
}

public class Test3 extends superclass implements Interface {
    
    public void printNumber(int arr) {
        System.out.println("You can print the number: " + arr);
    }
    
    public static void main(String[] args) {
        Test3 TestInstance = new Test3();
        TestInstance.printNumber(superclass.CONSTANT);
    }
}
