public class bst {
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
    
    //O(logn)
    //add node in a bst
    public static Node add(Node node, int data) {
        if(node == null) return new Node(data, null, null);
        
        if(node.data > data)
            node.left = add(node.left, data);
        
        else if(node.data < data)
            node.right = add(node.right, data);
            
        
        return node;
      }

    public static int maximum(Node node) {
        return node != null ? Math.max(maximum(node.right), node.data) : -(int)1e9;
    }
    

    //remove node in a bst O(logn)
    public static Node remove(Node node, int data) {
      if(node == null) return null;
      if(node.data > data) {
          node.left = remove(node.left, data);
      } else if(node.data < data) {
          node.right = remove(node.right, data);
      } else {
          if(node.left != null && node.right != null) {
              int lmax = maximum(node.left);
              node.data = lmax;
              node.left = remove(node.left, lmax);
              return node;
          } else if(node.left != null) {
              node.data = node.left.data;
              node.left = null;
              return node;
          } else if(node.right != null) {
              node.data = node.right.data;
              node.right = null;
              return node;
          } else {
              return null;
          }
      }
      return node;
    }  


    //lca in a bst
    public static int lca(Node node, int d1, int d2) {
        if(node == null) return 0;
        
        if(node.data < d1 && node.data < d2) {
            return lca(node.right, d1, d2);
        } else if(node.data > d1 && node.data > d2){
            return lca(node.left, d1, d2);
        } else{
            return node.data;
        }
      }


    //print in range (need to print inorder)  
    public static void pir(Node node, int d1, int d2) {
        if(node == null) return;
        
        pir(node.left, d1, d2);
        
        if(d1 <= node.data && d2 >= node.data) {
            System.out.println(node.data);
        }
        
        pir(node.right, d1, d2);
      }  
}
