import java.util.ArrayList;

public class Monceau
{
  ArrayList<Node> arbres;

  public Monceau()
  {
    arbres = new ArrayList<Node>();
  }

  public void fusion(Monceau autre)
  {
    // Pour simplifier le tout on garde les Noeuds en ordres
    Node retenue = null;
    int i = 0;
    int j = 0;
    int ordre = 0;
    try {
      while (j < autre.arbres.size()) {
        if (i >= this.arbres.size()) {
          this.arbres.add(autre.arbres.get(j));
          j++;
        } else {
          Node currNode = this.arbres.get(i);
          Node otherNode = autre.arbres.get(j);
          if (currNode.ordre == ordre && otherNode.ordre == ordre) {
            if (retenue != null) {
              retenue = retenue.fusion(otherNode);
              i++;
            } else {
              retenue = this.arbres.remove(i).fusion(otherNode);
            }
            j++;
          } else if (otherNode.ordre == ordre) {
            if (retenue != null) {
              retenue = retenue.fusion(otherNode);
            } else {
              this.arbres.add(i, otherNode);
            }
            j++;
          } else if (currNode.ordre == ordre) {
            if (retenue != null) {
              retenue = this.arbres.remove(i).fusion(retenue);
            } else {
              i++;
            }
          } else if (retenue != null) {
            this.arbres.add(i, retenue);
            i++;
          }
          ordre++;
        }
      }
      while (retenue != null) {
        if (i < this.arbres.size()) {
          if (retenue.ordre == this.arbres.get(i).ordre) {
            retenue = this.arbres.remove(i).fusion(retenue);
          } else {
            this.arbres.add(i, retenue);
            retenue = null;
          }
        } else {
          this.arbres.add(retenue);
          retenue = null;
        }
      }
    }
    catch (DifferentOrderTrees e) {
    }
  }

  public void insert(int val)
  {
    Node simpleNode = new Node(val);
    Monceau adder = new Monceau();
    adder.arbres.add(simpleNode);
    this.fusion(adder);
  }

  public boolean delete(int val)
  {
    boolean found = false;
    /*for (Node node : this.arbres) {
       Node currNode = node.findValue(val);
       while (currNode != null) {
        ArrayList<Node> arbre = node.removeEnfant(currNode);
        Monceau fusionneur = new Monceau();
        fusionneur.arbres = arbre;
        currNode = node.findValue(val);
       }
       node.print();
       found = currNode != null;
       }*/
    return found;
  }

  public void print()
  {
    for (Node node : arbres) {
      System.out.println("\n\nordre : " + node.ordre);
      node.print();
    }
  }

}