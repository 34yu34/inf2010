import java.util.Random;
import java.util.Stack;


public class SortStackMain
{
  static final int COUNT = 1000;
  static final int MAX_VALUE = 10000;

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
    Stack<Integer> stack2 = (Stack<Integer> )stack.clone();
    int count = 0;
    while (!stack2.empty()) {
      stack2.pop();
      count++;
    }
    return count;
  }

  static Stack<Integer> sortStack(Stack<Integer> stack)
  {
    Stack<Integer> stack2 = new Stack<Integer>();
    // Bubble sort
    int transformation = -1;
    int value1;
    int value2;
    int last;
    int size = stack.size();
    while (transformation != 0) {
      transformation = 0;
      value1 = stack.pop();
      value2 = stack.pop();
      last = 2;
      for (int i = 0; i < size - 2; i++) {
        if (value1 > value2) {
          stack2.push(value2);
          if (last == 2) {
            transformation++;
          }
          value2 = stack.pop();
          last = 2;
        } else if (value1 == value2) {
          stack2.push(value1);
          value1 = stack.pop();
          last = 1;
        } else {
          stack2.push(value1);
          if (last == 1) {
            transformation++;
          }
          value1 = stack.pop();
          last = 1;
        }
      }
      while (!stack2.empty()) {
        if (value1 > value2) {
          stack.push(value1);
          value1 = stack2.pop();
        } else {
          stack.push(value2);
          value2 = stack2.pop();
        }
      }
      if (value1 > value2) {
        stack.push(value1);
        stack.push(value2);
      } else {
        stack.push(value2);
        stack.push(value1);
      }
      size--;
    }
    return stack;
  }
}