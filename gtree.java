import java.util.*;

public class gtree {

    public static class Node {
        int data;
        ArrayList<Node> children;

        Node(int data) {
            this.data = data;
            children = new ArrayList<>();
        }
    }

    public static Node construct(int[] arr) {
        Stack<Node> st = new Stack<>();
        
        Node root = new Node(arr[0]);
        st.push(root);

        for(int i = 1; i < arr.length; i++) {
            if(arr[i] != -1) {
                Node nn = new Node(arr[i]);
                Node top = st.peek();
                top.children.add(nn);
                st.push(nn);
            } else {
                st.pop();
            }
        }

        return root;
    }

    public static void printTree(Node root) {
        if(root != null) {
            System.out.print(root.data + ": ");
        }

        for(Node child : root.children) {
            System.out.print(child.data + ", ");
        }

        System.out.println();

        for(Node child : root.children) {
            printTree(child);
        }
    }
    public static void main(String[] args) {
        int[] arr = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        Node root = construct(arr);
        printTree(root);
    }
}