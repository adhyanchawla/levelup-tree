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

    public static int size(Node root) {
        int sz = 1;
        for(Node child : root.children) {
            sz += size(child);
        }
        return sz;
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

    public static int max(Node root) {
    
        int mx = -(int)1e9;
        
        for(Node child : root.children) {
            mx = Math.max(mx, max(child));
        }
        
        return Math.max(mx, root.data);
      }


    //height in terms of edges 
    public static int height(Node root) {
        int ht = -1;
        
        for(Node child : root.children) {
            int cht = height(child);
            ht = Math.max(cht, ht);
        }
        
        return ht + 1;
      } 
    
    //height in terms of nodes  
    public static int height01(Node root) {
        int ht = 0;
        
        for(Node child : root.children) {
            int cht = height01(child);
            ht = Math.max(cht, ht);
        }
        
        return ht + 1;
      }   


    //level order traversal using queue method
    public static void levelOrder(Node node){
        Queue<Node> q = new ArrayDeque<>();
        q.add(node);
        
        while(q.size() != 0) {
            
            int sz = q.size(); 
            while(sz-->0) {
                //remove
                Node rm = q.remove();
            
                //work
                System.out.print(rm.data + " ");
            
                //add children
                for(Node child : rm.children) {
                    q.add(child);
                }   
            }
        }
        
        System.out.println(".");
    }
    
    public static void levelOrder01(Node node) {

    }


    //level order linewise using queue
    public static void levelOrderLinewise(Node node){
        Queue<Node> q = new ArrayDeque<>();
        q.add(node);
        
        while(q.size() != 0) {
            int sz = q.size();
            while(sz-->0) {
                Node rm = q.remove();
                
                System.out.print(rm.data + " ");
                
                for(Node child : rm.children) {
                    q.add(child);
                }
            }
            System.out.println();
        }
    }

    //level order linewise zig zag using two stacks
    public static void levelOrderLinewiseZZ(Node node){
        Stack<Node> s1 = new Stack<>();
        Stack<Node> s2 = new Stack<>();
        
        s1.push(node);
        
        int lvl = 0;
        while(s1.size() != 0){
            
            if(lvl % 2 == 0) {
                while(s1.size() != 0) {
                    Node rm = s1.pop();
                    
                    System.out.print(rm.data + " ");
                    
                    for(Node child : rm.children) {
                        s2.push(child);
                    }
                }
                lvl++;
            } else {
                while(s1.size() != 0) {
                    Node rm = s1.pop();
                    
                    System.out.print(rm.data + " ");
                    
                    for(int i = rm.children.size() - 1; i >= 0; i--) {
                        s2.push(rm.children.get(i));
                    }
                }
                
                lvl++;
            }
            
            if(s2.size() > 0) {
                System.out.println();
                s1 = s2;
                s2 = new Stack<>();
            }
        }
    }


    //mirror of generic tree
    public static void swap(ArrayList<Node> children, int si, int ei) {
      
        while(si <= ei) {
            Node n1 = children.get(si);
            Node n2 = children.get(ei);
            
            children.set(si, n2);
            children.set(ei, n1);
            
            si++;
            ei--;
        }
    }
    
  
    public static void mirror(Node node){
          
          swap(node.children, 0, node.children.size() - 1);
          
          for(Node child : node.children) {
              mirror(child);
          }
    }

    public static Node getLastChild(Node node) {
        for(int i = node.children.size() - 1; i >= 0; i--) {
            if(node.children.size() != 0)
            node = node.children.get(i);
        }
        
        return node;
    }
    
    //linearize in O(N*N)
    public static void linearize(Node node){
      for(int i = node.children.size() - 2; i >= 0; i--) {
          Node slc = getLastChild(node.children.get(i));
          Node lc = node.children.get(i + 1);
          node.children.remove(lc);
          slc.children.add(lc);
      }
      
      for(Node child : node.children) {
          linearize(child);
      }
    }

    public static Node linearize01(Node node) {
        if(node.children.size() == 0) return node;
    
        Node otail = linearize01(node.children.get(node.children.size() - 1));
        
        for(int i = node.children.size() - 2; i >= 0; i--) {
            Node slkitail = linearize01(node.children.get(i));
            slkitail.children.add(node.children.get(i + 1));
            node.children.remove(i + 1);
        }
        
        return otail;
    }


    //find 
    public static boolean find(Node node, int data) {
        //apne mei check krega  
        if(node.data == data) {
            return true;
        }
        
        boolean ans = false;
        for(Node child : node.children) {
            //child se puchega
            ans = find(child, data);
            //agar mila, toh true
            if(ans) return true;
        }
        
        //mere se ni match kiya, mere child se ni match kiya then false
        return false;
      }


      //node to root path
      public static ArrayList<Integer> nodeToRootPath(Node node, int data){
        if(node.data == data) {
            ArrayList<Integer> base = new ArrayList<>();
            base.add(node.data);
            return base;
        }
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        for(Node child : node.children) {
            ans = nodeToRootPath(child, data);
            if(ans.size() > 0) {
                ans.add(node.data);
                return ans;
            }
        }
        
        return new ArrayList<>();
     }
     

     //lca of generic tree
     public static int lca(Node node, int d1, int d2) {
        ArrayList<Integer> ans1 = nodeToRootPath(node, d1);
        ArrayList<Integer> ans2 = nodeToRootPath(node, d2);
        
        int i1 = ans1.size() - 1;
        int i2 = ans2.size() - 1;
        
        while(i1 >= 0 && i2 >= 0 && ans1.get(i1) == ans2.get(i2)) {
            i1--;
            i2--;
        }
        
        return ans1.get(i1 + 1);
      } 

    public static int floorNum(Node node, int ub) {
        int maxRes = -(int)1e9;
        for(Node child : node.children) {
            int recAns = floorNum(child, ub);
            maxRes = Math.max(maxRes, recAns);
        }
        
        return node.data < ub ? Math.max(node.data, maxRes) : maxRes;
    }
    

    //kth largest in a tree
    public static int kthLargest(Node node, int k){
      int ub = (int)1e9;
      for(int i = 0; i < k; i++) {
          ub = floorNum(node, ub);
      }
      
      return ub;
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


    //find max sum subtree using pair class
    public static class Pair {
        int sum = 0;
        int mst = 0;
        Node mssn = null;
        Pair(int sum, int mst, Node mssn) {
            this.sum = sum;
            this.mst = mst;
            this.mssn = mssn;
        }
    }
    
    public static Pair ans(Node node) {
        Pair myAns = new Pair(0, -(int)1e9, null);
        myAns.sum = node.data;
        
        for(Node child : node.children) {
            Pair cp = ans(child);
            
            myAns.sum += cp.sum;
            if(cp.mst > myAns.mst) {
                myAns.mst = cp.mst;
                myAns.mssn = cp.mssn;
            }
        }
        
        //myAns.sum += node.data;
        
        if(myAns.sum > myAns.mst) {
            myAns.mst = myAns.sum;
            myAns.mssn = node;
        }
        
        return myAns;
    }

    //max subtree sum
    //using static 
    public static int mst;
    public static Node mssn;
    public static int subTreeSum(Node node) {
        int sum = 0;

        for(Node child : node.children) {
            int chsts = subTreeSum(child);

            sum += chsts;
        }

        if(mst > sum) {
            mst = sum;
            mssn = node;
        }

        return sum;
    }

    public static int maxSubTreeSum(Node node) {
        mst = -(int)1e9;
        mssn = null;
        return subTreeSum(node);
    }
    public static void main(String[] args) {
        int[] arr = {10, 20, 50, -1, 60, -1, -1, 30, 70, -1, 80, 110, -1, 120, -1, -1, 90, -1, -1, 40, 100, -1, -1, -1};
        Node root = construct(arr);
        printTree(root);
    }
}