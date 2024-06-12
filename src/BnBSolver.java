package src;

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
        // visited.add(puzzle.getFormation());

        while (!queue.isEmpty() && !found) {
            Puzzle currPuzzle = queue.poll();
            visited.add(currPuzzle.getFormation());

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
            for (String key : Puzzle.MOVEMENTS.keySet()) {
                Point move = Puzzle.MOVEMENTS.get(key);
                if (currPuzzle.isMoveValid(move)) {
                    Puzzle generated = new Puzzle(currPuzzle, move, key);
                    if (!visited.contains(generated.getFormation())) {
                        queue.add(generated);
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
}

class BnBComparator implements Comparator<Puzzle> {
    @Override
    public int compare(Puzzle obj1, Puzzle obj2) {
        return Integer.compare(obj1.priorityValue(), obj2.priorityValue());
    }
}
