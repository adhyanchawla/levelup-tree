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

  static int dia = 0;
  public static int diameter1(Node node) {
    int maxht = -1;
    int smaxht = -1;
    if(node == null) return -1;
    
    int cht = diameter1(node.left);
    if(cht > maxht) {
        smaxht = maxht;
        maxht = cht;
    } else if(cht > smaxht) {
        smaxht = cht;
    }
    
    int cht1 = diameter1(node.right);
    if(cht1 > maxht) {
        smaxht = maxht;
        maxht = cht1;
    } else if(cht1 > smaxht) {
        smaxht = cht1;
    }
    
    dia = Math.max(dia, maxht + smaxht + 2);
    
    return maxht + 1;
  }
  
  
  public static class DiaPair {
    int dia;
    int ht;
    
    DiaPair(int dia, int ht) {
        this.dia = dia;
        this.ht = ht;
    }
}

//diameter of binary tree
public static DiaPair fun(Node node) {
    
    int maxht = -1;
    int smaxht = -1;
    if(node == null) {
        DiaPair mp = new DiaPair(0, -1);
        return mp;
    }
    
    DiaPair lp = fun(node.left);
    if(lp.ht > maxht) {
        smaxht = maxht;
        maxht = lp.ht;
    } else {
        smaxht = lp.ht;
    }
    
    DiaPair rp = fun(node.right);
    if(rp.ht > maxht) {
        smaxht = maxht;
        maxht = rp.ht;
    } else {
        smaxht = rp.ht;
    }
    
    DiaPair mp = new DiaPair(0, -1);
    mp.dia = Math.max(mp.dia, maxht + smaxht + 2);
    mp.ht = maxht + 1;
    return mp;
}


    //remove leaves
    public static Node removeLeaves(Node node){
      if(node == null) return null;
      
      if(node.left == null && node.right == null) {
          return null;
      }
      
      //to take the changes place, actually perform the changes
      node.left = removeLeaves(node.left);
      node.right = removeLeaves(node.right);
      
          
      return node;    
    }


    //transform back to original tree 
    public static Node transBackFromLeftClonedTree(Node node){
      if(node == null) return null;
      
      Node left = transBackFromLeftClonedTree(node.left.left);
      Node right = transBackFromLeftClonedTree(node.right);
      
      node.left = node.left.left;
      
      return node;
    }


    //print single child nodes
    public static void printSingleChildNodes(Node node){
      if(node == null) return;
      
      if((node.left == null && node.right != null)) {
          System.out.println(node.right.data);
          return;
      }
      
      if(node.left != null && node.right == null) {
          System.out.println(node.left.data);
          return;
      }
      
      printSingleChildNodes(node.left);
      printSingleChildNodes(node.right);
      
    }

    //create left clone of a tree
    public static Node createLeftCloneTree(Node node){
      if(node == null) return null;
      
      Node left = createLeftCloneTree(node.left);
      Node right = createLeftCloneTree(node.right);
      
      Node nn = new Node(node.data, null, null);
      Node lft = node.left;
      node.left = nn;
      nn.left = lft;
      
      return node;
    }

    //path from root to leaves sum within a range
    public static void pathToLeafFromRoot(Node node, String path, int sum, int lo, int hi){
    
      if(node == null) return;
      
      if(node.left == null && node.right == null) {
          sum += node.data;
          path += node.data + " ";
          if(sum >= lo && sum <= hi)
          System.out.println(path); 
          //return;
      }
      
      pathToLeafFromRoot(node.left, path + node.data + " ", sum + node.data, lo, hi);
      pathToLeafFromRoot(node.right, path + node.data + " ", sum + node.data, lo, hi);
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
