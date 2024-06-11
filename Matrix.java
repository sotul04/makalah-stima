import java.util.Arrays;

public class Matrix {
    protected int[] formation = new int[9];
    
    public Matrix() {
    }

    public Matrix(Matrix other) {
        formation = Arrays.copyOf(other.formation, 9);
    }

    public void setFormation(int[] newform) {
        formation = Arrays.copyOf(newform, newform.length);
    }

    public int get(int x, int y) {
        return get(x * 3 + y);
    }

    public int get(int i) {
        return formation[i];
    }

    public void set(int x, int y, int val) {
        set(x * 3 + y, val);
    }

    public void set(int idx, int val) {
        formation[idx] = val;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Matrix m) {
            return Arrays.equals(formation, m.formation);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(formation);
    }
}
