public class Point {
    
    public int x, y;

    public Point() {
        x = 0;
        y = 0;
    }

    public Point(int x_, int y_) {
        x = x_;
        y = y_;
    }

    public Point(Point other) {
        x = other.x;
        y = other.y;
    }

    /**
     * Mengubah nilai point menjadi indeks array 1 dimensi
     */
    public int dorm() {
        return x*3+y;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj ||
            (obj instanceof Point p && p.x == x && p.y == y);
    }
}
