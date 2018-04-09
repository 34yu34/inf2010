import java.util.ArrayList;

public class Monceau
{
  ArrayList<Node> arbres;

  public Monceau()
  {
    arbres = new ArrayList<Node>();
  }

  /*********************************************************************
  * Insertion sort to make sure all nodes are in good order
  * The variable that is compare is order and not their value
  *********************************************************************/
  public void setArbres(ArrayList<Node> arbre)
  {
    for (int i = 0; i < arbre.size(); i++) {
      int min = arbre.get(0).ordre;
      int indexMin = 0;
      for (int j = arbre.size() - i - 1; j >= 0; j--) {
        if (arbre.get(j).ordre < min) {
          min = arbre.get(0).ordre;
          indexMin = j;
        }
      }
      Node nodeMin = arbre.remove(indexMin);
      arbre.add(nodeMin);
    }
    this.arbres = arbre;
  }

  /*********************************************************************
  * permit the fusion of two Monceau
  * it follow a list of rules to make sure they stay in order
  * The reason fo the length is to reduce the complexity as much as possible
  *********************************************************************/
  public void fusion(Monceau autre)
  {
    Node retenue = null;
    int i = 0;
    int j = 0;
    int ordre = 0;
    try {
      while (j < autre.arbres.size()) {
        // if we finish our current arbres we blindly add autre element
        if (i >= this.arbres.size()) {
          this.arbres.add(autre.arbres.get(j));
          j++;
        } else {
          Node currNode = this.arbres.get(i);
          Node otherNode = autre.arbres.get(j);
          // we check if they are the same order to choose wich one to add
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
              i++;
            }
            j++;
          } else if (currNode.ordre == ordre) {
            if (retenue != null) {
              retenue = this.arbres.remove(i).fusion(retenue);
            } else {
              i++;
            }
            // If none of them are of good order and there is a retenue
            // we add the retenue
          } else if (retenue != null) {
            this.arbres.add(i, retenue);
            i++;
            retenue = null;
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

  /*********************************************************************
  * Creates a Simple Monceaux to fuse the current one with
  *********************************************************************/
  public void insert(int val)
  {
    Node simpleNode = new Node(val);
    Monceau adder = new Monceau();
    adder.arbres.add(simpleNode);
    this.fusion(adder);
  }

  /*********************************************************************
  * This delete all the node of the value val
  *********************************************************************/
  public boolean delete(int val)
  {
    boolean found = false;
    for (Node node : this.arbres) {
      Node currNode = node.findValue(val);
      if (currNode != null) {
        this.arbres.remove(node);
        ArrayList<Node> arbre = currNode.delete();
        Monceau fusionneur = new Monceau();
        // this call insertion sort to make sure it is in good order
        fusionneur.setArbres(arbre);
        this.fusion(fusionneur);
        found = true;
        this.delete(val);
        break;
      }
    }
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