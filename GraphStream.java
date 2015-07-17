import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;


class Node{
	//鐐�	
	private int key;
	private String label;
	
	public Node(int key, String label){
		this.key = key;
		this.label = label;
	}
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}	
}



public class GraphStream {
	
	
	enum OPT{
		ADD,DELETE
	}
	private class Operator{
		OPT opt;
		int timeslot;
		Node from;
		Node to;
	}
	
	
	private Graph orgin;
	private Graph query;
	private ArrayList<Graph> orginbySplit;
	private HashMap<String, HashSet<String>> NNIndex;
	private Graph GSP;
	private ArrayList<Operator> OperatorSequence;
	
	GraphStream(Graph orgin, Graph query){
		this.orgin = orgin;
		this.query = query;
		//init();
	}

	public Graph getOrgin() {
		return orgin;
	}
	public void setOrgin(Graph orgin) {
		this.orgin = orgin;
	}
	public Graph getQuery() {
		return query;
	}
	public void setQuery(Graph query) {
		this.query = query;
	}
	public ArrayList<Graph> getOrginbySplit() {
		return orginbySplit;
	}
	public void setOrginbySplit(ArrayList<Graph> orginbySplit) {
		this.orginbySplit = orginbySplit;
	}

	/**
	 * 鍒濆鍖栧浘娴�	 */
	void init(){
		Algorithm alg = new Algorithm();
		this.NNIndex = alg.NNInit(query);
		this.orginbySplit = alg.handler(orgin, query);
		this.GSP = alg.GSP(query, orgin);
		this.OperatorSequence = new ArrayList<GraphStream.Operator>();
		
	}
	
	/**
	 * 鎻掑叆鎿嶄綔
	 * @param from
	 * @param to
	 * @param timeslot
	 */
	public void inseart(Node from, Node to, int timeslot){
		Operator opera = new Operator();
		opera.opt = OPT.ADD;
		opera.from = from;
		opera.to = to;
		opera.timeslot = timeslot;
		this.OperatorSequence.add(opera);
		if(query.getLabels().contains(from.getLabel()) && query.getLabels().contains(to.getLabel())){
			if(NNIndex.get(from.getLabel()).contains(to.getLabel())){
				if(GSP.containVertex(from.getKey()) &&
				!GSP.containVertex(to.getKey())){
					inseartintoSplitGraph(this.orginbySplit,from,to);
				}else if(GSP.containVertex(to.getKey()) &&
				!GSP.containVertex(from.getKey())){
					inseartintoSplitGraph(this.orginbySplit,to,from);
				}else{
					if(isSameSplit(orginbySplit, from, to)){
						inseartintoSplitGraph(orginbySplit, from, to);
					}else{
						UnionSplit(orginbySplit, from, to);
					}
				}
			}
		}
	}
	
	private void inseartintoSplitGraph(ArrayList<Graph> orginbySplit, Node from, Node to){
			Iterator<Graph> it = orginbySplit.iterator();
			while(it.hasNext()){
				Graph g = it.next();
				if(g.containVertex(from.getKey()) && !g.containVertex(to.getKey())){
					g.addVertice(to.getKey(), to.getLabel());
					g.addEdge(from.getKey(), to.getKey());
				}else if(g.containVertex(to.getKey()) && !g.containVertex(from.getKey())){
					g.addVertice(from.getKey(), from.getLabel());
					g.addEdge(from.getKey(), to.getKey());
				}else if(g.containVertex(to.getKey()) && g.containVertex(from.getKey())){
					g.addEdge(from.getKey(), to.getKey());
				}else{
					
				}
			}
	}
	
