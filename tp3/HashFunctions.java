import java.util.ArrayList;
import java.util.Random;
import java.io.PrintWriter;
import java.io.File;
import java.io.*;

public class HashFunctions
{
  static int p = 46337;

  public static void main(String[] args) throws IOException
  {
    // Donnees brutes
    Integer[] array = {103, 75, 64, 25, 36, 101, 110, 92,
                       200, 175, 164, 125, 136, 201, 111, 192,
                       300, 275, 264, 225, 236, 301, 311, 292};

    // Les donnees sont inserees dans un ArrayList
    ArrayList<Integer> al = new ArrayList<Integer>(array.length);

    for (Integer item : array) al.add(item);

    /**
     * Exercice 1
     */
    // On cree un QuadraticSpacePerfectHashing et insere les donnees
    System.out.println("QuadraticSpacePerfectHashing:");
    System.out.println();

    QuadraticSpacePerfectHashing<Integer> e = new QuadraticSpacePerfectHashing<Integer>(al);

    // Verifie les proprietes d'occupation d'espace
    System.out.println("Number of elements: " + al.size());
    System.out.println("Size: " + e.Size());
    System.out.println();

    // Verifie qu'il fonctionne comme prevu
    System.out.println(100 + " est present: " + e.contains(100));
    System.out.println(99 + " est present: " + e.contains(99));
    System.out.println(200 + " est present: " + e.contains(200));
    System.out.println(199 + " est present: " + e.contains(199));
    System.out.println(300 + " est present: " + e.contains(300));
    System.out.println(299 + " est present: " + e.contains(299));
    System.out.println();

    /**
     * Exercice 2
     */
    // On cree un LinearSpacePerfectHashing et insere les memes donnees
    System.out.println("LinearSpacePerfectHashing:");
    System.out.println();

    LinearSpacePerfectHashing<Integer> pfhash = new LinearSpacePerfectHashing<Integer>(al);

    // Verifie les proprietes d'occupation d'espace
    System.out.println("Number of elements: " + al.size());
    System.out.println("Size: " + pfhash.Size());
    System.out.println();

    // Verifie qu'il fonctionne comme prevu
    System.out.println(100 + " est present: " + pfhash.contains(100));
    System.out.println(99 + " est present: " + pfhash.contains(99));
    System.out.println(200 + " est present: " + pfhash.contains(200));
    System.out.println(199 + " est present: " + pfhash.contains(199));
    System.out.println(300 + " est present: " + pfhash.contains(300));
    System.out.println(299 + " est present: " + pfhash.contains(299));
    System.out.println();

    /**
     * Question 1 (confirmation des resultats de Exercice 2)
     */
    // Effectues quelques tests aleatoires pour verifier les proprietes de taille

    pfhash = new LinearSpacePerfectHashing<Integer>();
    System.out.println("Tests aleatoires");
    PrintWriter writer = new PrintWriter("pts.txt");
    for (int i = 0, nbElements = 10; i < 40; ++i, nbElements += 10) {
      pfhash.SetArray(randomIntegers(nbElements));
      writer.println(nbElements + "\t" + pfhash.Size());
    }
    writer.close();
  }

  /**
   * Question 1
   */
  public static ArrayList<Integer> randomIntegers(int length)
  {
    ArrayList<Integer> items = new ArrayList<Integer>();
    Random generator = new Random(System.nanoTime());
    for (int i = 0; i < length; i++) {
      int number = generator.nextInt(p);
      while (items.contains(number)) {
        number = generator.nextInt(p);
      }
      items.add(number);
    }
    return items;
  }
}