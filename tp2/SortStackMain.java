import java.util.Random;
import java.util.Stack;


public class SortStackMain
{
  static final int COUNT = 30;
  static final int MAX_VALUE = 1000;

  public static void main(String[] args)
  {
    boolean sortIsGood = true;

    Random generator = new Random(System.nanoTime());
    Stack<Integer> stack = new Stack<Integer>();

    for (int i = 0; i < COUNT; i++)
      stack.push(generator.nextInt(MAX_VALUE));

    stack = sortStack(stack);

    boolean countIsGood = size(stack) == COUNT;

    int tmp = stack.pop();
    while (!stack.isEmpty()) {
      System.out.print(tmp + ", ");

      if (tmp > stack.peek())
        sortIsGood = false;

      tmp = stack.pop();
    }
    System.out.println(tmp);

    if (!countIsGood)
      System.out.println("Erreur: il manque des elements dans la pile");
    else if (!sortIsGood)
      System.out.println("Erreur: le trie a echoue");
    else
      System.out.println("It's all good");
  }

  private static int size(Stack<Integer> stack)
  {
    return stack.size();
  }

  static Stack<Integer> sortStack(Stack<Integer> stack)
  {
    int[] value = new int[stack.size()];
    for (int i = 0; i < value.length; i++) {
      value[i] = stack.pop();
    }
    // Bubble sort
    int transformation = -1;
    int temp;
    int size = value.length;
    while (transformation != 0) {
      transformation = 0;
      for (int i = 0; i < size - 1; i++) {
        if (value[i] > value[i + 1]) {
          temp = value[i];
          value[i] = value[i + 1];
          value[i + 1] = temp;
          transformation++;
        }
      }
      size--;
    }
    for (int i = value.length - 1; i >= 0; i--) {
      stack.push(value[i]);
    }
    return stack;
  }
}
