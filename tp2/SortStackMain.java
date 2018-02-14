import java.util.Random;
import java.util.Stack;


public class SortStackMain
{
  static final int COUNT = 300_000;
  static final int MAX_VALUE = 1_000_000;

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
    Stack<Integer> stackLeft = new Stack<Integer>();
    Stack<Integer> stackRight = new Stack<Integer>();
    int count = 0;
    while (!stack.empty()) {
      stackLeft.push(stack.pop());
      count++;
      if (!stack.empty()) {
        stackRight.push(stack.pop());
        count++;
      }
    }
    if (count > 2) {
      sortStack(stackLeft);
      sortStack(stackRight);
    }
    Stack<Integer> tempStack = new Stack<Integer>();
    boolean rightEmpty = false;
    boolean leftEmpty = false;
    int valLeft = stackLeft.pop();
    int valRight;
    if (count > 1) {
      valRight = stackRight.pop();
    } else {
      rightEmpty = true;
      valRight = 0;
    }
    while (!leftEmpty && !rightEmpty) {
      if (valRight < valLeft) {
        tempStack.push(valRight);
        if (!stackRight.empty()) {
          valRight = stackRight.pop();
        } else {
          rightEmpty = true;
        }
      } else {
        tempStack.push(valLeft);
        if (!stackLeft.empty()) {
          valLeft = stackLeft.pop();
        } else {
          leftEmpty = true;
        }
      }
    }
    if (rightEmpty) {
      tempStack.push(valLeft);
      transfertStack(tempStack, stackLeft);
    } else {
      tempStack.push(valRight);
      transfertStack(tempStack, stackRight);
    }
    transfertStack(stack, tempStack);
    return stack;
  }
  /*********************************************************************
  * Transfert whats in giver on top of receiver
  *********************************************************************/
  static void transfertStack(Stack<Integer> receiver, Stack<Integer> giver)
  {
    while (!giver.empty()) {
      receiver.push(giver.pop());
    }
  }

}