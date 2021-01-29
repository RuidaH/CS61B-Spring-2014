/* Vertex.java */

package graph;

import list.*;

/**
 *  A Vertex is a node with multiple fields in the graph
 */

class Vertex {
    
    protected Object content;       // Object in the Vertex
    protected DList adjacencyList;  // Adjacency list of edges for each vertex
    protected int degrees;

    public Vertex(Object obj) {
        content = obj;
        adjacencyList = new DList();
        degrees = 0;
    }
    
    public Object getContent() {
        return content;
    }
    
    public int getDegree() {
        return degrees;
    }
    
    public void setDegree(int d) {
        degrees = d;
    }
    
    public DList getAdjacencyList() {
        return adjacencyList;
    }
    
}
