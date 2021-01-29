package list;

class LockDListNode extends DListNode {
    
    boolean locked;
    
    public LockDListNode(Object i, DListNode p, DListNode n) {
        super(i, p, n);
        locked = false;
    }
}
