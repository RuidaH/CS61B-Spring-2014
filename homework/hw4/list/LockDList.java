package list;

/**
 * Actually, we don't need to use instanceof to check whether every DListNode is LockDListNode.
 * We just ensure that all the nodes in LockDList are LockDListNode.
 */
class LockDList extends DList {
    
    protected DListNode newNode(Object item, DListNode prev, DListNode next) {
        return new LockDListNode(item, prev, next);
    }
    
    /**
     *  lockNode() permanently locks "node". The DListNode is the static type, not LockDListNode.
     *  I chose this signature for the convenience of the users of your LockList
     *  It saves them the nuisance of having to cast every node they want to lock
     *  @param node : A DListNode in DList
     */
    public void lockNode(DListNode node) {
        if (node != null && node != head) {
            ((LockDListNode)node).locked = true;
        }
        // Equals to
        // LockDListNode lNode = (LockDListNode)node;
        // lNode.locked = true;
    }
    
    // Override the remove()
    // In LockDList, the parameter that you are passing in is LockDListNode
    // The type of DListNode is for the overriding
    // So we need to taking care of casting
    public void remove(DListNode node) {
        
        if ( (node != null) && ((LockDListNode)node).locked == false ) {
          super.remove(node);  // Prevent code duplication
      }
        
    }
    
}
