import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Iterator;

public class BnBSolver {
    
    private Puzzle puzzle;
    private Puzzle solution;
    private HashSet<Matrix> visited;
    private PriorityQueue<Puzzle> queue;
    private boolean found = false;
    private int counter = 0;

    public BnBSolver(Puzzle puzzle) {
        this.puzzle = puzzle;
        this.solution = null;
        puzzle.length = 0;
        puzzle.unmatch = puzzle.countUnmatch();
        visited = new HashSet<>();
        queue = new PriorityQueue<>(new BnBComparator());
    }

    public void search() {
        queue.add(puzzle);
        visited.add(puzzle.getFormation());

        while (!queue.isEmpty() && !found) {
            Puzzle currPuzzle = queue.poll();
            counter++;

            if (currPuzzle.isFinished()) {
                if (solution != null) {
                    if (solution.length > currPuzzle.length) {
                        solution = currPuzzle;
                    }
                } else {
                    solution = currPuzzle;
                }
                PriorityQueue<Puzzle> filtered = new PriorityQueue<>(new BnBComparator());
                Iterator<Puzzle> iterator = queue.iterator();
                while (iterator.hasNext()) {
                    Puzzle elem = iterator.next();
                    if (elem.priorityValue() <= solution.priorityValue()) {
                        filtered.add(elem);
                    }
                }
                if (filtered.isEmpty()) {
                    found = true;
                    continue;
                }
                queue = filtered;
                continue;
            }
            for (Point move : Puzzle.MOVEMENTS) {
                if (currPuzzle.isMoveValid(move)) {
                    Puzzle generated = new Puzzle(currPuzzle, move);
                    if (!visited.contains(generated.getFormation())) {
                        queue.add(generated);
                        visited.add(generated.getFormation());
                    }
                }
            }
        }
    }

    public Puzzle getSolution() {
        return solution;
    }

    public boolean isFound() {
        return found;
    }

    public int getCounterVisited() {
        return counter;
    }
    
    public static void main(String[] args) {
        Puzzle problem = new Puzzle();
        int[] probs = new int[]{4,1,2,6,7,8,5,3,9};
        problem.setFormation(probs);
        problem.setEmptyPoint(2, 2);
        BnBSolver solver = new BnBSolver(problem);

        // Runnable thread = new Runnable() {
        //     @Override
        //     public void run() {
        //         while (!Thread.currentThread().isInterrupted()) {
        //             try {
        //                 System.out.println("Banyak node: " + solver.getCounterVisited());
        //                 Thread.sleep(2000);
        //             } catch (InterruptedException e) {
        //                 Thread.currentThread().interrupt();
        //             }
        //         }
        //     }
        // };

        // Thread checkThread = new Thread(thread);
        // checkThread.start();
        solver.search();
        
        if (solver.isFound()) {
            System.out.println("Banyak node dikunjungi: " + solver.getCounterVisited() + "\nSolusi - "+solver.getSolution().length+":");
            solver.getSolution().displayPath();
        } else {
            System.out.println("No Solution found, counter: " + solver.getCounterVisited());
        }
        // checkThread.interrupt();
    }
}

class BnBComparator implements Comparator<Puzzle> {
    @Override
    public int compare(Puzzle obj1, Puzzle obj2) {
        return Integer.compare(obj1.priorityValue(), obj2.priorityValue());
    }
}
