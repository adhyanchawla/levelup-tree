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

    public static class Pair {
        Node node;
        int state; 
        Pair(Node node, int state) {
          this.node = node;
          this.state = state;
        }
    }


    //print k levels down
    public static void printKLevelsDown(Node node, int k){
      if(node == null) return;
      
      if(k == 0) {
          if(node != null) {
              System.out.println(node.data);
          }
          return;
      }
      
      printKLevelsDown(node.left, k - 1);
      printKLevelsDown(node.right, k - 1);
    }


    //node to root path and find
    public static boolean find(Node node, int data){
      if(node == null) return false;
      
      if(node.data == data) return true;
      
      boolean ans = false;
      
      ans = ans || find(node.left, data);
      ans = ans || find(node.right, data);
      
      return ans;
    }
  
    public static ArrayList<Integer> nodeToRootPath(Node node, int data){
      if(node == null) return new ArrayList<>();
      
      if(node.data == data) {
          ArrayList<Integer> base = new ArrayList<>();
          base.add(data);
          return base;
      }
      
      ArrayList<Integer> myAns = new ArrayList<>();
      
      myAns = nodeToRootPath(node.left, data);
          if(myAns.size() > 0) {
              myAns.add(node.data);
              return myAns;
          }
      
      myAns = nodeToRootPath(node.right, data);
          if(myAns.size() > 0) {
              myAns.add(node.data);
              return myAns;
          }
      
      return new ArrayList<>();
    }


    //traversals using stack ds
    public static void iterativePrePostInTraversal(Node node) {
      Stack<Pair> st = new Stack<>();
      st.push(new Pair(node, 0));
      String pre = "";
      String in = "";
      String post = "";
      
      while(st.size() != 0) {
          Pair top = st.peek();
          if(top.state == 0) {
              pre += top.node.data + " ";
              top.state++;
              if(top.node.left != null) st.push(new Pair(top.node.left, 0));
          } else if(top.state == 1) {
              in += top.node.data + " ";
              top.state++;
              if(top.node.right != null) st.push(new Pair(top.node.right, 0));
          } else if(top.state == 2) {
              post += top.node.data + " ";
              st.pop();
          }
      }
      
          System.out.println(pre);
          System.out.println(in);
          System.out.println(post);
    }
    

    //basic functions
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
