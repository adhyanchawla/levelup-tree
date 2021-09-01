import java.util.*;

public class btlevelup {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }
    
    //#1
    //construct binary tree from inorder and preorder traversals
    public static TreeNode constructBT(int[] preorder, int pst, int pet, int[] inorder, int ist, int iet) {
        if(pst > pet || ist > iet) {
            return null;
        }
        
        TreeNode root = new TreeNode(preorder[pst]);
        
        int idx = ist;
        while(inorder[idx] != preorder[pst]) {
            idx++;
        }
        
        int colse = idx - ist; //count of left subtree elements
        
        root.left = constructBT(preorder, pst + 1, pst + colse, inorder, ist, idx - 1);
        root.right = constructBT(preorder, pst + colse + 1, pet, inorder, idx + 1, iet);
            
        return root;    
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        return constructBT(preorder,0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    //#2
    //create a binary tree using inorder and postorder arrays
    public static TreeNode buildTree01(int[] inorder, int ist, int iet, int[] postorder, int pst, int pet) {
        if(ist > iet) return null;
        
        TreeNode root = new TreeNode(postorder[pet]);
        
        int idx = ist;
        while(inorder[idx] != postorder[pet]) idx++;
        
        int colse = idx - ist;
        
        root.left = buildTree01(inorder, ist, idx - 1, postorder, pst, pst + colse - 1);
        root.right = buildTree01(inorder, idx + 1, iet, postorder, pst + colse, pet - 1);
        
        return root;
    }

    public static TreeNode buildTree01(int[] inorder, int[] postorder) {
        return buildTree01(inorder, 0, inorder.length-1, postorder, 0, postorder.length-1);
    }


    //construct tree from post and pre
    public TreeNode buildTree2(int[] preorder, int prs, int pre, int[] postorder, int pos, int poe) {
        if(pre < prs) return null;
        
        //check this basecase to avoid prs + 1 going out of bonds as we cannot inc it more than poe
        if(prs == pre) return new TreeNode(preorder[prs]);
        
        TreeNode root = new TreeNode(preorder[prs]);
        
        int idx = pos;
        while(prs + 1 < preorder.length && preorder[prs + 1] != postorder[idx]) idx++;
        
        int colse = idx - pos + 1;
        
        root.left = buildTree2(preorder, prs + 1, prs + colse, postorder, pos, pos + colse - 1);
        root.right = buildTree2(preorder, prs + colse + 1, pre, postorder, pos + colse, poe - 1);
        
        return root;
    } 
    
    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        return buildTree2(preorder, 0, preorder.length - 1, postorder, 0, postorder.length - 1);
    }



    //interview pov se imp
    //construct BT from inorder and levelorder
    public static TreeNode buildTree4(int inord[], int level[])
    {
        map = new HashMap<>();
        
        for(int i = 0; i < inord.length; i++) {
            map.put(inord[i], i);
        }
        
        return buildTree3(inord, 0, inord.length - 1, level);
        //your code here
    }
    
    //O(N)
    //extract the elements of small left level order and small right level order
    public static void extract(int idx, int[] level, int[] llo, int[] rlo) {
        
        int j = 0, k = 0;
        for(int i = 1; i < level.length; i++) {
            int in = map.get(level[i]); //using map extract index of elements of level order
            
            //by comparing the index values place the elements in small level order arrays
            if(in < idx) {
                llo[j++] = level[i];
            } else if(in > idx) {
                rlo[k++] = level[i];
            }
        }
    }
    
    public static HashMap<Integer, Integer> map;
    //O(nh) h in worst case h = n, else O(nlogn), space: O(n)
    public static TreeNode buildTree3(int[] inord, int is, int ie, int[] level) {
        
        if(is > ie) return null;
        
        
        TreeNode node = new TreeNode(level[0]);
        
        int idx = map.get(level[0]); //idx obtained from inorder idx of hashmap
        
        int colse = idx - is; //count of left subtree elements
        int corse = ie - idx; //count of right subtree elements
        
        int []llo = new int[colse]; //make left level order array
        int []rlo = new int[corse]; //make right level order array
        
        extract(idx, level, llo, rlo);
        
        node.left = buildTree3(inord, is, idx - 1, llo);
        node.right = buildTree3(inord, idx + 1, ie, rlo);
        
        return node;
    }


    //construct BST from sorted array
    public static TreeNode buildTree(int[] nums, int si, int ei) {
        if(si > ei) return null;
        
        int mid = (si + ei) / 2;
        
        TreeNode root = new TreeNode(nums[mid]);
        
        root.left = buildTree(nums, si, mid - 1);
        root.right = buildTree(nums, mid + 1, ei);
        
        return root;
    }
    
    public static TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length - 1);
    }


    //method 1
    //using static keyword
    //construct BST from preorder traversal
    //lr : left range rr : right range
    //we have used BST property
    public static TreeNode buildTree6(int[] preorder, int lr, int rr) {
        if(idx >= preorder.length || preorder[idx] < lr || preorder[idx] > rr) return null;
        else {
            TreeNode root = new TreeNode(preorder[idx++]);
            root.left = buildTree6(preorder, lr, root.val);
            root.right = buildTree6(preorder, root.val, rr);
            return root;
        }
        
    }
    
    static int idx; //statically created index to iterate over the preorder array
    public static TreeNode bstFromPreorder(int[] preorder) {
        idx = 0;
        return buildTree6(preorder, -(int)1e9, (int)1e9);
    }


    //method 2
    //without using static
    public static TreeNode buildTree7(int[] preorder, int lr, int rr, int[] in) {
        if(in[0] >= preorder.length || preorder[in[0]] < lr || preorder[in[0]] > rr) return null;
        else {
            TreeNode root = new TreeNode(preorder[in[0]++]);
            root.left = buildTree7(preorder, lr, root.val, in);
            root.right = buildTree7(preorder, root.val, rr, in);
            return root;
        }
    }
    
    public static TreeNode bstFromPreorder01(int[] preorder) {
        int idx = 0;
        int[] in = new int[1];
        in[0] = idx;
        return buildTree7(preorder,-(int)1e9, (int)1e9, in);
    }


    //construct BST from postorder traversal
    public static TreeNode buildTree8(int[] post, int lr, int rr) {
        if(i < 0 || post[i] < lr || post[i] > rr){
            return null;
        } else {
            TreeNode root = new TreeNode(post[i--]);
            root.right = buildTree8(post, root.val, rr); //first right call
            root.left = buildTree8(post, lr, root.val);
            return root;
        }
    }
    static int i;
    public static TreeNode constructTree(int post[],int n)
    {
        i = post.length - 1;
        return buildTree8(post, -(int)1e9, (int)1e9);
        //Add your code here.
    }


    //construct bst from level order
    //important: not recursive, used queue
    public static class Pair {
        TreeNode par;
        int lr;
        int rr;
        
        Pair(TreeNode par, int lr, int rr) {
            this.par = par;
            this.lr = lr;
            this.rr = rr;
        }
    }

    public static TreeNode constructBSTFromLevelOrder(int[] arr) {
        
        Queue<Pair> q = new ArrayDeque<>();
        TreeNode root = null;
        q.add(new Pair(root, -(int)1e9, (int)1e9));
        
        int idx = 0;
        while(q.size() > 0 && idx < arr.length) {
            Pair rm = q.remove();
            
            if(rm.lr > arr[idx] || rm.rr < arr[idx]) continue;
            
            TreeNode nn = new TreeNode(arr[idx]);
            idx++;
            
            if(rm.par == null) {
                root = nn;
            } else {
                if(rm.par.val > nn.val) {
                    rm.par.left = nn;
                } else if(rm.par.val < nn.val) {
                    rm.par.right = nn;
                }
            }
            
            q.add(new Pair(nn, rm.lr, nn.val));
            q.add(new Pair(nn, nn.val, rm.rr));
        }
        
        return root;
    }


    //serialize and deserialise a binary tree
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root == null) {
            return "n";
        }
        
        String ls = serialize(root.left);
        String rs = serialize(root.right);
        
        return root.val + " " + ls + " " + rs;
    }
    
    public TreeNode helper(String[] tokens) {
        if(tokens[idx1].equals("n")) {
            idx1++;
            return null;
        }
        
        TreeNode root = new TreeNode(Integer.parseInt(tokens[idx1++]));
        root.left = helper(tokens);
        root.right = helper(tokens);
        
        return root;
    }

    // Decodes your encoded data to tree.
    static int idx1;
    public TreeNode deserialize(String data) {
        String[] tokens = data.split(" ");
        idx1 = 0;
        return helper(tokens);
    }


    //left view of binary tree
    public static ArrayList<Integer> leftView(TreeNode root) {
        if(root == null) return new ArrayList<>();
        
        Queue<TreeNode> q = new ArrayDeque<>();
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        q.add(root);
        
        while(q.size() != 0) {
            int sz = q.size();
            ans.add(q.peek().val);
            while(sz-->0) {
                TreeNode rm = q.remove();
                
                if(rm.left != null) q.add(rm.left);
                if(rm.right != null) q.add(rm.right);
            }
        }
        return ans;
    }

    //right view of binary tree
    public static List<Integer> rightSideView(TreeNode root) {
        if(root == null) return new ArrayList<>();
        
        List<Integer> ans = new ArrayList<>();
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        
        while(q.size() != 0) {
            int sz = q.size();
            
            while(sz-->0) {
                
                if(sz == 0) {
                    ans.add(q.peek().val);
                }
                
                TreeNode rm = q.remove();
                
                if(rm.left != null) q.add(rm.left);
                if(rm.right != null) q.add(rm.right);
            }
            
            
        }
        
        return ans;
    }
}