	private void UnionSplit(ArrayList<Graph> orginbySplit, Node from, Node to){
		Graph g1 = null;
		Graph g2 = null;
		Iterator<Graph> it = orginbySplit.iterator();
		while(it.hasNext()){
			Graph g3 = it.next();
			if(g3.containVertex(from.getKey())){
				g1 = g3;
			}
		}
		it = orginbySplit.iterator();
		while(it.hasNext()){
			Graph g3 = it.next();
			if(g3.containVertex(to.getKey())){
				g2 = g3;
			}
		}
		orginbySplit.remove(g1);
		orginbySplit.remove(g2);
		Graph g = UnionGraph(g1, g2);
		g.addEdge(from.getKey(), to.getKey());
		orginbySplit.add(g);
	}
	
	
	private Graph UnionGraph(Graph g1, Graph g2) {
		// TODO Auto-generated method stub
		Graph g = new Graph();
		ArrayList<Vertex> NodeTabel = new ArrayList<Vertex>();
		NodeTabel.addAll(g1.getNodeTabel());
		NodeTabel.addAll(g2.getNodeTabel());
		g.setNodeTabel(NodeTabel);
		
		g.setNumEdges(g1.getNumEdges() + g2.getNumEdges());
		g.setNumVertices(g1.getNumVertices() + g2.getNumVertices());
		
		HashSet<String> labels = new HashSet<String>();
		labels.addAll(g1.getLabels());
		labels.addAll(g2.getLabels());
		g.setLabels(labels);
		
		HashMap<String, ArrayList<Integer>> relation = new HashMap<String, ArrayList<Integer>>();
		relation.putAll(g2.getRelation());
		Iterator<String> it = g1.getRelation().keySet().iterator();
		while(it.hasNext()){
			String label = it.next();
		if(relation.keySet().contains(label)){
			HashSet<Integer> set = new HashSet<Integer>();
			set.addAll(relation.get(label));
			set.addAll(g1.getRelation().get(label));
			ArrayList<Integer> list = new ArrayList<Integer>(set);
			relation.put(label, list);
		}else{
			relation.put(label, g1.getRelation().get(label));
		}
		}
		g.setRelation(relation);
		
		return g;
		
	}

