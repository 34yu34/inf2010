import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author maitr
 */
public class Node
{

  public int ordre;
  public Node parent;

  private int valeur;
  private ArrayList<Node> enfants;

  public Node(int valeur)
  {
    this.valeur = valeur;
    ordre = 0;
    this.parent = null;
    enfants = new ArrayList<Node>();
  }

  public Node(int valeur, Node parent)
  {
    ordre = 0;
    this.valeur = valeur;
    this.parent = parent;
    enfants = new ArrayList<Node>();
  }

  public int getVal()
  {
    return valeur;
  }

  public ArrayList<Node> getEnfants()
  {
    return enfants;
  }

  public void addEnfant(Node enfant)
  {
    enfants.add(enfant);
  }

  public boolean removeEnfant(Node enfant)
  {
    if (enfants.contains(enfant)) {
      enfants.remove(enfant);
      return true;
    }
    return false;
  }

  public void removeEnfants(ArrayList<Node> enfants)
  {
    this.enfants.removeAll(enfants);
  }

  /*********************************************************************
  * fuse two node of equal order
  *********************************************************************/
  public Node fusion(Node autre) throws DifferentOrderTrees
  {
    if (this.ordre != autre.ordre) {
      throw new DifferentOrderTrees();
    }
    if (this.parent == null && autre.parent == null) {
      if (this.getVal() >= autre.getVal()) {
        this.addEnfant(autre);
        autre.parent = this;
        this.ordre += 1;
        return this;
      } else {
        autre.addEnfant(this);
        autre.ordre += 1;
        this.parent = autre;
        return autre;
      }
    }
    return null;
  }

  /*********************************************************************
  * Swap this node with its parent
  *********************************************************************/
  private void moveUp()
  {
    if (this.parent == null) {
      return;
    }
    Node dad = this.parent;
    Node grandParent = dad.parent;
    ArrayList<Node> sibbling = dad.enfants;
    dad.parent = this;
    dad.enfants = this.enfants;
    this.parent = grandParent;
    this.enfants = sibbling;
    int tempOrdre = dad.ordre;
    dad.ordre = this.ordre;
    this.ordre = tempOrdre;
    this.removeEnfant(this);
    this.addEnfant(dad);
    if (grandParent != null) {
      grandParent.removeEnfant(dad);
      grandParent.addEnfant(this);
    }
  }

  /*********************************************************************
  * Grab the node to delete and swap it to the top then returns all the children
  * changes all the children parent node to null
  *********************************************************************/
  public ArrayList<Node> delete()
  {
    Node currNode = this;
    while (currNode.parent != null) {
      currNode.moveUp();
    }
    for (int i = 0; i < currNode.enfants.size(); i++) {
      currNode.enfants.get(i).parent = null;
    }
    return currNode.enfants;
  }

  public Node findValue(int valeur)
  {
    if (this.valeur == valeur) {
      return this;
    }
    for (int i = 0; i < this.enfants.size(); i++) {
      Node currNode = this.enfants.get(i);
      Node ans = currNode.findValue(valeur);
      if (ans != null) {
        return ans;
      }
    }
    return null;
  }

  /*********************************************************************
  * Heap sort : pop the root node to put it in the result
  * then grab the biggest element in the children node and
  * replace the root with it. repeat until node is empty.
  *********************************************************************/
  public ArrayList<Integer> getElementsSorted()
  {
    ArrayList<Integer> sol = new ArrayList();
    Node maxNode = this;
    ArrayList<Node> unsorted = new ArrayList();
    ArrayList<Node> brokenNode = new ArrayList();
    while (maxNode != null) {
      sol.add(maxNode.valeur);
      brokenNode = maxNode.enfants;
      for (int i = 0; i < brokenNode.size(); i++) {
        unsorted.add(brokenNode.get(i));
      }
      if (unsorted.size() > 0) {
        Node max = unsorted.get(0);
        for (int i = 0; i < unsorted.size(); i++) {
          if (unsorted.get(i).valeur > max.valeur) {
            max = unsorted.get(i);
          }
        }
        maxNode = max;
        unsorted.remove(maxNode);
      } else {
        maxNode = null;
      }
    }
    return sol;
  }

  public void print()
  {
    print("", true);
  }

  private void print(String prefix, boolean isTail)
  {
    System.out.println(prefix + (isTail ? "'-- " : "|-- ") + valeur);
    for (int i = 0; i < enfants.size() - 1; i++) {
      enfants.get(i).print(prefix + (isTail ? "    " : "|   "), false);
    }
    if (enfants.size() > 0) {
      enfants.get(enfants.size() - 1)
      .print(prefix + (isTail ? "    " : "|   "), true);
    }
  }
}