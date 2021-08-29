import java.util.*;

public class bt {

    public static class Node {
        int data;
        Node left;
        Node right;

        Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }
    
    public static int size(Node node) {
        if(node == null) return 0;
        
        return size(node.left) + size(node.right) + 1;
      }
    
      public static int sum(Node node) {
        return node != null ? sum(node.left) + sum(node.right) + node.data : 0;
      }
    
      public static int max(Node node) {
        return node != null ? Math.max(node.data, Math.max(max(node.left), max(node.right))) : 0;
      }
    
      public static int height(Node node) {
        return node != null ? Math.max(height(node.left), height(node.right)) + 1 : -1;
      }

      public static void levelOrder(Node node) {
        Queue<Node> q = new ArrayDeque<>();
        q.add(node);
        
        while(q.size() != 0) {
            int rs = q.size();
            while(rs-->0) {
                Node rm = q.remove();
                System.out.print(rm.data + " ");
                
                if(rm.left != null) q.add(rm.left);
                if(rm.right != null) q.add(rm.right);
            }
            
            System.out.println();
        }
      }
}
