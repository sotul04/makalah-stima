package src;

public class Demo {
    public static void main(String[] args) {
        Puzzle problem = new Puzzle();
        
        // Silahkan ganti nilai pada array probs di bawah ini untuk menentukan jawaban yang lain:
        int[] probs = new int[]{1,7,5,4,2,6,8,3,9};
        
        problem.setFormation(probs);
        problem.setEmptyPoint(2, 2);
        
        // Algoritma Branch and Bound
        BnBSolver solverBnB = new BnBSolver(problem);
        long start = System.currentTimeMillis();
        solverBnB.search();
        long end = System.currentTimeMillis();
        long diff = end - start;
        System.out.println("\nAlgoritma Branch and Bound");
        System.out.println("Waktu pencarian: "+diff+"ms");
        
        if (solverBnB.isFound()) {
            System.out.println("Banyak node dikunjungi: " + solverBnB.getCounterVisited() + "\nSolusi - dengan panjang lintasan "+solverBnB.getSolution().length+":");
            // solverBnB.getSolution().displayPath();
            solverBnB.getSolution().displayDirection();
        } else {
            System.out.println("No Solution found, counter: " + solverBnB.getCounterVisited());
        }

        //Algoritma IDS
        IDSolver solverIDS = new IDSolver(problem);
        
        start = System.currentTimeMillis();
        
        solverIDS.search();
        
        end = System.currentTimeMillis();
        diff = end - start;
        System.out.println("\nAlgoritma IDS");
        System.out.println("Waktu pencarian: "+diff+"ms");

        if (solverIDS.isFound()) {
            System.out.println("Banyak node dikunjungi: " + solverIDS.getCounterVisited() + "\nSolusi - dengan panjang lintasan "+solverIDS.getSolution().length+":");
            // solverIDS.getSolution().displayPath();
            solverIDS.getSolution().displayDirection();
        } else {
            System.out.println("No Solution found, counter: " + solverIDS.getCounterVisited());
        }
    }
}
