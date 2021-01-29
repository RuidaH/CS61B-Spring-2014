package list;

class myDListTest {
    
    public static void main(String[] args) {
        
        DList dl = new DList();
        
        System.out.println("\n### TESTING insertFront ###\nEmpty list is " + dl);
        
        dl.insertFront(2);
        System.out.println("\nInserting 2 at front.\nList with 2 is " + dl);
        if ((int)dl.head.next.item != 2) {
            System.out.println("head.next.item is wrong.");
        }
        if (dl.head.next.prev != dl.head) {
            System.out.println("invariant 'if x.next == y, then y.prev == x' is wrong.");
        }
        if ((int)dl.head.prev.item != 2) {
            System.out.println("head.prev.item is wrong.");
        }
        if (dl.size != 1) {
            System.out.println("size is wrong.");
        }
        
        dl.insertBack(3);
        System.out.println("\nInserting 9 at back.\nList with 3 is " + dl);
        if ((int)dl.head.prev.item != 3) {
            System.out.println("head.prev.item is wrong.");
        }
        if ((int)dl.head.next.next.item != 3) {
            System.out.println("head.next.next.item is wrong.");
        }
        if (dl.size != 2) {
            System.out.println("size is wrong.");
        }
        
        DListNode cur = dl.front();
        System.out.println("\nShowing the front.\nList is " + dl);
        if ((int)cur.item != 2) {
            System.out.println("front is wrong");
        }
        
        DListNode node = dl.back();
        System.out.println("\nShowing the back.\nList is " + dl);
        if ((int)node.item != 3) {
            System.out.println("back is wrong");
        }
        
        cur = dl.next(cur);  // 3 (the last one for now)
        System.out.println("\nShowing the next of 2.\nList is " + dl);
        if ((int)cur.item != 3) {
            System.out.println("next is wrong");
        }
        
        node = dl.prev(node); // 2 (the first one for now)
        System.out.println("\nShowing the prev.\nList is " + dl);
        if ((int)node.item != 2) {
            System.out.println("prev is wrong");
        }
        
        dl.insertAfter("String1", node);
        System.out.println("\nInserting 'String1' after 2.\nList is " + dl);
        if (!dl.head.next.next.item.equals("String1")) {
            System.out.println("the item after node '2' is not 'String1'");
        }
        if (dl.size != 3) {
            System.out.println("size is wrong.");
        }
        
        dl.insertBefore("String2", cur);
        System.out.println("\nInserting 'String2' before 3.\nList is " + dl);
        if (!dl.head.prev.prev.item.equals("String2")) {
            System.out.println("the item before node '3' is not 'String2'");
        }
        if (dl.size != 4) {
            System.out.println("size is wrong.");
        }
        
    }
    
}
