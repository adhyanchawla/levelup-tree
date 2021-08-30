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


    // k distance far away
    public static ArrayList<Node> nodeToRootPath1(Node node, int data){
      if(node == null) return new ArrayList<>();
      
      if(node.data == data) {
          ArrayList<Node> base = new ArrayList<>();
          base.add(node);
          return base;
      }
      
      ArrayList<Node> myAns = new ArrayList<>();
      
      myAns = nodeToRootPath1(node.left, data);
          if(myAns.size() > 0) {
              myAns.add(node);
              return myAns;
          }
      
      myAns = nodeToRootPath1(node.right, data);
          if(myAns.size() > 0) {
              myAns.add(node);
              return myAns;
          }
      
      return new ArrayList<>();
    }
    
      public static void printKLevelsDown(Node node, int k, Node blocker){
      if(node == null || node == blocker || k < 0) return;
      
      if(k == 0) {
          System.out.println(node.data);
          return;
      }
      
      printKLevelsDown(node.left, k - 1, blocker);
      printKLevelsDown(node.right, k - 1, blocker);
    }

    public static void printKNodesFar(Node node, int data, int k) {
      ArrayList<Node> ans = nodeToRootPath1(node, data);
      for(int i = 0; i < ans.size(); i++) {
          printKLevelsDown(ans.get(i), k - i, i != 0 ? ans.get(i - 1) : null);
      }
    }


    //in order to calculate the tilt of a binary tree we need left subtree sum and right subtree sum
    //tilt of a node will be absolute diff of ltilt and rtilt
    //tilt of a tree will be sum of all the tilts obtained
    //hum kuch aur nikaalre and return kuch aur kr rhe
    static int tilt = 0;
    public static int tilt(Node node){
      // write your code here to set the tilt data member
      if(node == null) return 0;
      int lst = 0; //left subtree sum
      int rst = 0; //right subtree sum
      lst += tilt(node.left); //sum of all lst
      rst += tilt(node.right); //sum of all rst
      
      tilt += Math.abs(lst - rst); //tilt of a tree = tilt of all the nodes
      
      return lst + rst + node.data; //we return subtree sum
    }

    public static class Pair {
        Node node;
        int state; 
        Pair(Node node, int state) {
          this.node = node;
          this.state = state;
        }
    }
  
    
  //tilt of binary tree using pair class   
  public static class TiltPair {
      int tilt;
      int st;
      
      TiltPair(int tilt, int st) {
          this.tilt = tilt;
          this.st = st;
      }
  }
  
  public static TiltPair fun1(Node node) {
      if(node == null) return new TiltPair(0, 0);
      
      TiltPair lp = fun1(node.left);
      TiltPair rp = fun1(node.right);
      
      TiltPair mp = new TiltPair(0, 0);
      
      mp.st = lp.st + rp.st + node.data; //poore tree ka subtree sum
                //mera tilt               //left ka tilt // right ka tilt
      mp.tilt = Math.abs(lp.st - rp.st) + lp.tilt + rp.tilt; //poore tree ka tilt
      
      return mp;
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