	private boolean isSameSplit(ArrayList<Graph> orginbySplit, Node from, Node to){
		Iterator<Graph> it = orginbySplit.iterator();
		while(it.hasNext()){
			Graph g = it.next();
			if(g.containVertex(from.getKey()) && g.containVertex(to.getKey())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 鍒犻櫎鎿嶄綔
	 * @param from
	 * @param to
	 * @param timesolt
	 */
	public void delete(Node from, Node to, int timeslot){
		
		Operator opera = new Operator();
		opera.opt = OPT.DELETE;
		opera.from = from;
		opera.to = to;
		opera.timeslot = timeslot;
		this.OperatorSequence.add(opera);
		if(GSP.containEdge(from.getKey(), to.getKey())){
			Iterator<Graph> it = this.orginbySplit.iterator();
			while(it.hasNext()){
				Graph g = it.next();
				if(g.containEdge(from.getKey(), to.getKey())){
					g.removeEdge(from.getKey(), to.getKey());
					if(g.getAdjSet(from.getKey()).size() == 0 && 
							g.getAdjSet(to.getKey()).size() != 0){
						g.removeVertices(from.getKey());
						break;
					}else if(g.getAdjSet(from.getKey()).size() != 0 && 
					g.getAdjSet(to.getKey()).size() == 0){
						g.removeVertices(to.getKey());
						break;
					}else if(g.getAdjSet(from.getKey()).size() == 0 && 
							g.getAdjSet(to.getKey()).size() == 0){
						g.removeVertices(from.getKey());
						g.removeVertices(to.getKey());
						break;
					}else{
						break;
					}
				}
			}	
			
		}
	}
	
	
	/**
	 * 鍖归厤绠楁硶
	 * @param h
	 * @param seq
	 * @param partmapping
	 * @param g
	 * @param missedges
	 * @param threshold
	 */
	public void simSearch(int h, SEQ seq, Vertex[] partmapping , Graph g,ArrayList<sedge> missedges, int threshold){
		Entry entry = seq.getSeq().get(h-1); 
		ArrayList<Integer> vexs = getCandiate(h,entry, partmapping, g,seq);
		Iterator<Integer> iter = vexs.iterator();
		while(iter.hasNext()){
			Vertex vex = g.getVexbyid(iter.next());
			int mismatch = getMissingBackedges(vex,seq,partmapping,h).size();
			if(mismatch + missedges.size() <= threshold && !contains(partmapping,vex)){
				missedges.addAll(getMissingBackedges(vex,seq,partmapping,h));
					partmapping[h - 1] =  vex;
				if(h < query.numVertices){
					simSearch(h + 1, seq, partmapping, g, missedges, threshold);
				}else{
					display( partmapping,missedges);
				}
			}
		}
		partmapping[h - 1] = null;
	}
		
	private boolean contains(Vertex[] partmapping, Vertex vex) {
		// TODO Auto-generated method stub
		for(int i = 0; i < partmapping.length; i++){
			if(partmapping[i] == vex){
				return true;
			}
		}
		return false;
	}

	private void display(Vertex[] partmapping,
			ArrayList<sedge> missedges) {
		// TODO Auto-generated method stub
		for(int i = 0; i < partmapping.length; i++){
			System.out.print(partmapping[i].getKey()+":"+partmapping[i].getLabel()+" ");
		}
		System.out.println();
	}

	/**
	 * 绉佹湁鏂规硶 鑾峰彇澶囬�闆嗗悎
	 * @param h
	 * @param entry
	 * @param seq 
	 * @param partmapping
	 * @param g
	 * @return
	 */
	private ArrayList<Integer> getCandiate(int h, Entry entry, Vertex[] partmapping,Graph g,SEQ seq){
		ArrayList<Integer> result = new ArrayList<Integer>();
		if(g.getLabels().contains(entry.getVex().getLabel())){
			ArrayList<Integer> list =  g.getRelation().get(entry.getVex().getLabel());
			if(h == 1){
				result.addAll(list);
			}
			if(h != 1){
				Iterator<Integer> iter2 = list.iterator();
				while(iter2.hasNext()){
					int id = iter2.next();
					if(g.getAdjSet(partmapping[indexof(entry.getSEdge().getTo(),seq)].getKey()).contains(id)){
						result.add(id);
					}
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * 绉佹湁鏂规硶 鑾峰彇缂哄け鐨勫洖杈� 
	 * @param vex
	 * @param seq
	 * @param graph
	 * @return
	 */
	private ArrayList<sedge> getMissingBackedges(Vertex vex, SEQ seq, Vertex[] partmapping, int h) {
		ArrayList<sedge> missingbackedges = new ArrayList<sedge>();
		if(seq.getSeq().get(h - 1).getNSEdge() != null){
		Iterator<sedge> edges = seq.getSeq().get(h - 1).getNSEdge().iterator();
		while(edges.hasNext()){
			sedge e = edges.next();
			String label = seq.getSeq().get(indexof(e.getTo(),seq)).getVex().getLabel();
			if(partmapping[indexof(e.getTo(),seq)].getLabel() != label){
				missingbackedges.add(e);
			}
		}
		}
		return missingbackedges;
	}

	
	private int indexof(int key, SEQ seq){
		int index = -1;
		Iterator<Entry> iter = seq.getSeq().iterator();
		while(iter.hasNext()){
			Entry entry = iter.next();
			index++;
			if(entry.getVex().getKey() == key){
				break;
			}
		}
		return index;
	}
	
	/**
	 * 涓诲姛鑳�	 */
	public void mainfunction(){
		long start = System.currentTimeMillis();
		ArrayList<SEQ> seqlist = new ArrayList<SEQ>(); 
		ArrayList<Integer> T = query.prim(1);
		ArrayList<Integer> TR = new ArrayList<Integer>(); 
		ArrayList<ArrayList<Integer>> QT = new ArrayList<ArrayList<Integer>>();
		QT.add(T);
		query.GeneQST(1, 1, T, TR , 2, QT);	
		Iterator<ArrayList<Integer>> iter0 = QT.iterator();
		while(iter0.hasNext()){
			ArrayList<Integer> qt = iter0.next();
			SEQ seq = new SEQ(query, qt);
			seqlist.add(seq);
		}
		
		Iterator<Graph> iter = this.orginbySplit.iterator();
		while(iter.hasNext()){
			Graph g = iter.next();
			Iterator<SEQ> iter3 = seqlist.iterator();
			while(iter3.hasNext()){
				SEQ seq = iter3.next();
				Vertex[] partmapping = new Vertex[query.numVertices];
				ArrayList<sedge> missedges = new ArrayList<sedge>();
				this.simSearch(1, seq, partmapping, g, missedges, 2);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("------------------------------");
		System.out.println("查询时间："+(end - start)+"ms");
	}
	
	public void mainfunction2(){
		long start = System.currentTimeMillis();
		ArrayList<SEQ> seqlist = new ArrayList<SEQ>(); 
		ArrayList<Integer> T = query.prim(1);
		ArrayList<Integer> TR = new ArrayList<Integer>(); 
		ArrayList<ArrayList<Integer>> QT = new ArrayList<ArrayList<Integer>>();
		QT.add(T);
		query.GeneQST(1, 1, T, TR , 2, QT);	
		Iterator<ArrayList<Integer>> iter0 = QT.iterator();
		while(iter0.hasNext()){
			ArrayList<Integer> qt = iter0.next();
			SEQ seq = new SEQ(query, qt);
			seqlist.add(seq);
		}
		
		
			Iterator<SEQ> iter3 = seqlist.iterator();
			while(iter3.hasNext()){
				SEQ seq = iter3.next();
				Vertex[] partmapping = new Vertex[query.numVertices];
				ArrayList<sedge> missedges = new ArrayList<sedge>();
				this.simSearch(1, seq, partmapping, this.getOrgin(), missedges, 2);
			}
		long end = System.currentTimeMillis();
		System.out.println("------------------------------");
		System.out.println("查询时间："+(end - start)+"ms");
	}
	
	
	
	public static void main(String[] args) {
		Graph g = new Graph();
		Graph query = new Graph();
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("A");
		labels.add("B");
		labels.add("C");
		labels.add("D");
		labels.add("E");
		labels.add("F");
		labels.add("G");
		Random r = new Random();
		for(int i = 1; i < 3001; i++){
			g.addVertice(i, labels.get(r.nextInt(7)));
		}
		 try {
             File file=new File("C:\\Users\\Administrator\\Desktop\\test\\3000.txt");
             if(file.isFile() && file.exists()){ 
                 InputStreamReader read = new InputStreamReader(
                 new FileInputStream(file));
                 BufferedReader bufferedReader = new BufferedReader(read);
                 String lineTxt = null;
                 while((lineTxt = bufferedReader.readLine()) != null){
                     String[] str = lineTxt.split("\t");
                     System.out.println(str[1]+":"+str[2]);
                     g.addEdge(Integer.parseInt(str[1])+1, Integer.parseInt(str[2])+1);
                 }
                 read.close();
     }else{
         System.out.println("找不到指定的文件");
     }
     } catch (Exception e) {
         System.out.println("读取文件内容出错");
         e.printStackTrace();
     }		
	g.printGraph();
		
	for(int i = 1; i < 6; i++){
		query.addVertice(i, labels.get(r.nextInt(3)));
	}
	try {
        File file=new File("C:\\Users\\Administrator\\Desktop\\test\\qe5.txt");
        if(file.isFile() && file.exists()){ 
            InputStreamReader read = new InputStreamReader(
            new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
                String[] str = lineTxt.split("\t");
                System.out.println(str[1]+":"+str[2]);
                query.addEdge(Integer.parseInt(str[1]), Integer.parseInt(str[2]));
            }
            read.close();
}else{
    System.out.println("找不到指定的文件");
}
} catch (Exception e) {
    System.out.println("读取文件内容出错");
    e.printStackTrace();
}		
				
		query.printGraph();
		
		
		
		GraphStream gs = new GraphStream(g, query);
//		Node from = new Node(9, "C");
//		Node to = new Node(10, "B");
////		gs.inseart(from, to, 5);
//		gs.delete(from, to, 10);
//		ArrayList<Graph> list = gs.orginbySplit;
//		Iterator<Graph> it = list.iterator();
//		while(it.hasNext()){
//			it.next().printGraph();
//			System.out.println("---------------------------------");
//		}
		 try {  
	            //文件生成路径  
	            PrintStream ps=new PrintStream("D:\\result.txt");  
	            System.setOut(ps);  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        }  
		gs.mainfunction2();
	}
}
