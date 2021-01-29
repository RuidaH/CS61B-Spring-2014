/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import dict.*;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g) {
      
      int i, j;
      
      // (1) Create a new graph having the same vertices as g
      // (2) Make an array of all the edges in g
      WUGraph result = new WUGraph();
      Object[] allVertices = g.getVertices();

      int idx = 0;
      NewEdge tempEdge;
      Neighbors neighbors;
      NewEdge[] edges = new NewEdge[g.edgeCount()];
      HashTableChained EHashTable = new HashTableChained();
      
      for (i = 0; i < allVertices.length; i++) {
          result.addVertex(allVertices[i]);
          neighbors = g.getNeighbors(allVertices[i]);
          
          // Insert the edges into the hash table
          // To avoid repeated edges going into edges array
          for (j = 0; j < neighbors.neighborList.length; j++) {
              tempEdge = new NewEdge(allVertices[i], neighbors.neighborList[j], neighbors.weightList[j]);
              if (EHashTable.find(tempEdge) == null) {
                  EHashTable.insert(tempEdge, null);
                  edges[idx] = tempEdge;
                  idx++;
              }
          }
        
      }
      
      // (3) Sort all the edges by weight
      QuickSort sort = new QuickSort();
      sort.quicksort(edges);
      
      // (4) Find all the edges of new graph using disjoint sets
      HashTableChained Vertex2Integer = new HashTableChained();
      DisjointSets vertexSets = new DisjointSets(g.vertexCount());
      int v1, v2, setNumber;
      Object vertex1, vertex2;
      
      // Map the objects that serves as vertices to unique integer
      for (i = 0; i < g.vertexCount(); i++) {
          Vertex2Integer.insert(allVertices[i], i);
      }
      
      for (i = 0; i < edges.length; i++) {
          // Convert the vertices to corresponding unique integer
          vertex1 = edges[i].getOneEnd();
          vertex2 = edges[i].getOtherEnd();
          setNumber = vertexSets.getSetNumber();
          v1 = (int)Vertex2Integer.find(vertex1).value();
          v2 = (int)Vertex2Integer.find(vertex2).value();
          
          vertexSets.union(v1, v2);
          
          // If 2 sets union together, then we add the edge
          if (vertexSets.getSetNumber() == setNumber - 1) {
              result.addEdge(vertex1, vertex2, edges[i].getWeight());
          }
          
      }
      
      return result;
 
  }

}

/**
 * Actually, you don't need to get rid of the repeated edges
 * because in AddEdge(), if you add repeated edges, it only updates the weight.
 *
 * If you don't care about the repeated edges, you better use the linked list to store the edge
 * because you don't the length of array any more due to the repeated edges
 */


