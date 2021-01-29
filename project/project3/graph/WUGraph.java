/* WUGraph.java */

package graph;

import list.*;
import dict.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
    
    protected int vertexNum;
    protected int edgeNum;
    protected DList vertices;
    protected HashTableChained VHashTable;
    protected HashTableChained EHashTable;

  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
      VHashTable = new HashTableChained();
      EHashTable = new HashTableChained();
      vertices = new DList();
      vertexNum = 0;
      edgeNum = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
      return vertexNum;
  }

  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
      return edgeNum;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
      // Return application object instead of actual Vertex
      Object[] allVertices;
      
      if (vertexNum == 0) {
          allVertices = new Object[0];
      } else {
          allVertices = new Object[vertexNum];
          DListNode cur = (DListNode)vertices.front();
          allVertices[0] = ((Vertex)cur.item()).getContent();
          for (int i = 1; i < vertexNum; i++) {
              cur = vertices.next(cur);
              allVertices[i] = ((Vertex)cur.item()).getContent();
          }
      }
      
      return allVertices;
      
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
      
      Vertex v = new Vertex(vertex);
      
      // Insert the vertex into the HashTableChained
      if (VHashTable.find(vertex) == null) {
          
          // v: the item of DListNode
          // vertex: reference to the original object
          vertices.insertFront(v, vertex);
          
          // vertex: key of entry
          // vertices.front(): (value of entry) reference to the DListNode in DList
          VHashTable.insert(vertex, vertices.front());
          
          vertexNum++;

      }

  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
      
      // Hinted by others
      if (!isVertex(vertex)) {
          return;
      }
      
      Entry node = VHashTable.find(vertex);
      
      DList dl = ((Vertex)((DListNode)node.value()).item()).getAdjacencyList();
      DListNode partner;
      
      while(!dl.isEmpty()) {
          partner = ((DListNode)dl.front()).getPartner();
          removeEdge(vertex, ((DListNode)partner.getRef()).getRef()); // Remove the edge first
      }
      
      // Remove the vertex
      ((DListNode)node.value()).remove();
      
      // Remove the vertex in hash table
      Entry useless = VHashTable.remove(vertex);
      
      vertexNum--;
      
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
      return VHashTable.find(vertex) != null;
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
      int degreeNumber = 0;
      Entry node = VHashTable.find(vertex);
      if (node != null)
          degreeNumber = ((Vertex)((DListNode)node.value()).item()).getDegree();
      
      return degreeNumber;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
      
      if (VHashTable.find(vertex) == null ||
          ((Vertex)((DListNode)VHashTable.find(vertex).value()).item()).getAdjacencyList().length() == 0) {
          return null;
      }
      
      Neighbors neighbors = new Neighbors();
      Vertex v = (Vertex)((DListNode)VHashTable.find(vertex).value()).item();
      neighbors.neighborList = new Object[v.getAdjacencyList().length()];
      neighbors.weightList = new int[v.getAdjacencyList().length()];
      
      DListNode cur = (DListNode)v.getAdjacencyList().front();
      for (int i = 0; i < v.getAdjacencyList().length(); i++) {
          neighbors.neighborList[i] = ((Edge)cur.item()).getOtherEnd(vertex);
          neighbors.weightList[i] = ((Edge)cur.item()).getWeight();
          cur = v.getAdjacencyList().next(cur);
      }
      
      return neighbors;

  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u.equals(v)) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
      
      if (VHashTable.find(u) == null || VHashTable.find(v) == null) {
          return;
      }
      
      VertexPair vPair = new VertexPair(u, v);
      Entry node = EHashTable.find(vPair);
      
      // If the edge already exists, update the weight
      if (node != null) {
          // Update the weight of each half edge
          ((Edge)((DListNode)node.value()).item()).updateWeight(weight);
          ((Edge)((DListNode)node.value()).getPartner().item()).updateWeight(weight);
      } else {
          Edge edge1 = new Edge(u, v, weight);
          Edge edge2 = new Edge(v, u, weight);
          
          // Find the corresponding adjacency list of each vertex
          Vertex v1 = (Vertex)((DListNode)VHashTable.find(u).value()).item();
          Vertex v2 = (Vertex)((DListNode)VHashTable.find(v).value()).item();
          DList uList = v1.getAdjacencyList();
          DList vList = v2.getAdjacencyList();
          
          // Insert the Edge into the adjacency list of each vertex
          // If they are the same vertex, just insert once
          if (u.equals(v)) {
              uList.insertFront(edge1, VHashTable.find(u).value());
              ((DListNode)uList.front()).setPartner(((DListNode)vList.front()));
          } else {
              // Ref points back to the DListNode of the vertices
              uList.insertFront(edge1, VHashTable.find(u).value());
              vList.insertFront(edge2, VHashTable.find(v).value());
              
              // Set the half edge-reference
              ((DListNode)uList.front()).setPartner(((DListNode)vList.front()));
              ((DListNode)vList.front()).setPartner(((DListNode)uList.front()));
          }

          // Insert the vertexPair (key of entry) into the edge hashTable
          EHashTable.insert(vPair, uList.front());
          
          // Update the degree of each vertex and the edge number
          if (u.equals(v)) {
              v1.setDegree(v1.getDegree() + 1);
          } else {
              v1.setDegree(v1.getDegree() + 1);
              v2.setDegree(v2.getDegree() + 1);
          }
          edgeNum++;
      }
      
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v) {
      
      if (VHashTable.find(u) == null || VHashTable.find(v) == null) {
          return;
      }
      
      VertexPair vPair = new VertexPair(u, v);
      Entry node = EHashTable.find(vPair);
      
      // If the edge exists in the graph
      if (node != null) {
          if (u.equals(v)) {
              ((DListNode)node.value()).remove();
              
              Vertex v1 = (Vertex)((DListNode)VHashTable.find(u).value()).item();
              
              v1.setDegree(v1.getDegree() - 1);
          } else {
              DListNode partner = ((DListNode)node.value()).getPartner();
              ((DListNode)node.value()).remove();
              partner.remove();
              
              Vertex v1 = (Vertex)((DListNode)VHashTable.find(u).value()).item();
              Vertex v2 = (Vertex)((DListNode)VHashTable.find(v).value()).item();
              
              // Update the degree of each vertex and the edge number
              v1.setDegree(v1.getDegree() - 1);
              v2.setDegree(v2.getDegree() - 1);
          }
          
          edgeNum--;
          
          // Remove the vertexPairs in the edge hash table
          Entry useless = EHashTable.remove(vPair);
      }

  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
      VertexPair vPair = new VertexPair(u, v);
      return EHashTable.find(vPair) != null;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
      int Weight = 0;
      VertexPair vPair = new VertexPair(u, v);
      Entry node = EHashTable.find(vPair);
      
      if (node != null) {
          Weight = ((Edge)((DListNode)node.value()).item()).getWeight();
      }
      
      return Weight;
      
  }

}
