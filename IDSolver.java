
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
        int i = 1;
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
        for (Point move : Puzzle.MOVEMENTS) {
            if (currPuzzle.isMoveValid(move)) {
                Puzzle generated = new Puzzle(currPuzzle, move);
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

    public static void main(String[] args) {
        Puzzle problem = new Puzzle();
        int[] probs = new int[]{4,1,2,6,7,8,5,3,9};
        problem.setFormation(probs);
        problem.setEmptyPoint(2, 2);
        IDSolver solver = new IDSolver(problem);
        solver.search();
        
        if (solver.isFound()) {
            System.out.println("Banyak node dikunjungi: " + solver.getCounterVisited() + "\nSolusi - "+solver.getSolution().length+":");
            solver.getSolution().displayPath();
        } else {
            System.out.println("No Solution found, counter: " + solver.getCounterVisited());
        }

    }
}
