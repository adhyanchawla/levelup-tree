public class avl {
    
    public static class Node {
        int data;
        Node left;
        Node right;
        int ht = 0;
        int bal = 0;

        Node(int data) {
            this.data = data;
        }

        Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    public static Node add(Node node, int data) {
        if(node == null) {
            return new Node(data);
        }

        if(node.data > data) {
            node.left = add(node.left, data);
        } else if(node.data < data) {
            node.right = add(node.right, data);
        }

        updateHtAndBal(node);
        node = check(node);

        return node;
    }

    public static int max(Node node) {
        if(node.right != null) {
            return max(node.right);
        }
        else {
            return node.data;
        }
    }

    public static Node remove(Node node,int data) {
        if(node == null) {
            return null;
        }

        if(node.data < data) {
            node.right = remove(node.right,data);
        }
        else if(node.data > data) {
            node.left = remove(node.left,data);
        }
        else {
            //no child
            if(node.left == null && node.right == null) {
                return null;
            }
            //only right child
            else if(node.left == null) {
                return node.right;
            }
            //only left child
            else if(node.right == null) {
                return node.left;
            }
            //both child
            else {
                int lmax = max(node.left);
                node.data = lmax;
                node.left = remove(node.left,lmax);
            }

        }

        updateHtAndBal(node);

        //if bal is safe or not
        node = check(node);

        return node;
    }

    public static void updateHtAndBal(Node node) {
        int lht = (node.left != null) ? node.left.ht : -1;
        int rht = (node.right != null) ? node.right.ht : -1;

        int ht = Math.max(lht, rht) + 1;
        int bal = lht - rht;

        node.ht = ht;
        node.bal = bal;
    }

    public static Node leftRotation(Node node) {
        Node B = node.right;

        Node B_left = B.left;
        B.left = node;
        node.right = B_left;

        updateHtAndBal(node);
        updateHtAndBal(B);
        return B;
    }

    public static Node rightRotation(Node node) {
        Node B = node.left;

        Node B_right = B.right;
        B.right = node;
        node.left = B_right;

        updateHtAndBal(node);
        updateHtAndBal(B);
        return B;
    }

    public static Node setLL(Node node) {
        return rightRotation(node);
    }

    public static Node setRR(Node node) {
        return leftRotation(node);
    }

    public static Node check(Node node) {

        if(node.bal == 2) {
            //LL
            if(node.left.bal == 1) {
                return setLL(node);
            }//LR 
            else {
                node.left = setRR(node.left);
                return setLL(node);
            }
        } else if(node.bal == -2) {
            //RR
            if(node.right.bal == -1) {
                return setRR(node);
            }
            //RL
            else {
                node.right = setLL(node.right);
                return setRR(node);
            }
        }
        return node;
    }

    public static void display(Node node) {
        if (node == null)
          return;
    
        StringBuilder sb = new StringBuilder();
        sb.append((node.left != null ? node.left.data : "."));
        sb.append(" -> " + node.data + " <- ");
        sb.append((node.right != null ? node.right.data : "."));
    
        System.out.println(sb.toString());
    
        display(node.left);
        display(node.right);
      }

    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50, 60 , 70, 80, 90, 100};
        Node root = null;
        
        for(int i = 0; i < arr.length; i++) {
            root = add(root, arr[i]);
        }

        root = remove(root, 10);
        root = remove(root, 20);
        display(root);
    }
}
