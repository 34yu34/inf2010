public class ArrayQueue<AnyType> implements Queue<AnyType>
{
  private int size = 0;                 // Nombre d'elements dans la file.
  private int startindex = 0;           // Index du premier element de la file
  private AnyType[] table;

  @SuppressWarnings("unchecked")
  public ArrayQueue()
  {
    table = (AnyType[])new Object[1];
  }

  // Indique si la file est vide
  public boolean empty()
  {
    return size == 0;
  }

  // Retourne la taille de la file
  public int size()
  {
    return size;
  }

  // Retourne l'element en tete de file
  // Retourne null si la file est vide
  // complexit� asymptotique: O(1)
  public AnyType peek()
  {
    if (size > 0) {
      return table[startindex];
    }
    return null;

  }

  // Retire l'element en tete de file
  // complexite asymptotique: O(1)
  public void pop() throws EmptyQueueException
  {
    if (size == 0) {
      throw new EmptyQueueException();
    }
		
    if (startindex < table.length - 1) {
      startindex++;
      size--;
    }
    else {
      startindex = 0;
      size = 0;
    }
  }

  // Ajoute un element a la fin de la file
  // Double la taille de la file si necessaire (utiliser la fonction resize definie plus bas)
  // complexit� asymptotique: O(1) ( O(N) lorsqu'un redimensionnement est necessaire )
  public void push(AnyType item)
  {
    if (startindex + size + 1 > table.length) {
      resize(2);
    }
    table[size + startindex] = item;
    size++;
  }

  // Redimensionne la file. La capacite est multipliee par un facteur de resizeFactor.
  // Replace les elements de la file au debut du tableau
  // complexit� asymptotique: O(N)
  @SuppressWarnings("unchecked")
  private void resize(int resizeFactor)
  {
    int newLength = table.length * resizeFactor;
    AnyType[] newTable = (AnyType[])new Object[newLength];
    for (int i = startindex; i < size + startindex; i++) {
      newTable[i - startindex] = table[i];
    }
    startindex = 0;
    table = newTable;
  }
}
