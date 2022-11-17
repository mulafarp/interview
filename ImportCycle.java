import java.util.*;

class Node {
    String name;
    List<Node> imports;
    public Node(String name) {
        this.name = name;
        this.imports = new ArrayList<>();
    }

    public void addImports(Node node) {
        this.imports.add(node);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

public class ImportCycle {
    static Set<Node> classes;
    static Set<String> visited;
    public static void main(String[] args) {
        Node a = new Node("A");
        Node b = new Node("B");
        Node c = new Node("C");
        Node d = new Node("D");
        Node e = new Node("E");
        Node f = new Node("F");

        classes = new HashSet<>();
        classes.add(a);
        classes.add(b);
        classes.add(c);
        classes.add(d);
        classes.add(e);
        classes.add(f);
        addConnectedImports(a, b);
        addConnectedImports(b, c);
        addConnectedImports(b, d);
        addConnectedImports(b, e);
        addConnectedImports(d, f);
        addConnectedImports(d, b);
        boolean detectedCycle = false;
        if(classes.isEmpty()) {
            detectedCycle = true;
        } else {
            for(Node cla: classes) {
                if (detectCycle(cla)) {
                    detectedCycle = true;
                    break;
                }
            }
        }

        if(detectedCycle) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

    }

    public static void addConnectedImports(Node a, Node b) {
        classes.remove(b);
        a.imports.add(b);
    }

    public static boolean detectCycle (Node a) {
        visited = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(a);
        Node curr = null;
        while (!queue.isEmpty()) {
            curr = queue.poll();
            if(visited.contains(a.name)) {
                return true;
            }
            visited.add(a.name);
            if(curr.imports != null) {
                for(Node n: curr.imports) {
                    queue.add(n);
                }
            }
        }

        return false;
    }
}
