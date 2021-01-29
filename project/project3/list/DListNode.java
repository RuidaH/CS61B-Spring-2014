/* DListNode.java */

package list;

/**
 *  A DListNode is a node in a DList (doubly-linked list).
 */

public class DListNode {

  /**
   *  item references the item stored in the current node.
   *  prev references the previous node in the DList.
   *  next references the next node in the DList.
   */

    public Object item;
    protected DList myList;
    protected DListNode prev;
    protected DListNode next;
    protected DListNode partner;
    protected Object ref;
    // If DListNode.item is an edge, refer back to the Vertex it belongs to
    // If DListNode.item is a vertex, refer back to the corresponding application vertex

    /**
     *  DListNode() constructor.
     *  @param i the item to store in the node.
     *  @param p the node previous to this node.
     *  @param n the node following this node.
     */
    DListNode(Object i, DList ml, DListNode p, DListNode n, Object v) {
      item = i;
      myList = ml;
      prev = p;
      next = n;
      ref = v;
    }
    
    public Object item() {
        return item;
    }
    
    public void setItem(Object item) {
        this.item = item;
    }
    
    public Object getRef() {
        return ref;
    }
    
    /**
     *  getPartner() return the other half of the edge
     *
     *  @return the other half of the edge (DListNode) in another adjacency list
     */
    public DListNode getPartner() {
        return this.partner;
    }

    /**
     *  setPartner() sets this node's partner to "partner".  If this node is invalid,
     *  throws an exception.
     *
     *  Performance:  runs in O(1) time.
     */
    public void setPartner(DListNode partner) {
      this.partner = partner;
    }
    
    public void remove() {
        this.prev.next = this.next;
        this.next.prev = this.prev;
        myList.size--;
    }
    
}
