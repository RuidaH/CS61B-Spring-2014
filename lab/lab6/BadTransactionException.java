
public class BadTransactionException extends Exception {

    public BadTransactionException(int BadTransactionNumber) {
        super("Invalid transaction number: " + BadTransactionNumber);
    }

}
