
class X {
    public int x;
}

public class Y extends X {
    public int y;
}

public class Test1 {
    public static void main(String[] args) {
        
        X[] xa1 = null;
        Y[] ya1 = null;
        xa1 = ya1;
        ya1 = (Y[])xa1;
        
        X[] xa2;
        Y[] ya2 = new Y[3];
        xa2 = ya2;
        ya2 = (Y[])xa2;
        
        X[] xa3 = new X[4];
        Y[] ya3;
        // Since X is the superclass of Y, you cannot cast the superclass to subclass
        ya3 = (Y[])xa3;
        xa3 = ya3;
    }
}
