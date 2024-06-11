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

    public static void main(String[] args) {
        Puzzle problem = new Puzzle();
        int[] probs = new int[]{};
        problem.setFormation(probs);
        problem.setEmptyPoint(2, 2);
        
        IDSolver solver = new IDSolver(problem);
        
        long start = System.currentTimeMillis();
        
        solver.search();
        
        long end = System.currentTimeMillis();
        long diff = end - start;
        System.out.println("Waktu pencarian: "+diff+"ms");

        if (solver.isFound()) {
            System.out.println("Banyak node dikunjungi: " + solver.getCounterVisited() + "\nSolusi - dengan panjang lintasan: "+solver.getSolution().length+":");
            solver.getSolution().displayPath();
            solver.getSolution().displayDirection();
        } else {
            System.out.println("No Solution found, counter: " + solver.getCounterVisited());
        }
    }
}
