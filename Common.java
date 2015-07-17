import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Common {
	public static ArrayList Difference(ArrayList o1, ArrayList o2){
		ArrayList result = new ArrayList();
		result.addAll(o1);
		result.removeAll(Intersection(o1, o2));
		return result;
	}
	
	
	public static ArrayList Intersection(ArrayList o1, ArrayList o2){
		ArrayList result = new ArrayList();
		Iterator it = o1.iterator();
		while(it.hasNext()){
			Object o = it.next();
			if(o2.contains(o)){
			result.add(o);	
			}
		}
		return result;
	}
	
	public static ArrayList Union(ArrayList o1, ArrayList o2){
		ArrayList result = new ArrayList();
		result.addAll(o1);
		result.addAll(o2);
		result.removeAll(Intersection(o1, o2));
		result.addAll(Intersection(o1, o2));
		return result;
		
	}
	
	public static void main(String[] args) {
		ArrayList list1 = new ArrayList();
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		ArrayList list2 = new ArrayList();
		list2.add(1);
		list2.add(3);
		list2.add(5);
		
		
		System.out.println(Union(list1, list2).toString());
		System.out.println(Intersection(list1, list2).toString());
		System.out.println(Difference(list1, list2).toString());
		
	}
	
	public static HashMap<String, ArrayList<Integer>> filter(
			HashMap<String, ArrayList<Integer>> editedg_i,
			ArrayList<Integer> visited) {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<Integer>> result = new HashMap<String, ArrayList<Integer>>();
		Iterator<String> it = editedg_i.keySet().iterator();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		while(it.hasNext()){
			String label = it.next();
			ArrayList list = editedg_i.get(label);
			list2 = Common.Difference(list, visited);
			result.put(label, list2);
		}
		return result;
	}
	
	public static ArrayList<Integer> findsmallest(HashMap<String,ArrayList<Integer>> map){
		ArrayList<Integer> list = null;
		int length = Integer.MAX_VALUE;
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String label = it.next();
			if(map.get(label).size() > 0 && length > map.get(label).size()){
				length = map.get(label).size();
				list = map.get(label);
			}
		}
		return list;
	}

	
	public static boolean isEmpty(HashMap<String,ArrayList<Integer>> map){
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String label = it.next();
			if(map.get(label).size() != 0){
				return false;
			}
			}
		return true;
		}

	public static int Min(ArrayList<Integer> list){
		int min = Integer.MAX_VALUE;
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()){
			int temp = it.next();
			if(temp < min){
				min = temp;
			}
		}
		return min;
	}

}
