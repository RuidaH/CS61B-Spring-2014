abstract class superclass {
    
    public abstract void printNumber(int new_arr);
    
}

interface Interface {
    
    public void printNumber(int arr);
    
}

public class Test2 extends superclass implements Interface {
    
    public void printNumber(int arr) {
        System.out.println("You can print the number: " + arr);
    }
    
    public static void main(String[] args) {
        Test2 TestInstance = new Test2();
        TestInstance.printNumber(3);
    }
}
