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

    //width of a binary tree
    public static void helper(TreeNode root, int hl, int[] minmaxhl) {
        if(root == null) return;
        
        helper(root.left, hl - 1, minmaxhl);
        helper(root.right, hl + 1, minmaxhl);
        
        minmaxhl[0] = Math.min(minmaxhl[0], hl);
        minmaxhl[1] = Math.max(minmaxhl[1], hl);
            
    }

    public static int width(TreeNode root) {
      if(root.left == null && root.right == null) return 1;
        int[] minmaxhl = new int[2];
        helper(root, 0, minmaxhl);
        
        int minhl = minmaxhl[0];
        int maxhl = minmaxhl[1];
        
        return maxhl - minhl + 1;
    }


    //vertical order of binary tree using hashmap method 1 gfg solution
        public static class Pair3 {
        TreeNode node;
        int hl;
        int minhl;
        int maxhl;
        
        Pair3() {
            
        }
        
        Pair3(TreeNode node, int hl, int minhl, int maxhl) {
            this.node = node;
            this.hl = hl;
            this.minhl = minhl;
            this.maxhl = maxhl;
        }
    }

    public static ArrayList<ArrayList<Integer>> verticalOrderTraversal1(TreeNode node) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        
        int minhl = (int)1e9;
        int maxhl = -(int)1e9;
        
        Queue<Pair3> q = new ArrayDeque<>();
        q.add(new Pair3(node, 0, minhl, maxhl));
        while(q.size() != 0) {
            Pair3 rm = q.remove();
            maxhl = Math.max(rm.hl, maxhl);
            minhl = Math.min(rm.hl, minhl);
            
            if(!map.containsKey(rm.hl)) {
                ArrayList<Integer> arr = new ArrayList<>();
                arr.add(rm.node.val);
                map.put(rm.hl, arr);   
            } else {
                ArrayList<Integer> arr = map.get(rm.hl);
                arr.add(rm.node.val);
                map.put(rm.hl, arr);
            }
            
            if(rm.node.left != null) {
                q.add(new Pair3(rm.node.left, rm.hl - 1, minhl, maxhl));
            }
            
            if(rm.node.right != null) {
                q.add(new Pair3(rm.node.right, rm.hl + 1, minhl, maxhl));
            }
        }
        
        
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        
        int count = minhl;
        while(count <= maxhl) {
            if(map.containsKey(count)) {
                ArrayList<Integer> list = map.get(count);
                ans.add(list);
            }
            count++;
        }
        return ans;

    }


    //method 2 without hashmap
    //vertical order of binary tree
    public static class Pair4 {
        TreeNode node;
        int hl;
        
        Pair4(TreeNode node, int hl) {
            this.node = node;
            this.hl = hl;
        }
    }


    public static void helper2(TreeNode root, int hl, int[] minmaxhl) {
        if(root == null) return;
        
        minmaxhl[0] = Math.min(minmaxhl[0], hl);
        minmaxhl[1] = Math.max(minmaxhl[1], hl);
        
        helper2(root.left, hl - 1, minmaxhl);
        helper2(root.right, hl + 1, minmaxhl);
        
    }
    
    public static int widthOfTree(TreeNode root, int[] minmaxhl) {
        helper2(root, 0, minmaxhl);
        return minmaxhl[1] - minmaxhl[0] + 1;
    }

    public static ArrayList<ArrayList<Integer>> verticalOrderTraversal(TreeNode root) {
        
        int[] minmaxhl = new int[2];
        int w = widthOfTree(root, minmaxhl);
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < w; i++) {
            ans.add(new ArrayList<>());
        }
        
        Queue<Pair4> q = new ArrayDeque<>();
        
        int blvl = -minmaxhl[0];
        q.add(new Pair4(root, blvl));
        
        while(q.size() != 0){
            
            Pair4 rm = q.remove();
            
            ans.get(rm.hl).add(rm.node.val);
            
            if(rm.node.left != null) {
                q.add(new Pair4(rm.node.left, rm.hl - 1));
            }
            
            if(rm.node.right != null) {
                q.add(new Pair4(rm.node.right, rm.hl + 1));
            }
        }
        
        return ans;
    }

    //vertical order of tree leetcode version using Comparable compareTo
    public class Pair11 implements Comparable<Pair11> {
        
        TreeNode node;
        int x;
        int y;
        
        public Pair11(TreeNode node, int x, int y) {
            this.node = node;
            this.x = x;
            this.y = y;
        }
        
        public int compareTo(Pair11 o) {
            if(this.y != o.y) return this.y - o.y;
            else if(this.x != o.x) return this.x - o.x;
            else return this.node.val - o.node.val;
        }
        
    }
    
    public void width(TreeNode root, int hl, int[] minmaxhl) {
        if(root == null) return;
        
        minmaxhl[0] = Math.min(minmaxhl[0], hl);
        minmaxhl[1] = Math.max(minmaxhl[1], hl);
        
        width(root.left, hl - 1, minmaxhl);
        width(root.right, hl + 1, minmaxhl);
    }
    
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        
        PriorityQueue<Pair11> pq = new PriorityQueue<>();
        List<List<Integer>> ans = new ArrayList<>();
        
        int[] minmaxhl = new int[2];
        width(root, 0, minmaxhl);
        
        int w = minmaxhl[1] - minmaxhl[0] + 1;
        
        for(int i = 0; i < w; i++) {
            ans.add(new ArrayList<>());
        }
        
        pq.add(new Pair11(root, -minmaxhl[0], 0));
        
        while(pq.size() != 0) {
            //List<Integer> ans = new ArrayList<>();
            Pair11 rm = pq.remove();
            
            ans.get(rm.x).add(rm.node.val);
            
            if(rm.node.left != null) pq.add(new Pair11(rm.node.left, rm.x - 1, rm.y + 1));
            if(rm.node.right != null) pq.add(new Pair11(rm.node.right, rm.x + 1, rm.y + 1));
        }
        
        return ans;
        
    }

    //top view of binary tree
    //
    public static class Pair5 {
        TreeNode node;
        int hl;
        
        Pair5(TreeNode node, int hl) {
            this.node = node;
            this.hl = hl;
        }
    }
    
    public static void width1(TreeNode node, int hl, int[] maxminhl) {
        if(node == null) return;
        
        width1(node.left, hl - 1, maxminhl);
        width1(node.right, hl + 1, maxminhl);
        
        maxminhl[0] = Math.min(maxminhl[0], hl);
        maxminhl[1] = Math.max(maxminhl[1], hl);
    }
    
    
    public static ArrayList<Integer> TopView(TreeNode root)
    {
        int[] maxminhl = new int[2];
        width1(root, 0, maxminhl);
        
        int w = maxminhl[1] - maxminhl[0] + 1;
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
    
        for(int i = 0; i < w; i++) {
            ans.add(new ArrayList<>());
        }
        
        Queue<Pair5> q = new ArrayDeque<>();
        int blvl = -maxminhl[0];
        
        q.add(new Pair5(root, blvl));
        
        while(q.size() != 0) {
            
            Pair5 rm = q.remove();
            
            int hl = rm.hl;
            
            ans.get(hl).add(rm.node.val);
            
            if(rm.node.left != null) q.add(new Pair5(rm.node.left, hl - 1));
            if(rm.node.right != null) q.add(new Pair5(rm.node.right, hl + 1));
            
        }
        
        ArrayList<Integer> res = new ArrayList<>();
        
        for(ArrayList<Integer> list : ans) {
            res.add(list.get(0));
        }
        
        return res;
    }

    //bottom view of binary tree
    public static class Pair7 {
        TreeNode node;
        int hl;
        
        Pair7(TreeNode node, int hl) {
            this.node = node;
            this.hl = hl;
        }
    }
    
    public static void width4(TreeNode root, int hl, int[] minmaxhl) {
        if(root == null) return;
        
        minmaxhl[0] = Math.min(minmaxhl[0], hl);
        minmaxhl[1] = Math.max(minmaxhl[1], hl);
        width4(root.left, hl - 1, minmaxhl);
        width4(root.right, hl + 1, minmaxhl);
    }
    
    //Function to return a list containing the bottom view of the given tree.
    public static ArrayList <Integer> BottomView(TreeNode root)
    {
        int[] minmaxhl = new int[2];
        
        width4(root, 0, minmaxhl);
        int w = minmaxhl[1] - minmaxhl[0] + 1;
        
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        
        for(int i = 0; i < w; i++) {
            ans.add(new ArrayList<>());
        }
        
        Queue<Pair7> q = new ArrayDeque<>();
        q.add(new Pair7(root, -minmaxhl[0]));
        // Code here
        
        while(q.size() != 0) {
            Pair7 rm = q.remove();
            
            ans.get(rm.hl).add(rm.node.val);
            
            if(rm.node.left != null) q.add(new Pair7(rm.node.left, rm.hl - 1));
            if(rm.node.right != null) q.add(new Pair7(rm.node.right, rm.hl + 1));
        }
        
        ArrayList<Integer> res = new ArrayList<>();
        
        for(ArrayList<Integer> list : ans) {
            res.add(list.get(list.size() - 1));
        }
        
        return res;
    }

    //diagonal traversal of binary tree
    public static ArrayList<ArrayList<Integer>> diagonalOrder(TreeNode root) {
        
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        
        while(q.size() != 0) {
            int sz = q.size();
            ArrayList<Integer> list = new ArrayList<>();
            while(sz-->0) {
                TreeNode rm = q.remove();
                while(rm != null) {
                    if(rm.left != null) {
                        q.add(rm.left);
                    }
                    
                    list.add(rm.val);
                    rm = rm.right;
                }
            }
            ans.add(list);
        }
        
        return ans;
    }

    //diagonal traversal anticlockwise
    public static ArrayList<ArrayList<Integer>> diagonalOrderAnti(TreeNode root) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        
        while(q.size() != 0) {
            int sz = q.size();
            ArrayList<Integer> diag = new ArrayList<>();
            
            while(sz-->0) {
                TreeNode rm = q.remove();
                while(rm != null) {
                    
                    if(rm.right != null){
                        q.add(rm.right);
                    }
                    
                    diag.add(rm.val);
                    rm = rm.left;
                }
            }
            ans.add(diag);
        }
        
        return ans;
    }

    //vertical order sum
    public static class Pair8{
        TreeNode node;
        int hl;
        
        Pair8(TreeNode node, int hl) {
            this.node = node;
            this.hl = hl;
        }
    }
    
    public static void width3(TreeNode root, int hl, int[] minmaxhl) {
        if(root == null) return;
        
        minmaxhl[0] = Math.min(minmaxhl[0], hl);
        minmaxhl[1] =Math.max(minmaxhl[1], hl);
        
        width3(root.left, hl - 1, minmaxhl);
        width3(root.right, hl + 1, minmaxhl);
    }
    
    public static ArrayList <Integer> verticalOrderSum(TreeNode root) {
        
        int[] minmaxhl = new int[2];
        width3(root, 0, minmaxhl);
        
        int w = minmaxhl[1] - minmaxhl[0] + 1;
        
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for(int i = 0; i < w; i++) {
            ans.add(new ArrayList<>());
        }
        
        Queue<Pair8> q = new ArrayDeque<>();
        q.add(new Pair8(root, -minmaxhl[0]));
        
        while(q.size() != 0) {
            Pair8 rm = q.remove();
            
            ans.get(rm.hl).add(rm.node.val);
            
            if(rm.node.left != null) q.add(new Pair8(rm.node.left, rm.hl - 1));
            if(rm.node.right != null) q.add(new Pair8(rm.node.right, rm.hl + 1));
            
        }
        
        ArrayList<Integer> sum = new ArrayList<>();
        
        for(ArrayList<Integer> list : ans) {
            int s = 0;
            for(int ele : list) {
                s += ele;
            }
            sum.add(s);
        }
        
        return sum;
    }

    public static class Pair12{
        TreeNode node;
        int hl;
        
        Pair12(TreeNode node, int hl) {
            this.node = node;
            this.hl = hl;
        }
    }
    
    public static void width5(TreeNode root, int hl, int[] minmaxhl) {
        if(root == null) return;
        
        minmaxhl[0] = Math.min(minmaxhl[0], hl);
        minmaxhl[1] =Math.max(minmaxhl[1], hl);
        
        width5(root.left, hl - 1, minmaxhl);
        width5(root.right, hl + 1, minmaxhl);
    }
    
    public static ArrayList <Integer> verticalOrderSum2(TreeNode root) {
        
        int[] minmaxhl = new int[2];
        width5(root, 0, minmaxhl);
        
        int w = minmaxhl[1] - minmaxhl[0] + 1;
        
        ArrayList<Integer> sum = new ArrayList<>();
        
        for(int i = 0; i < w; i++) {
            sum.add(0);
        }
        
        Queue<Pair12> q = new ArrayDeque<>();
        q.add(new Pair12(root, -minmaxhl[0]));
        
        while(q.size() != 0) {
            Pair12 rm = q.remove();
            
            int s = sum.get(rm.hl);
            sum.set(rm.hl, s + rm.node.val);
            
            if(rm.node.left != null) q.add(new Pair12(rm.node.left, rm.hl - 1));
            if(rm.node.right != null) q.add(new Pair12(rm.node.right, rm.hl + 1));
            
        }
        
        return sum;
    }

    //vertical order sum using dfs approach without hashmap, arraylist

    public static class ListNode {
        int val;
        ListNode next;
        ListNode prev;
        
        ListNode() {
            
        }
        
        ListNode(int val) {
            this.val = val;
        }
    }
    public static void helper(TreeNode root, ListNode node) {
        node.val += root.val;
        //if(node == null) return;
        
        if(root.left != null) {
            if(node.prev == null) {
                //add first in linkedlist
                ListNode nn = new ListNode(0);
                nn.next = node;
                node.prev = nn; 
            }
            //call to left
            helper(root.left, node.prev);
        }
        
        if(root.right != null) {
            if(node.next == null) {
                //add last in linked list
                ListNode nn = new ListNode(0);
                nn.prev = node;
                node.next = nn;
            }
            helper(root.right, node.next);
        }
        
    }

    public static ArrayList<Integer> verticalOrderSum3(TreeNode root) {
        ListNode dummy = new ListNode(0);
        helper(root, dummy);
        
        ArrayList<Integer> ans = new ArrayList<>();
        
        ListNode curr = dummy;
        while(curr.prev != null) curr = curr.prev;

        while(curr != null) {
            ans.add(curr.val);
            curr = curr.next;
        }
        
        return ans;
    }

    //diagonal order sum of binary tree
    public static ArrayList <Integer> diagonalOrderSum(TreeNode root) 
    {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        Queue<TreeNode> q = new ArrayDeque<>();
        
        q.add(root);
        
        while(q.size()!= 0) {
            int sz = q.size();
            ArrayList<Integer> diag = new ArrayList<>();
            while(sz-->0) {
                TreeNode rm = q.remove();
                
                while(rm != null) {
                    if(rm.left != null) {
                        q.add(rm.left);
                    }
                    diag.add(rm.val);
                    
                    rm = rm.right;
                }
            }
            ans.add(diag);
        }
        
        ArrayList<Integer> sum = new ArrayList<>();
        for(ArrayList<Integer> list : ans) {
            int s = 0;
            for(int ele : list) {
                s += ele;
            }
            sum.add(s);
        }
        
        return sum;
    }

    //recursive manner
    public static void dos_helper(TreeNode root, int lvl, ArrayList <Integer> ans){
        if(lvl < ans.size()) {
            int s = ans.get(lvl);
            ans.set(lvl, s + root.val);
        } else {
            ans.add(root.val);   
        }
        
        
        if(root.left != null) dos_helper(root.left, lvl + 1, ans);
        if(root.right != null) dos_helper(root.right, lvl, ans);
        
    }
    
    public static ArrayList <Integer> diagonalOrderSum2(TreeNode root) {
        ArrayList <Integer> ans = new ArrayList<>();
        
        dos_helper(root, 0, ans);
        return ans;
    }

    //root to all leaf paths
    //void type
    public static void helper(TreeNode root, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> ans) {
        if(root == null) return;
        if(root.left == null && root.right == null) {
            ans.add(root.val);
            ArrayList<Integer> base = new ArrayList<>();
            res.add(new ArrayList<>(ans));
            ans.remove(ans.size() - 1); //last wale ko remove krna hoga kyuki isko issi case mei add krra
            return;
        }
        
        ans.add(root.val);
        
        helper(root.left, res, ans);
        helper(root.right, res, ans);
  
        ans.remove(ans.size() - 1);
        
    }
  
    public static ArrayList<ArrayList<Integer>> rootToAllLeafPath(TreeNode root) {
      ArrayList<ArrayList<Integer>> res = new ArrayList<>();
      ArrayList<Integer> ans = new ArrayList<>();
      
      helper(root, res, ans);
      return res;
    }

    //can be done using return type
}
