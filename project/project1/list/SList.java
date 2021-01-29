/* SList.java */

package list;

/**
 *  A SList is a mutable singly-linked list ADT.  Its implementation employs
 *  a tail reference.
 *
 *  DO NOT CHANGE THIS FILE.
 **/

public class SList extends List {

  /**
   *  (inherited)  size is the number of items in the list.
   *  head references the first node.
   *  tail references the last node.
   **/

  protected SListNode head;
  protected SListNode tail;

  /* SList invariants:
   *  1)  Either head == null and tail == null, or tail.next == null and the
   *      SListNode referenced by tail can be reached from the head by a
   *      sequence of zero or more "next" references.  This implies that the
   *      list is not circularly linked.
   *  2)  The "size" field is the number of SListNodes that can be accessed
   *      from head (including head itself) by a sequence of "next" references.
   *  3)  For every SListNode x in an SList l, x.myList = l.
   **/

  /**
   *  newNode() calls the SListNode constructor.  Use this method to allocate
   *  new SListNodes rather than calling the SListNode constructor directly.
   *  That way, only this method need be overridden if a subclass of SList
   *  wants to use a different kind of node.
   *
   *  @param item the item to store in the node.
   *  @param next the node following this node.
   **/
  protected SListNode newNode(Object item, SListNode next) {
    return new SListNode(item, this, next);
  }

  /**
   *  SList() constructs for an empty SList.
   **/
  public SList() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   *  insertFront() inserts an item at the front of this SList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertFront(Object item) {
    head = newNode(item, head);
    if (size == 0) {
      tail = head;
    }
    size++;
  }

  /**
   *  insertBack() inserts an item at the back of this SList.
   *
   *  @param item is the item to be inserted.
   *
   *  Performance:  runs in O(1) time.
   **/
  public void insertBack(Object item) {
    if (head == null) {
      head = newNode(item, null);
      tail = head;
    } else {
      tail.next = newNode(item, null);
      tail = tail.next;
    }
    size++;
  }

  /**
   *  front() returns the node at the front of this SList.  If the SList is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.
   *
   *  @return a ListNode at the front of this SList.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode front() {
    if (head == null) {
      // Create an invalid node.
      SListNode node = newNode(null, null);
      node.myList = null;
      return node;
    } else {
      return head;
    }
  }

  /**
   *  back() returns the node at the back of this SList.  If the SList is
   *  empty, return an "invalid" node--a node with the property that any
   *  attempt to use it will cause an exception.
   *
   *  @return a ListNode at the back of this SList.
   *
   *  Performance:  runs in O(1) time.
   */
  public ListNode back() {
    if (tail == null) {
      // Create an invalid node.
      SListNode node = newNode(null, null);
      node.myList = null;
      return node;
    } else {
      return tail;
    }
  }

  /**
   *  toString() returns a String representation of this SList.
   *
   *  @return a String representation of this SList.
   *
   *  Performance:  runs in O(n) time, where n is the length of the list.
   */
  public String toString() {
    String result = "[  ";
    SListNode current = head;
    while (current != null) {
      result = result + current.item + "  ";
      current = current.next;
    }
    return result + "]";
  }
}
