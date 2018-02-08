
public class LinkedListQueue<AnyType> implements Queue<AnyType>
{
	// Un noeud de la file
	@SuppressWarnings("hiding")
	private class Node<AnyType>
	{
		private AnyType data;
		private Node next;

		public Node(AnyType data, Node next)
		{
			this.data = data;
			this.next = next;
		}

		public void setNext(Node next)
		{
			this.next = next;
		}

		public Node<AnyType> getNext()
		{
			return next;
		}

		public AnyType getData()
		{
			return data;
		}
	}

	private int size = 0;		//Nombre d'elements dans la file.
	private Node<AnyType> last;	//Dernier element de la liste

	//Indique si la file est vide
	public boolean empty()
	{
		return size == 0;
	}

	//Retourne la taille de la file
	public int size()
	{
		return size;
	}

	//Retourne l'element en tete de file
	//Retourne null si la file est vide
	//complexit� asymptotique: O(1)
	public AnyType peek()
	{
		if (size > 0) {
			AnyType value =  last.getNext().getData();
			return value;
		}
		return null;
	}

	//Retire l'element en tete de file
	//complexit� asymptotique: O(1)
	public void pop() throws EmptyQueueException
	{
		if (size-1 > 0)
		{
			last.setNext(last.getNext().getNext());
		} else if (size == 1) {
			last = null;
		} else {
			throw new EmptyQueueException();
		}
		size--;
	}

	//Ajoute un element a la fin de la file
	//complexit� asymptotique: O(1)
	public void push(AnyType item)
	{
		if (last == null) {
			Node<AnyType> node = new Node(item, null);
			node.setNext(node);
			last = node;
		} else {
			Node<AnyType> node = new Node(item, last.getNext());
			last.setNext(node);
			last = node;
		}
		size++;
	}
}
