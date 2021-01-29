package graphalg;

class NewEdge {
    
    protected Object end1;
    protected Object end2;
    protected int weight;
    
    public NewEdge(Object e1, Object e2, int w) {
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
    
    public Object getOneEnd() {
        return end1;
    }
    
    public Object getOtherEnd() {
        return end2;
    }
    
//    public Object getOtherEnd(Object e) {
//        if (end1.equals(e))  return end2;
//        else return end1;
//    }
    
    // Override the compareTo() for NewEdge
    public int compareTo(Object o) {
        int result;
        if (this.weight < ((NewEdge)o).getWeight()) {
            result = -1;
        } else if (this.weight == ((NewEdge)o).getWeight()) {
            result = 0;
        } else {
            result = 1;
        }
        return result;
    }
    

      public int hashCode() {
        if (end1.equals(end2)) {
          return end1.hashCode() + 1;
        } else {
          return end1.hashCode() + end2.hashCode();
        }
      }


      public boolean equals(Object o) {
        if (o instanceof NewEdge) {
          return ((end1.equals(((NewEdge) o).end1)) &&
                  (end2.equals(((NewEdge) o).end2))) ||
                 ((end1.equals(((NewEdge) o).end2)) &&
                  (end2.equals(((NewEdge) o).end1)));
        } else {
          return false;
        }
      }
    
}

