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