package src;

public class IDSolver {
    private Puzzle puzzle;
    private Puzzle solution;
    private boolean found = false;
    private int counter = 0;

    public IDSolver(Puzzle puzzle) {
        this.puzzle = puzzle;
        this.solution = null;
        puzzle.length = 0;
        puzzle.unmatch = puzzle.countUnmatch();
    }

    public void search() {
        int i = 0;
        while (!found) {
            worker(puzzle, i, 0);
            i++;
        }
    }

    public void worker(Puzzle currPuzzle, int maxDepth, int depth) {
        if (found || depth >= maxDepth) {
            return;
        } 
        counter++;
        if (currPuzzle.isFinished()) {
            found = true;
            solution = currPuzzle;
            return;
        }
        for (String keys : Puzzle.MOVEMENTS.keySet()) {
            Point move = Puzzle.MOVEMENTS.get(keys);
            if (currPuzzle.isMoveValid(move)) {
                Puzzle generated = new Puzzle(currPuzzle, move, keys);
                worker(generated, maxDepth, depth+1);
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
