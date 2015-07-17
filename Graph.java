import java.util.*;

/**
 * 鍥剧殑閭绘帴琛ㄨ〃绀� * @author victoryxs
 *
 */
class Edge{
	// 杈�	
	private int id;
	private int dest;
	private int head;
	private Edge link;

	
	Edge(int id, int head, int dest){
		this.dest = dest;
		this.head = head;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHead() {
		return head;
	}
	public void setHead(int head) {
		this.head = head;
	}
	public int getDest(){
		return this.dest;
	}
	public void setDest(int dest){
		this.dest = dest;
	}
	public Edge getLink(){
		return this.link;
	}
	public void setLink(Edge link){
		this.link = link;
	}
	public String toString(){
		return "id="+ id + ",from" + head + "to" + dest;
	}

}


class Vertex{
	//鑺傜偣
	
	private int key;
	private String label;
	private Edge adj;
	private boolean isVisit;
	
	public Vertex(){
		
	}
	public Vertex(int key, String label){
		this.key = key;
		this.label = label;
	}
	
	public int getKey(){
		return this.key;
	}
	public void setKey(int key){
		this.key = key;
	}
	public String getLabel(){
		return this.label;
	}
	public void setLabel(String label){
		this.label = label;
	}
	public Edge getAdj(){
		return this.adj;
	}
	public void setAdj(Edge adj){
		this.adj = adj;
	}
	public void setIsVisit(boolean isVisit){
		this.isVisit = isVisit;
	}
	public boolean getIsVisit(){
		return isVisit;
	}
	public String toString(){
		return key + ":" + label;
	}
}


class NearNode{
	//杈�	
	Vertex vex;
	int dest;
	
	NearNode(Vertex vex, int dest){
		this.vex = vex;
		this.dest = dest;
	}
	
	public Vertex getVex() {
		return vex;
	}
	public void setVex(Vertex vex) {
		this.vex = vex;
	}
	public int getDest() {
		return dest;
	}
	public void setDest(int dest) {
		this.dest = dest;
	}
} 





public class Graph {
			
	
	protected int numEdges;
	protected int numVertices;
	protected ArrayList<Vertex> NodeTabel = null;  
	protected HashSet<String> labels = null;  //鏍囩闆嗗悎
 	protected HashMap<String,ArrayList<Integer>> relation = null;  //鍝堝笇瀵瑰簲鍏崇郴
    
 	
    
	
 	public Graph(){
		init();
	}

	
	public int getNumEdges() {
		return numEdges;
	}
	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}
	public int getNumVertices() {
		return numVertices;
	}
	public void setNumVertices(int numVertices) {
		this.numVertices = numVertices;
	}
	public ArrayList<Vertex> getNodeTabel() {
		return NodeTabel;
	}
	public void setNodeTabel(ArrayList<Vertex> nodeTabel) {
		NodeTabel = nodeTabel;
	}
	public HashSet<String> getLabels() {
		return labels;
	}
	public void setLabels(HashSet<String> labels) {
		this.labels = labels;
	}
	public HashMap<String, ArrayList<Integer>> getRelation() {
		return relation;
	}
	public void setRelation(HashMap<String, ArrayList<Integer>> relation) {
		this.relation = relation;
	}


	/**
	 * 鍒濆鍖栭偦鎺ヨ〃
	 * @param size 鏁扮粍鏈�ぇ瀹归噺
	 */
	private void init(){
		this.numVertices = 0;
		this.numEdges = 0;
		labels = new HashSet<String>();
		relation = new HashMap<String,ArrayList<Integer>>();
		NodeTabel = new ArrayList<Vertex>();
		if (NodeTabel == null) {
			System.out.println("failed to create graph");
			System.exit(1);
		}
		
	}
	
