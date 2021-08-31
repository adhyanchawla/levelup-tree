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

}
