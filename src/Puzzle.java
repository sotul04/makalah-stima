package src;

import java.util.HashMap;

public class Puzzle {
    
    /**
     * Matrix yang menyimpan kondisi puzzle saat ini
     */
    private Matrix formation;

    /**
     * Menyimpan nilai parent (Puzzle) dari mana langkah pemrosesan di lakukan
     */
    private Puzzle parent;

    /**
     * Posisi ubin kosong saat ini
     */
    private Point emptyPoint;

    private String direction = null;

    /*
     * Nilai g(n) dan h(n)
     * g(n) = length
     * h(n) = unmatch;
     */
    public int length, unmatch;

    public int priorityValue() {
        return length+unmatch;
    }

    private static int[] additional = new int[]{0,1,0,1,0,1,0,1,0};

    /**
     * Langkah-langkah yang dapat dilakukan
     * LEFT = Point(-1,0)
     * RIGHT = Point(1,0)
     * UP = Point(0,-1)
     * DOWN = Point(0,1)
     */
    // public static ArrayList<Point> MOVEMENTS;

    public static HashMap<String, Point> MOVEMENTS;

    static {
        MOVEMENTS = new HashMap<>();
        MOVEMENTS.put("LEFT", new Point(-1,0));
        MOVEMENTS.put("RIGHT", new Point(1,0));
        MOVEMENTS.put("UP", new Point(0,-1));
        MOVEMENTS.put("DOWN", new Point(0,1));
    }
    
    public Puzzle() {
        formation = new Matrix();
        parent = null;
        emptyPoint = new Point();
        length = 0;
        unmatch = countUnmatch();
    }

    public Puzzle(Puzzle other, Point move, String direction) {
        formation = new Matrix(other.formation);
        parent = other;
        emptyPoint = new Point(other.emptyPoint);
        this.direction = direction;
        move(move);
        length = other.length+1;
        unmatch = countUnmatch();
    }

    /**
     * @param i
     * @param j
     * Point(i,j) is valid
     * @return int
     */
    public int get(int x, int y) {
        return get(x * 3 + y);
    }

    public void setFormation(int[] newform) {
        formation.setFormation(newform);
    }

    public void setEmptyPoint(int x, int y) {
        emptyPoint = new Point(x, y);
    }

    public int get(int i) {
        return formation.get(i);
    }

    public void set(int x, int y, int val) {
        set(x * 3 + y, val);
    }

    public void set(int idx, int val) {
        formation.set(idx, val);
    }

    public Matrix getFormation() {
        return formation;
    }

    private boolean isIndexValid(Point p) {
        return p.x >= 0 && p.y >= 0 && p.x < 3 && p.y < 3;
    }

    public boolean isMoveValid(Point move) {
        Point currePoint = new Point(emptyPoint.x + move.x, emptyPoint.y + move.y);
        return isIndexValid(currePoint);
    }

    private int reachPoint() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = i+1; j < 9; j++) {
                if (get(j) < get(i)) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isGoalReachable() {
        return reachPoint() + additional[emptyPoint.dorm()] % 2 == 0;
    }

    public int countUnmatch() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (i+1 != get(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * prerequisite : isMoveValid(move)
     * @param move
     */
    public void move(Point move) {
        Point newPoint = new Point(emptyPoint.x + move.x, emptyPoint.y + move.y);
        set(emptyPoint.x, emptyPoint.y, get(newPoint.x, newPoint.y));
        set(newPoint.x, newPoint.y, 9);
        emptyPoint = newPoint;
    }

    public boolean isFinished() {
        for (int i = 0; i < 9; i++) {
            if (formation.get(i) != i + 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isVisited() {
        Puzzle par = parent;
        while (par != null) {
            if (this.equals(par)) return true;
            par = par.parent;
        }
        return false;
    }

    public void displayPath() {
        if (parent != null) {
            parent.displayPath();
        }
        if (direction != null) {
            System.out.println(direction);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(get(i,j)+"\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayDirection() {
        displayDirectionOnly();
        System.out.println();
    }

    private void displayDirectionOnly() {
        if (parent != null) {
            parent.displayDirectionOnly();
        }
        if (direction != null) {
            System.out.print(direction+" ");
        }
    }
}