	/**
	 * 閿�瘉閭绘帴琛�	 */
	public void destory(){
		for(int i = 0; i < numVertices; i++){
			Edge p = NodeTabel.get(i).getAdj();
			Edge q;
			 while (p != null) {
			    q = p;
				NodeTabel.get(i).setAdj(p.getLink());
				q = null;
				p = NodeTabel.get(i).getAdj();
			}
		}	
	}

	
	/**
	 * 娣诲姞鑺傜偣
	 * @param key 鑺傜偣鏍囪瘑
	 * @param label 鑺傜偣鏍囩
	 */
	public void addVertice(int key, String label){
		
		Vertex vex = new Vertex(key, label);
		vex.setAdj(null);
		vex.setIsVisit(false);
		NodeTabel.add(vex);
		numVertices++;
		labels.add(label);
		if(relation.containsKey(label)){
		relation.get(label).add(key);}
		else{
			ArrayList<Integer> list = new ArrayList<Integer>();
			list.add(key);
			relation.put(label,list);
		}
	}
	
	/**
	 * 娣诲姞杈�	 * @param from 鍏ョ偣
	 * @param to  鍑虹偣
	 */
	public void addEdge(int from, int to){
			this.addEdge1(from, to);
			this.addEdge1(to, from);
			numEdges++;
		
	}
	
	/**
	 * 绉佹湁鏂规硶 鍒犻櫎鍗曡竟
	 * @param from
	 * @param to
	 */
	private void addEdge1(int from, int to){
		Edge e1 = new Edge(numEdges+1,from,to);
		int v1 = getVertexPos(from);	
		e1.setLink(null);
		Edge e = NodeTabel.get(v1).getAdj();
		if(e == null){
			NodeTabel.get(v1).setAdj(e1);
		}else{
			
			while(e.getLink() != null){
				e = e.getLink();
			}
			e.setLink(e1);
		}
	}
	
	
	/**
	 * 鍒犻櫎鑺傜偣
	 * @param key 鑺傜偣鏍囪瘑
	 */
	public void removeVertices(int key){
		int v = getVertexPos(key);
		
		String templabel = NodeTabel.get(v).getLabel(); 
		ArrayList<Integer> list = relation.get(templabel);
		list.remove((Integer)key);
		if(list.isEmpty()){
			relation.remove(templabel);
			labels.remove(templabel);
		}

		
			
			Edge edge = NodeTabel.get(v).getAdj();
			Edge temp = null;
			while (edge != null) {
				temp = edge.getLink();
				removeEdge(key,edge.getDest());
				edge = temp;
			}
			
		    NodeTabel.remove(v);
			numVertices--;
			
	}
	
	
	/**
	 * 鍒犻櫎杈�	 * @param from 鍏ョ偣
	 * @param to  鍑虹偣
	 */
	public void removeEdge(int from, int to){
		
		int v1 = getVertexPos(from);
		int v2 = getVertexPos(to);
		
		
		Edge pre = NodeTabel.get(v1).getAdj();
		Edge next = pre.getLink();
		Edge temp;
		if (pre == null) {
			System.out.print(v2 + " not exist in NodeTable" + v1);
		}
		if(pre != null && pre.getDest() == to){
			temp = pre;
			NodeTabel.get(v1).setAdj(pre.getLink());
			temp = null;
		}else{
			while (next != null && next.getDest() != to) {
				pre = pre.getLink();
				next = next.getLink();
			}
			temp = next;
			pre.setLink(next.getLink());
			temp = null;
		}
	
	
		pre = NodeTabel.get(v2).getAdj();
		next = pre.getLink();
		if (pre == null) {
			System.out.print(v1 + " not exist in NodeTable" + v2);
		}
		if(pre != null && pre.getDest() == from){
			temp = pre;
			NodeTabel.get(v2).setAdj(pre.getLink());
			temp = null;
		}else{
			while (next != null && next.getDest() != from) {
				pre = pre.getLink();
				next = next.getLink();
			}
			temp = next;
			pre.setLink(next.getLink());
			temp = null;
		}

		numEdges--;
	}
	
		
	
