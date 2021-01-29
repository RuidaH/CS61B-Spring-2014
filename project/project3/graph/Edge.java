/* Edge.java */

package graph;

import list.*;

class Edge {
    
    protected Object end1;
    protected Object end2;
    protected int weight;
    
    public Edge(Object e1, Object e2, int w) {
        end1 = e1;
        end2 = e2;
        weight = w;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public void updateWeight(int w) {
        weight = w;
    }
    
    public Object getOtherEnd(Object e) {
        if (end1.equals(e))  return end2;
        else return end1;
    }
    
}
