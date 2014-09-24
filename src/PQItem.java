public class PQItem implements Comparable<PQItem> {
	
	int value;
	int arrayIndex; //the index of the array containing this element
	
	public PQItem(Integer val, int arrayIndex){
		this.value = val;
		this.arrayIndex = arrayIndex;
	}
	/**
	 * compares two PQItems
	 */
	public int compareTo(PQItem pq) {
		Integer valPQ = pq.value;
		Integer val = this.value;
		return val.compareTo(valPQ);
	}
	
	public String toString(){
		return this.value + "";
	}

}