	/**
	 * 绉佹湁鏂规硶锛岃妭鐐逛綅缃畾浣�	 * @param vertx 鑺傜偣鏍囪瘑
	 * @return i in NodeTabel  
	 */
	private int getVertexPos(int vertx){
		for(int i = 0; i < numVertices; i++){
			if(NodeTabel.get(i).getKey() == vertx){
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * 鎵撳嵃閭绘帴琛�	 */
	public void printGraph(){
		System.out.println("total vexs:"+this.numVertices);
		System.out.println("total edges:"+this.numEdges);
		for(int i = 0; i < NodeTabel.size(); i++){
			System.out.print(NodeTabel.get(i).getKey()+":"+NodeTabel.get(i).getLabel()+" ");
			Edge e = NodeTabel.get(i).getAdj();
			while(e != null){
				System.out.print(e.getDest() + ":" + NodeTabel.get(getVertexPos(e.getDest())).getLabel() + " "); 
				e = e.getLink();
			}
			System.out.println("\n");
		}
	}
	
	
	private Stack stack;   //杈呭姪鏍�   
	private Queue queue;	//杈呭姪闃熷垪
    
    /**
     * 娣卞害閬嶅巻
     * @param key
     * @return int[] dfs
     */
	public ArrayList<Integer> dfs(int key){ 
		ArrayList<Integer> dfs = new ArrayList<Integer>();
				
		int v = getVertexPos(key);
    	stack = new Stack<Vertex>();
    	NodeTabel.get(v).setIsVisit(true);
    	dfs.add(NodeTabel.get(v).getKey());
    	
    	stack.push(NodeTabel.get(v));
    	int dfsIndex = 0;
    	
    	Vertex vex;
    	while(!stack.isEmpty()){
    		vex = getAdjVertex((Vertex)stack.peek());
    		if(vex == null){
    			stack.pop();
    		}else{
    			vex.setIsVisit(true);
    			dfs.add(vex.getKey());
    			dfsIndex++;
    			stack.push(vex);
    		}
    	}
    	for(int i = 0; i < numVertices; i++){
    		NodeTabel.get(i).setIsVisit(false);
    	}
    	return dfs;
    }
	
	
	/**
	 * 骞垮害閬嶅巻
	 * @param key
	 * @return 
	 */
    public ArrayList<Integer> bfs(int key){
    	ArrayList<Integer> bfs = new ArrayList<Integer>();
    	    	
		int v = getVertexPos(key);
		queue = new LinkedList<Vertex>();
		NodeTabel.get(v).setIsVisit(true);
		bfs.add(NodeTabel.get(v).getKey());
		queue.offer(NodeTabel.get(v));
		int bfsIndex = 0;
		Vertex vex;
		while(!queue.isEmpty()){
			Vertex vex2 = (Vertex)queue.poll();
			while((vex = getAdjVertex(vex2)) != null){
				vex.setIsVisit(true);
				bfs.add(vex.getKey());
				bfsIndex++;
				queue.offer(vex);
			}
		}	
		for(int i = 0; i < numVertices; i++){
			NodeTabel.get(i).setIsVisit(false);
		}
		return bfs;
	}
    
    
    /**
     * @ 鑾峰彇杩為�鍒嗛噺
     */
    public ArrayList<Graph> getConnectedComponent(){
    	ArrayList<Graph> list = new ArrayList<Graph>();
    	Graph g = null;
    	ArrayList<Integer> unvisited = this.getVexs();
    	while(!unvisited.isEmpty()){
    		ArrayList<Integer> list2 =this.dfs(unvisited.get(0));
    		g = createbyVexs(list2);
    		list.add(g);
    		unvisited = Common.Difference(unvisited, list2);
    	}
    	return list;
    }
    

    /**
    *  鍒涘缓鍥�
    */
    private Graph createbyVexs(ArrayList<Integer> list2) {
    	Graph g = new Graph();
    	g.numVertices = list2.size();
    	int edges = 0;
    	Iterator<Integer> it = list2.iterator();
    	while(it.hasNext()){
    		Vertex vex = NodeTabel.get(getVertexPos(it.next()));
    		g.NodeTabel.add(vex);
    		g.labels.add(vex.getLabel());
    		if(!g.relation.containsKey(vex.getLabel())){
    			ArrayList<Integer> list = new ArrayList<Integer>();
    			list.add(vex.getKey());
    			g.relation.put(vex.getLabel(), list);
    		}else{
    		g.relation.get(vex.getLabel()).add(vex.getKey());}
    		Edge e = vex.getAdj();
    		while(e != null){
    			edges++;
    			e = e.getLink();
    		}
    	}
   	g.numEdges = edges / 2;
    	return g;
    }
    
    /**
     * 绉佹湁鏂规硶 鑾峰彇鐐归泦鍚�     * @return
     */
	public ArrayList<Integer> getVexs(){
    	ArrayList<Integer> vexs = new ArrayList<Integer>();
    	Iterator<Vertex> it = this.NodeTabel.iterator();
    	while(it.hasNext()){
    		vexs.add(it.next().getKey());
    	}
    	return vexs;
    }
   
	
	/**
     * 绉佹湁鏂规硶 鑾峰彇绗竴涓病鏈夐亶鍘嗙殑閭昏妭鐐�     * @param vex
     * @return
     */
    private Vertex getAdjVertex(Vertex vex){
    	Edge head = vex.getAdj();
    	while(head != null){
    		if(NodeTabel.get(getVertexPos(head.getDest())).getIsVisit() == false){
    			return NodeTabel.get(getVertexPos(head.getDest()));
    		}
    		head = head.getLink();
    	} 
    	return null;
    }
    
   
    /**
     * 鑾峰彇閭昏妭鐐规爣绛鹃泦鍚�     * @param label
     * @return
     */
    public HashSet<String> getAdjSet(String label){
    	HashSet<String> adjset = new HashSet<String>();     	
    	ArrayList<Integer> vexbylabel = (ArrayList<Integer>)this.getRelation().get(label);
    	Iterator it = vexbylabel.iterator();
    	while(it.hasNext()){
    		Edge e = NodeTabel.get(getVertexPos((int)it.next())).getAdj();
    		while(e != null){
    			adjset.add(NodeTabel.get(getVertexPos(e.getDest())).getLabel());
    			e = e.getLink();
    		}
    	}
    	return adjset;
    }
   
    
    /**
     * 鑾峰彇閭昏妭鐐归泦鍚�     * @param key
     * @return
     */
    public ArrayList<Integer> getAdjSet(int key){
    	int vex = getVertexPos(key);
    	ArrayList<Integer> list = new ArrayList<Integer>(); 
    	Edge e = NodeTabel.get(vex).getAdj();
    	while(e != null){
    		list.add(e.getDest());
    		e = e.getLink();
    	}
    	return list;
    }

    
    /**
     * 閫氳繃key鍊艰幏鍙栨爣绛�     * @param key
     * @return
     */
    public String getLabelbyKey(int key){
    	return NodeTabel.get(getVertexPos(key)).getLabel();
    }
    
    
       
    
    /**
     * 閫氳繃鏍囩鑾峰彇鏈�繎閭昏妭鐐归泦鍚�     * @param label
     * @return
     */
    public ArrayList<NearNode> getNearNode(int key, String label){
    	ArrayList<NearNode> nearnode = new ArrayList<NearNode>();
    	int dept = 1;
    	int predept = 0;
    	Queue<NearNode> queue = new LinkedList<NearNode>();
    	Edge e = NodeTabel.get(getVertexPos(key)).getAdj();
    	while( e != null){
    		NodeTabel.get(getVertexPos(e.getDest())).setIsVisit(true);
    		NearNode node = new NearNode(NodeTabel.get(getVertexPos(e.getDest())),dept);
    		predept = dept;
    		queue.offer(node);
    		e = e.getLink();
    	}
    	Vertex vex;
    	while(!queue.isEmpty()){
    		NearNode node1 = queue.poll();
    		if(predept != node1.getDest() && nearnode.size() != 0){
    			break;
    		}else{
    			predept = node1.getDest();
    		}
    		if(node1.getVex().getLabel().equals(label)){
    			nearnode.add(node1);
    		}
    		while((vex = getAdjVertex(node1.getVex())) != null){
    			dept = node1.getDest() + 1;
    			if(dept > 3){
    				break;
    			}
    			vex.setIsVisit(true);
    			NearNode node2 = new NearNode(vex, dept);
    			queue.offer(node2);
    		}
    	}
    	
    	for(int i = 0; i < numVertices; i++){
			NodeTabel.get(i).setIsVisit(false);
		}

    	return nearnode;
    }
     
    
    /**
     * 鍒ゆ柇鏄惁鍖呭惈杈�     * @param key
     * @param key2
     * @return
     */
    public boolean containEdge(int key, int key2) {
		// TODO Auto-generated method stub
		int v = getVertexPos(key);
		if(v == -1){
			return false;
		}
		Edge e = NodeTabel.get(v).getAdj();
		while(e != null){
			if(e.getDest() == key2){
				return true;
			}
		}
		return false;
	}
    
    
    /**
     * 鍒ゆ柇鏄惁鍖呭惈鐐�     * @param key
     * @return
     */
    public boolean containVertex(int key){
    	if(getVertexPos(key) == -1){
    		return false;
    	}else{
    		return true;
    	}
    }
    
    /**
     * 鑾峰彇杈归泦
     * @return
     */
    public ArrayList<Integer> getEdges(){
    	HashSet<Integer> set = new HashSet<Integer>();
    	Iterator<Vertex> it = NodeTabel.iterator();
    	while(it.hasNext()){
    		Edge e = it.next().getAdj();
    		while(e != null){
    			set.add(e.getId());
    			e = e.getLink();
    		}
    	}
    	return  new ArrayList<Integer>(set);
    }
       
    /**
     * prim绠楁硶瀹炵幇
     * @param key
     * @return 杈归泦
     */
    public ArrayList<Integer> prim(int key){
    	int length = this.numVertices;
    	int[] prim = new int[length];
    	ArrayList<Integer> edges = new ArrayList<Integer>();
    	int index = 0;
    	prim[index++] = key;
    	while(index < length){
    		int MIN = Integer.MAX_VALUE;
        	int key2 = 0;
    		for(int i = 0; i < index; i++){
    			Edge e = NodeTabel.get(getVertexPos(prim[i])).getAdj();
    			while(e != null){
    				if(e.getId() < MIN && !edges.contains(e.getId())){
    					MIN = e.getId();
    					key2 = e.getDest();
    				}
    				e = e.getLink();
    			}
    		}
    		prim[index++] = key2;
    		edges.add(MIN);
    	}
    	return edges;
    	
    }
    
    /**
     * 鏍规嵁杈归泦鐢熸垚DDT鏍�     * @param edges
     * @return DDT鏍�     */
    public ManyNodeTree creatDDT(ArrayList<Integer> edges){
    	ManyNodeTree tree = new ManyNodeTree();
    	Iterator<Integer> it = edges.iterator();
    	int pre = 0;
    	while(it.hasNext()){
    		int i = it.next();
    		TreeNode node = new TreeNode(i,pre);
    		tree.addChild(node);
    		pre = i;
    	}
    	return tree;
    }
    
    /**
     * 涓ゅ眰閫掑綊鐢熸垚QST鏍�     * @param key
     * @param h
     * @param T
     * @param TR
     * @param threshold
     * @param QT
     */
//    public void GeneQST(int key, int h, ArrayList<Integer> T, ArrayList<Integer> TR, 
//    		int threshold, ManyNodeTree QT){
//    	if(h < this.numVertices - 1){
//    		GeneQST(key, h + 1, T, TR, threshold, QT);
//    	}
//    	if(TR.size() < threshold){
//    		ArrayList<Integer> minus = Common.Difference(this.getEdges(),Common.Union(T, TR));
//    		int i = findsmallest(minus,T,h);
//    		if(i != -1){
//    			ArrayList<Integer> minus1 = new ArrayList<Integer>(T);
//    			int e = minus1.get(h - 1);
//    			minus1.set(h-1, i);
//    			ArrayList<Integer> T_temp = reOrding(key, minus1, h);
//    			ArrayList<Integer> TR_temp = new ArrayList<Integer>(TR);
//    			TR_temp.add(e);
//    			if(h - 2 < 0){
//    				int pre = 0;
//    				for(int j = 0; j < T_temp.size(); j++){
//    					TreeNode node = new TreeNode(T_temp.get(j), pre);
//    					pre = T_temp.get(j);
//    					QT.addChild(node);
//    				}
//    			}else{
//    				int pre = h - 2;
//    				for(int j = h - 1; j < T_temp.size(); j++){
//    					TreeNode node = new TreeNode(T_temp.get(j), T_temp.get(pre));
//    					pre = j;
//    					QT.addChild(node);
//    				}
//    			}
//    			GeneQST(key, h, T_temp, TR_temp, threshold, QT);
//    		}
//    	}
//    }    
   	
    
  public void GeneQST(int key, int h, ArrayList<Integer> T, ArrayList<Integer> TR, 
	int threshold, ArrayList<ArrayList<Integer>> QT){
if(h < this.numVertices - 1){
	GeneQST(key, h + 1, T, TR, threshold, QT);
}
if(TR.size() < threshold){
	ArrayList<Integer> minus = Common.Difference(this.getEdges(),Common.Union(T, TR));
	int i = findsmallest(minus,T,h);
	if(i != -1){
		ArrayList<Integer> minus1 = new ArrayList<Integer>(T);
		int e = minus1.get(h - 1);
		minus1.set(h-1, i);
		ArrayList<Integer> T_temp = reOrding(key, minus1, h);
		ArrayList<Integer> TR_temp = new ArrayList<Integer>(TR);
		TR_temp.add(e);
		QT.add(T_temp);
		GeneQST(key, h, T_temp, TR_temp, threshold, QT);
	}
}
}
 
    /**
     * 绉佹湁鏂规硶 prim閲嶆帓搴�     * @param key
     * @param minus1
     * @param h
     * @return 
     */
    private ArrayList<Integer> reOrding(int key, ArrayList<Integer> minus1, int h){
    	ArrayList<Integer> result = new ArrayList<Integer>();
    	HashSet<Integer> fixedvex = new HashSet<Integer>();
    	fixedvex.add(key);
    	HashSet<Integer> exsitedges = new HashSet<Integer>();
    	for(int i = 0; i < h - 1; i++){
    		result.add(minus1.get(i));
    		Edge e = findedgebyid(minus1.get(i));
    		if(e != null){
    			fixedvex.add(e.getDest());
    			fixedvex.add(e.getHead());
    		}
    	}
    	for(int i = h - 1; i < minus1.size(); i++){
    		exsitedges.add(minus1.get(i));
    	}
    	while(exsitedges.size() != 0){
    		int MIN = Integer.MAX_VALUE;
    		Edge e = null;
    		Iterator<Integer> it = fixedvex.iterator();
    		while(it.hasNext()){
    			int i = it.next();
    			Edge e1 = NodeTabel.get(getVertexPos(i)).getAdj();
    			while(e1 != null){
    				if(exsitedges.contains(e1.getId()) &&
    						e1.getId() < MIN){
    					MIN = e1.getId();
    					e = e1;
    				} 
    				e1 = e1.getLink();
    			}
    		}
    		result.add(MIN);
    		exsitedges.remove((Integer)MIN);
    		fixedvex.add(e.getDest());
    		fixedvex.add(e.getHead());
    	}
    	
    	return result;
    	}


    /**
     * 绉佹湁鏂规硶 鎵惧埌鏈�皬鐨勭鍚堟潯浠剁殑杈�     * @param minus
     * @param t
     * @param h
     * @return
     */
	private int findsmallest(ArrayList<Integer> minus, ArrayList<Integer> t,int h) {
		// TODO Auto-generated method stub
		int result = -1;
		ArrayList<Integer> list = new ArrayList<Integer>(t);
		list.remove(h - 1);
		ArrayList<Edge> edgeset  = new ArrayList<Edge>();
		HashSet<Integer> vexset = new HashSet<Integer>();
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()){
			int i = it.next();
			Edge e = findedgebyid(i);
			if(e != null){
				edgeset.add(e);
				vexset.add(e.getHead());
				vexset.add(e.getDest());
			}
		}
		Collections.sort(minus);
		Iterator<Integer> it2 = minus.iterator();
		while(it2.hasNext()){
			int i = it2.next();
			Edge e = findedgebyid(i);
			if(e != null){
			edgeset.add(e);
			vexset.add(e.getDest());
			vexset.add(e.getHead());
			Graph g = new Graph();
			Iterator<Integer> it3 = vexset.iterator();
			while(it3.hasNext()){
				int key = it3.next();
				g.addVertice(key, NodeTabel.get(getVertexPos(key)).getLabel());
			}
			Iterator<Edge> it4 = edgeset.iterator();
			while(it4.hasNext()){
				Edge e1 = it4.next();
				g.addEdge(e1.getHead(), e1.getDest());				
			}
			if(g.dfs((int) ((vexset.toArray())[0])).size() == this.numVertices){
				result = i;
				break;
			} 
			}
			
			
			
		}
		return result;
	}


	/**
	 * 閫氳繃id鎵惧埌杈�	 * @param i
	 * @return
	 */
	public Edge findedgebyid(int i) {
		// TODO Auto-generated method stub
		Iterator<Vertex> it = NodeTabel.iterator();
		while(it.hasNext()){
			Vertex v = it.next();
			Edge e = v.getAdj();
			while(e != null){
				if(e.getId() == i){
					return e;
				}
				e = e.getLink();
			}
		}
		return null;
	}


	/**
	 * 閫氳繃鑾峰彇鐐�	 * @param id
	 * @return
	 */
	public Vertex getVexbyid(int id){
		return NodeTabel.get(getVertexPos(id));
	}
	
	/**
	 * 鑾峰彇鍛ㄥ洿鏍囩闆嗗悎
	 * @param args
	 */
	public HashSet<String> getLabelsbyid(int id){
		HashSet<String> labels = new HashSet<String>();
		Vertex vex = NodeTabel.get(getVertexPos(id));
		Edge e = vex.getAdj();
		while(e != null){
			labels.add(NodeTabel.get(getVertexPos(e.getDest())).getLabel());
			e = e.getLink();
		}
		return labels;
	}
	
	public static void main(String[] args) {
		Graph g = new Graph();
		Graph query = new Graph();
		
//		g.addVertice(1, "A");
//		g.addVertice(2, "B");
//		g.addVertice(3, "B");
//		g.addVertice(4, "C");
//		g.addVertice(5, "C");
//		g.addVertice(6, "B");
//		g.addVertice(7, "A");
//		g.addVertice(9, "C");
//		g.addVertice(10, "B");
//		g.addVertice(11, "A");
//		g.addVertice(12, "B");
//		g.addVertice(13, "B");
//		g.addVertice(14, "C");
//		g.addVertice(15, "B");
//		
//		g.addEdge(1, 2);
//		g.addEdge(1, 3);
//		g.addEdge(1, 5);
//		g.addEdge(2, 4);
//		g.addEdge(5, 6);
//		g.addEdge(6, 7);
//		g.addEdge(9, 10);
//		g.addEdge(11, 12);
//		g.addEdge(11, 13);
//		g.addEdge(13, 14);
//		g.addEdge(14, 15);
//		
//		
////		g.removeVertices(1);
////		g.removeVertices(4);
//		g.removeEdge(5,6);
//
//		g.printGraph();	
		
		query.addVertice(1, "A");
		query.addVertice(2, "B");
		query.addVertice(3, "C");
		query.addVertice(4, "D");
		query.addVertice(5, "E");
		
		query.addEdge( 1, 2);
		query.addEdge( 2, 3);
		query.addEdge( 2, 4);
		query.addEdge( 3, 4); 
		query.addEdge( 4, 5);
	
		ArrayList<Integer> T = query.prim(1);
//		ArrayList<Integer> TR = new ArrayList<Integer>(); 
//		ArrayList<ArrayList<Integer>> QT = new ArrayList<ArrayList<Integer>>();
//		QT.add(T);
//		query.GeneQST(1, 1, T, TR , 2, QT);
//		Iterator<ArrayList<Integer>> iter = QT.iterator();
//		while(iter.hasNext()){
//			Iterator<Integer> iter2 = iter.next().iterator();
//			while(iter2.hasNext()){
//				System.out.print(iter2.next());
//			}
//			System.out.println();
//		}
		Iterator<Integer> iter = T.iterator();
		while(iter.hasNext()){
		System.out.println(iter.next());}
	}
}
