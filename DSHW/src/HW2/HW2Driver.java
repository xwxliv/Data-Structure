package HW2;

public class HW2Driver {
    public static void main(String[] args) {
        // You can change size and rotation values for your testing
        int size = 100000;
        int rotation = 30000;

        Josephus game = new Josephus();
        Stopwatch timer1 = new Stopwatch();
        System.out.println("Survivor's position: " + game.playWithAD(size, rotation));
        System.out
                .println("Computing time with ArrayDeque used as Queue/Deque: " + timer1.elapsedTime() + " millisec.");

        Stopwatch timer2 = new Stopwatch();
        System.out.println("Survivor's position: " + game.playWithLL(size, rotation));
        System.out
                .println("Computing time with LinkedList used as Queue/Deque: " + timer2.elapsedTime() + " millisec.");

        Stopwatch timer3 = new Stopwatch();
        System.out.println("Survivor's position: " + game.playWithLLAt(size, rotation));
        System.out
                .println("Computing time with LinkedList (remove with index) : " + timer3.elapsedTime() + " millisec.");
    }
}
