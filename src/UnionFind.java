import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

class UF {
    private int[] id;
    public int[] size;
    public String pair;

    public UF(int N) {
        id = new int[N];
        size = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    private int root(int i) {
        while (i != id[i]) i = id[i];
        return i;
    }

    public int find(int i) {
        return root(i);
    }


    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        if (i == j) return;
        if (size[i] < size[j]) {
            id[i] = j;
            size[j] += size[i];
        } else {
            id[j] = i;
            size[i] += size[j];
        }
        if (size[i] == 8 || size[j] == 8) {
            pair = Integer.toString(p) + " " + Integer.toString(q);
        }
    }


    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
}

public class UnionFind {
    public static void main(String[] args) {
        int N = StdIn.readInt();
        int count = 0;
        UF uf = new UF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
            }
        }
        for (int i = 0; i < N; i++) {
            StdOut.println(i + ": " + uf.size[i]);
        }
        StdOut.println(uf.pair);
    }
}
