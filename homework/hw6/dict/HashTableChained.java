/* HashTableChained.java */

package dict;

import list.*;
import java.lang.Math;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
  private int     num_buckets;
  private int     entries;
  private List[]  buckets;
  private int     collisions;


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
      
      // sizeEstimate is the number of entries
      num_buckets = getPrime(sizeEstimate);
      buckets     = new SList[num_buckets];
      collisions  = 0;
      entries     = 0;
      
  }
    
  /**
   * Returns the next prime number after num. num is included
   *
   * @param num: the input number
   * @return the next prime number after num
   */
  private int getPrime(int num) {
      for (int time = 0; ; time++) {
          
          int result = num + time;
          boolean flag = true;
          
          for (int i = 2; i < result; i++) {
              if (result % i == 0) {
                  flag = false;
                  break;
              }
          }
          
          if (flag) return result;
          
      }
  }
   
  /**
   * Obtain the load factor of hash table.
   *
   * @return the load factor of hash table
   */
  private int loadFactor() {
      return entries / num_buckets;
  }
   
  /**
   * Resize the hash table when load factor is greater than 1.
   * The size of hash table will be twice as it was before.
   * All the entries should be reallocated to the new hash table
   */
  private void resize() {

      List[] new_buckets = new SList[num_buckets * 2];
      
      num_buckets = num_buckets * 2;
      
      Entry entry = null;
      
      for (int i = 0; i < (int)(num_buckets/2); i++) {
          if (buckets[i] != null) {
              ListNode node = ((SList)buckets[i]).front();
              
              do {
                  try {
                      entry = (Entry)node.item();
                  } catch (InvalidNodeException e) {
                      System.out.println("Catch the InvalidNodeException");
                  }
                  
                  int idx = compFunction(entry.key.hashCode());
                  if (new_buckets[idx] == null) {
                      new_buckets[idx] = new SList();
                  }
                  ((SList)new_buckets[idx]).insertFront(entry);
                  
                  try {
                      node = ((SListNode)node).next();
                  } catch (InvalidNodeException e) {
                      System.out.println("Catch the InvalidNodeException");
                  }
              } while (node != null);
                  
          }
      }
      
      buckets = new_buckets;
      
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {

      this(100);
      
  }
    
  public void CheckCollision() {
      System.out.println("The actual collision: " + collisions);
      double result = entries - num_buckets + (num_buckets * Math.pow((1 - (1/(double)num_buckets)), entries));
      System.out.println("The expected collision: " + result);
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {

      int temp = (3 * code + 5) % 145637;
      if (temp < 0)
          temp = temp + 145637;
      return temp % num_buckets;
      
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
      return entries;
  }
    
  public int lengthOfHashtable() {
      return num_buckets;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
      return entries == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
      
      int idx = compFunction(key.hashCode());
      
      if (buckets[idx] == null) {
          buckets[idx] = new SList();
      } else {
          // It means one or more entries already in this bucket
          collisions++;
      }
      
      Entry entry = new Entry();
      entry.key = key;
      entry.value = value;
      
      ((SList)buckets[idx]).insertFront(entry);
      entries++;
      
      // Resize the hashtable if load factor is greater than 1
      if (loadFactor() > 1) {
          resize();
      }
      
      return entry;
      
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
      
      int idx = compFunction(key.hashCode());
      Entry entry = null;
      
      if (buckets[idx] != null) {
          ListNode node = ((SList)buckets[idx]).front();
          
          do {
              try {
                  entry = (Entry)node.item();
              } catch (InvalidNodeException e) {
                  System.out.println("Catch the InvalidNodeException");
              }
              
              if (entry.key.equals(key)) {
                  return entry;
              }
              
              try {
                  node = ((SListNode)node).next();
              } catch (InvalidNodeException e) {
                  System.out.println("Catch the InvalidNodeException");
              }
          } while (node != null);
              
      }
      
      return null;
      
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
      
      int idx = compFunction(key.hashCode());
      Entry entry = null;
      
      if (buckets[idx] != null) {
          SListNode node = (SListNode)((SList)buckets[idx]).front();
          
          do {
              try {
                  entry = (Entry)node.item();
              } catch (InvalidNodeException e) {
                  System.out.println("Catch the InvalidNodeException");
              }
              
              if (entry.key.equals(key)) {
                  
                  try {
                      node.remove();
                  } catch (InvalidNodeException e) {
                      System.out.println("Catch the InvalidNodeException");
                  }
                  
                  entries--;
                  return entry;
                  
              }
              
              try {
                  node = (SListNode)node.next();
              } catch (InvalidNodeException e) {
                  System.out.println("Catch the InvalidNodeException");
              }
              
          } while (node != null);
              
      }
      
      return null;
      
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
      for (int i = 0; i < num_buckets; i++) {
          buckets[i] = null;
      }
      entries = 0;
  }
    
  public String toString() {
      String result = "[ \n";
      Entry entry = null;
      
      for (int i = 0; i < num_buckets; i++) {
          result = result + "{ ";
          if (buckets[i] != null) {
              ListNode node = ((SList)buckets[i]).front();
              
              try {
                  do {
                      entry = (Entry)node.item();
                      result = result + entry.key() + ", " + entry.value() + " ";
                      node = (SListNode)node.next();
                  } while (node.next() != null);
              } catch (InvalidNodeException e) {
                  ;
              }
              
          }
          result = result + "}\n";
      }
      
      return result + "]\n";
  }

}
