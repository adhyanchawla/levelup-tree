public class btlevelup {

    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode(int val) {
            this.val = val;
        }
    }
    
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
}
