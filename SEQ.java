import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

class sedge{
		private int from;
		private int to;
		
		//UPDATE 2015/6/5
		public int getFrom() {
			return from;
		}
		public void setFrom(int from) {
			this.from = from;
		}
		public int getTo() {
			return to;
		}
		public void setTo(int to) {
			this.to = to;
		}
	}
class Entry{
		private Vertex vex;
		private sedge SEdge;
		private ArrayList<sedge> NSEdge;
		public Vertex getVex() {
			return vex;
		}
		public void setVex(Vertex vex) {
			this.vex = vex;
		}
		public sedge getSEdge() {
			return SEdge;
		}
		public void setSEdge(sedge sEdge) {
			SEdge = sEdge;
		}
		public ArrayList<sedge> getNSEdge() {
			return NSEdge;
		}
		public void setNSEdge(ArrayList<sedge> nSEdge) {
			NSEdge = nSEdge;
		}
	}
	
public class SEQ{
		private ArrayList<Entry> seq = new ArrayList<Entry>();

		public ArrayList<Entry> getSeq() {
			return seq;
		}

		public void setSeq(ArrayList<Entry> seq) {
			this.seq = seq;
		} 
		
		SEQ(){
			
		}
		SEQ(Graph g, ArrayList<Integer> QT){
			
			//UPDATE 2015/6/5
				ArrayList<Integer> existed = new ArrayList<Integer>();
			 
				Entry entry = new Entry();
				entry.setVex(g.getVexbyid(1));
				seq.add(entry);
				existed.add(1);
				
			Iterator<Integer> iter = QT.iterator();
			while(iter.hasNext()){
				int edgeid = iter.next();
				Edge e = g.findedgebyid(edgeid);
				sedge spanedge = new sedge();
				Entry entry1 = new Entry();
				if(!existed.contains(e.getDest())){
					entry1.setVex(g.getVexbyid(e.getDest()));
					spanedge.setFrom(e.getDest());
					spanedge.setTo(e.getHead());
					entry1.setSEdge(spanedge);
					seq.add(entry1);
					existed.add(e.getDest());
				}else{
					entry1.setVex(g.getVexbyid(e.getHead()));
					spanedge.setFrom(e.getHead());
					spanedge.setTo(e.getDest());
					entry1.setSEdge(spanedge);
					seq.add(entry1);
					existed.add(e.getHead());
				}
			}
			
			ArrayList<Integer> exclude = Common.Difference(g.getEdges(), QT);
			Iterator<Integer> iter3 = exclude.iterator();
			while(iter3.hasNext()){
				int edgeid = iter3.next();
				Edge e = g.findedgebyid(edgeid);
				sedge backwardedge = new sedge();
				if(existed.indexOf(e.getDest()) > existed.indexOf(e.getHead())){
					if(seq.get(existed.indexOf(e.getDest())).getNSEdge() == null){
						ArrayList<sedge> nSedge = new ArrayList<sedge>();
						backwardedge.setFrom(e.getDest());
						backwardedge.setTo(e.getHead());
						nSedge.add(backwardedge);
						seq.get(existed.indexOf(e.getDest())).setNSEdge(nSedge);
					}else{
						backwardedge.setFrom(e.getDest());
						backwardedge.setTo(e.getHead());
						seq.get(existed.indexOf(e.getDest())).getNSEdge().add(backwardedge);
					}
				}else{
					if(seq.get(existed.indexOf(e.getHead())).getNSEdge() == null){
						ArrayList<sedge> nSedge = new ArrayList<sedge>();
						backwardedge.setFrom(e.getHead());
						backwardedge.setTo(e.getDest());
						nSedge.add(backwardedge);
						seq.get(existed.indexOf(e.getHead())).setNSEdge(nSedge);
					}else{
						backwardedge.setFrom(e.getHead());
						backwardedge.setTo(e.getDest());
						seq.get(existed.indexOf(e.getHead())).getNSEdge().add(backwardedge);
					}
				}

			}
						
		}
	}
