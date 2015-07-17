import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;




public class Algorithm {
	
		
	/**
	 * 澧為噺瀛愬浘鍖归厤绠楁硶 
	 * @author victory
	 *   
	 */
	
	private ArrayList<edge> AE_set = new ArrayList<Algorithm.edge>(); //杈呭姪杈归泦鍚�	
	private class edge{
		//杈呭姪杈�		
		int v1;
		int v2;
		edge(int v1, int v2){
			this.v1 = v1;
			this.v2 = v2;
		}
		public int getV1() {
			return v1;
		}
		public void setV1(int v1) {
			this.v1 = v1;
		}
		public int getV2() {
			return v2;
		}
		public void setV2(int v2) {
			this.v2 = v2;
		}
		public String toString(){
			return "AE From "+ v1  +" To " + v2;
		}
	}
	
	
	/**
	 * 缁撴瀯鍓灊
	 * @param query
	 * @param g
	 * @param query_set
	 * @return graph g
	 */
	public Graph GSP(Graph query, Graph g){ 
		Graph result = new Graph();
		
		HashSet<String> query_set = query.getLabels();
		ArrayList<Integer> unvisited = new ArrayList<Integer>();
		Iterator<Vertex> it = g.getNodeTabel().iterator();
		while(it.hasNext()){
			Vertex vex = it.next();
			unvisited.add(vex.getKey());
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		
		while(!unvisited.isEmpty()){
				int vex = unvisited.get(0);
				queue.offer(vex);
				unvisited.remove((Integer)vex);
				if(query_set.contains(g.getLabelbyKey(vex))){
					result.addVertice(vex, g.getLabelbyKey(vex));
				}
				while(!queue.isEmpty()){
					int v = queue.poll();
					Iterator<Integer> it2 = g.getAdjSet(v).iterator();
					while(it2.hasNext()){
						int v2 = it2.next();
						if(unvisited.contains(v2)){
						unvisited.remove((Integer)v2);
						if(query_set.contains(g.getLabelbyKey(v2))){
							queue.offer(v2);
							result.addVertice(v2, g.getLabelbyKey(v2));
							result.addEdge(v, v2);
						}
					}
						}
				}	
		}
		
		return result;
	}
	
	
	
	/**
	 * 鏈�繎閭诲垽鏂�	 * @param g
	 * @param query
	 * @param edit_g
	 * @return 
	 */
	public Graph NNJudgement(Graph g, Graph query, Graph edit_g){
		
		//鍓灊鍥剧殑鏍囩瀵瑰簲鍏崇郴
		HashMap<String,ArrayList<Integer>> editedg_i = edit_g.getRelation();
		
		//鏌ヨ鍥剧殑NN绱㈠紩
		HashMap<String, HashSet<String>> query_NN = this.NNInit(query);
		
		
		Graph result = new Graph();
		//鍒濆鍖栫粨鏋滈泦
		Iterator<Vertex> it = edit_g.getNodeTabel().iterator();
		while(it.hasNext()){
			Vertex vex = it.next();
			result.addVertice(vex.getKey(), vex.getLabel());
		}
		
		
		//杈呭姪杈归泦鍚�		
		ArrayList<edge> result_AE = new ArrayList<edge>();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		Queue<Integer> queue = new LinkedList<Integer>();
		
		
		while(!Common.isEmpty(editedg_i)){
			ArrayList<Integer> list = Common.findsmallest(editedg_i);
			int vex = list.get(0);
				queue.offer(vex);
				while(!queue.isEmpty()){
					int vex2 = queue.poll();
					if(!visited.contains(vex2)){
						visited.add(vex2);
						HashSet<String> labels = query_NN.get(edit_g.getLabelbyKey(vex2));
						Iterator<String> it3 = labels.iterator();
						while(it3.hasNext()){
							String label = it3.next();
							if(g.getNearNode(vex2, label).size() == 0){ 
								break;
							}else{
							Iterator<NearNode> it4 = g.getNearNode(vex2, label).iterator();
							while(it4.hasNext()){
								NearNode node = it4.next();
								Vertex v = node.getVex();
								int dest = node.getDest();
								if(!visited.contains(v.getKey())){
									if(!queue.contains(v.getKey())){
									queue.offer(v.getKey());
									}
									result.addEdge(vex2, v.getKey());
									if(dest != 1){
										result_AE.add(new edge(vex2,v.getKey()));
									}
								}
							}
															
							}
						}
					}
					
				}
				editedg_i = Common.filter(editedg_i, visited);
			}
		this.AE_set = result_AE;
		return result;
	}

	/**
	 * 寤虹珛NN绱㈠紩
	 * @param query
	 * @return
	 */
	public HashMap<String, HashSet<String>> NNInit(Graph query){
		HashMap<String, HashSet<String>> NNIndex = new HashMap<String, HashSet<String>>();
		Iterator query_it = query.getLabels().iterator();
		while(query_it.hasNext()){
			String label = (String)query_it.next();
			NNIndex.put(label,query.getAdjSet(label));
		}
		return NNIndex;
	}

	
	/**
	 * 鍒嗗尯
	 * @param Graph g
	 */ 
	public ArrayList<Graph> spiltGraph(Graph g){
		ArrayList<Graph> orign = new ArrayList<Graph>();
		orign = g.getConnectedComponent();
		Iterator<edge> it1 = AE_set.iterator(); 
		Iterator<Graph> it = orign.iterator();
		while(it1.hasNext()){
			edge e = it1.next();
			while(it.hasNext()){
				Graph g1 = it.next();
				if(g1.containVertex(e.getV1()) && g1.containVertex(e.getV2())){
					g1.removeEdge(e.getV1(), e.getV2());
				}
				
			}
			it = orign.iterator();
		}
		return orign;
	}
	
	
	/**
	 * 鎬诲鐞嗙被
	 * @param g
	 * @param query
	 * @return
	 */
	public ArrayList<Graph> handler(Graph g, Graph query){
		Graph GSP = GSP(query,g);
		Graph NN_Graph = NNJudgement(g, query, GSP);
		ArrayList<Graph> Orign_Graph = spiltGraph(NN_Graph);
		return Orign_Graph;
	}
	
	
	public static void main(String[] args) {
		Graph g = new Graph();
		
		g.addVertice(1, "A");
		g.addVertice(2, "A");
		g.addVertice(3, "B");
		g.addVertice(4, "C");
		g.addVertice(5, "D");
		g.addVertice(6, "E");
		
		g.addEdge(1, 2);
		g.addEdge(1, 6);
		g.addEdge(2, 3);
		g.addEdge(2, 5);
		g.addEdge(2, 6);
		g.addEdge(2, 4);
		g.addEdge(5, 6);
		g.addEdge(4, 5);
		
//		g.printGraph();
		
		Graph query = new Graph();
		query.addVertice(1, "A");
		query.addVertice(2, "B");
		query.addVertice(3, "C");
		query.addVertice(4, "D");
		
		query.addEdge(1, 2);
		query.addEdge(2, 3);
		query.addEdge(3, 4);
		query.addEdge(4, 1);
		query.addEdge(1, 3);
//		query.printGraph();
		
				
		
		System.out.println("--------result-----------");
		
		Algorithm alg = new Algorithm();
		Iterator<Graph> it = alg.handler(g, query).iterator();
		 while(it.hasNext()){
			it.next().printGraph();
			System.out.println("------------------------------");
		}
		
	}
}
