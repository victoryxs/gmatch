import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


class TreeNode   
{  
	//节点
    private int nodeId;    
    private int parentId;  
       
    public TreeNode(int nodeId)  
    {  
        this.nodeId = nodeId;  
    }  
      
 
    public TreeNode(int nodeId, int parentId)  
    {  
        this.nodeId = nodeId;  
        this.parentId = parentId;  
    }  
  
    public int getNodeId() {  
        return nodeId;  
    }  
  
    public void setNodeId(int nodeId) {  
        this.nodeId = nodeId;  
    }  
  
    public int getParentId() {  
        return parentId;  
    }  
  
    public void setParentId(int parentId) {  
        this.parentId = parentId;  
    }  
      
}  


class ManyTreeNode   
{  
	//节点集合
    private TreeNode data;   
    private List<ManyTreeNode> childList;  
      
  
    public ManyTreeNode(TreeNode data)  
    {  
        this.data = data;  
        this.childList = new ArrayList<ManyTreeNode>();  
    }  
      
    public ManyTreeNode(TreeNode data, List<ManyTreeNode> childList)  
    {  
        this.data = data;  
        this.childList = childList;  
    }  
  
    public TreeNode getData() {  
        return data;  
    }  
  
    public void setData(TreeNode data) {  
        this.data = data;  
    }  
  
    public List<ManyTreeNode> getChildList() {  
        return childList;  
    }  
  
    public void setChildList(List<ManyTreeNode> childList) {  
        this.childList = childList;  
    }  
}  


public class ManyNodeTree {
	//m阶树
	 private ManyTreeNode root;  
    
	    public ManyNodeTree()  
	    {  
	        root = new ManyTreeNode(new TreeNode(0));  
	    }  
	      
	   
	   
	   /**
	    * 添加孩子
	    * @param child
	    */
	    public void addChild(TreeNode child)  
	    {  
	    	Stack<ManyTreeNode> stack = new Stack<ManyTreeNode>();
	    	ManyTreeNode root = this.getRoot();
	    	stack.push(root);
	    	while(!stack.isEmpty()){
	    		ManyTreeNode node1 = stack.pop();
	    		if(node1.getData().getNodeId() == child.getParentId()){
	    			if(!contatinId(node1.getChildList(), child.getNodeId())){
	    			ManyTreeNode manytree = new ManyTreeNode(child);
					node1.getChildList().add(manytree);
	    			}
	    			break;
	    		}
	    		else{
	    			Iterator<ManyTreeNode> it2 = node1.getChildList().iterator();
	    			while(it2.hasNext()){
	    					stack.push(it2.next());
	    			}
	    		}
	    	}
	    } 
	      
	   /**
	    * 是否包含这个孩子
	    * @param childList
	    * @param nodeId
	    * @return
	    */
	    private boolean contatinId(List<ManyTreeNode> childList, int nodeId) {
			// TODO Auto-generated method stub
			Iterator<ManyTreeNode> it = childList.iterator();
			while(it.hasNext()){
				if(it.next().getData().getNodeId() == nodeId){
					return true;
				}
			}
			return false;
		}



	    /**
	     * 遍历节点
	     * @param manyTreeNode
	     * @return
	     */
		public String iteratorTree(ManyTreeNode manyTreeNode)  
	    {  
	        StringBuilder buffer = new StringBuilder();  
	        buffer.append("\n");  
	          
	        if(manyTreeNode != null)   
	        {     
	            for (ManyTreeNode index : manyTreeNode.getChildList())   
	            {  
	                buffer.append(index.getData().getNodeId()+ ",");  
	                  
	                if (index.getChildList() != null && index.getChildList().size() > 0 )   
	                {     
	                    buffer.append(iteratorTree(index));  
	                }  
	            }  
	        }  
	          
	        buffer.append("\n");  
	          
	        return buffer.toString();  
	    }  
	      
		/**
		 * 显示DDT树
		 */
		public String toString()  
	    {  
	        StringBuilder buffer = new StringBuilder();  
	        buffer.append("\n");  
	          
	        if(root != null)   
	        {     
	            for (ManyTreeNode index :root.getChildList())   
	            {  
	                buffer.append(index.getData().getNodeId()+ ",");  
	                  
	                if (index.getChildList() != null && index.getChildList().size() > 0 )   
	                {     
	                    buffer.append(iteratorTree(index));  
	                }  
	            }  
	        }  
	          
	        buffer.append("\n");  
	          
	        return buffer.toString();  
	    }  
	      
		/**
		 * 获取root
		 * @return
		 */
	    public ManyTreeNode getRoot() {  
	        return root;  
	    }  
	  
	    /**
	     * 设置root
	     * @param root
	     */
	    public void setRoot(ManyTreeNode root) {  
	        this.root = root;  
	    }
	    
	    
	    public static void main(String[] args) {
			ManyNodeTree tree = new ManyNodeTree();
			TreeNode child = new TreeNode(1, 0);
			TreeNode child2 = new TreeNode(2, 0);
			TreeNode child3 = new TreeNode(3, 0);
			TreeNode child4 = new TreeNode(4, 2);
			TreeNode child8 = new TreeNode(4, 2);
			TreeNode child5 = new TreeNode(5, 2);
			TreeNode child6 = new TreeNode(7, 1);
			TreeNode child7 = new TreeNode(8, 3);
			tree.addChild(child);
			tree.addChild(child2);
			tree.addChild(child3);
			tree.addChild(child4);
			tree.addChild(child5);
			tree.addChild(child6);
			tree.addChild(child7);
			tree.addChild(child7);
			System.out.println(tree.iteratorTree(tree.getRoot()));
		}
}


