/*
 * References:
 * How to use abstract classes:
 * http://docs.oracle.com/javase/tutorial/java/IandI/abstract.html
 * How to use compareTo:
 * http://www.tutorialspoint.com/java/java_string_compareto.htm
 */

import java.util.Arrays;

public class PriorityQ extends PQ {

	public PriorityQ(int k) {
		super(k);
		// TODO Auto-generated constructor stub
	}

	static private int parent(int index) {
		return (index - 1) / 2;
	}

	static private int left(int index) {
		return 2 * index + 1;
	}

	static private int right(int index) {
		return 2 * index + 2;
	}

	@Override
	public void insert(PQItem item) {
		this.heapArray[this.currentSize] = item;
		this.swapUp();
		this.currentSize++;

	}

	private void swap(int i1, int i2) {
		PQItem tmp = this.heapArray[i1];
		this.heapArray[i1] = this.heapArray[i2];
		this.heapArray[i2] = tmp;
	}

	private int numChildren(int index) {
		int num = 0;
		if (left(index) < this.currentSize) {
			num++;
		}// if
		if (right(index) < this.currentSize) {
			num++;
		}// if
		return num;
	}// numChildren

	private void swapUp() {
		int index = this.currentSize; //index of the element you want to swap up
		PQItem val = this.heapArray[this.currentSize];
		while (index >= 1) {
			int parent = parent(index);
			if (val.compareTo(this.heapArray[parent(index)]) < 0) {
				this.swap(index, parent(index));
				index = parent(index);
			} else {
				break;
			}// if/else
		}// while
	}// swapUp

	private void swapDown() {
		int index = 0;
		int smallerChildIndex = 0;
		PQItem val = this.heapArray[0];
		int numKids = numChildren(index);
		while (numChildren(index) != 0) {
			if (numChildren(index) == 1
					|| this.heapArray[left(index)]
							.compareTo(this.heapArray[right(index)]) <= 0) {
				smallerChildIndex = left(index);
			}// if
			else {
				smallerChildIndex = right(index);
			}
			int something = this.heapArray[smallerChildIndex]
					.compareTo(this.heapArray[index]);
			if (this.heapArray[smallerChildIndex]
					.compareTo(this.heapArray[index]) < 0) {
				swap(index, smallerChildIndex);
				index = smallerChildIndex;
			}// if -left/index
			else {
				break;
			}// else
		}// while
	}

	@Override
	public PQItem remove() {
		PQItem top = this.heapArray[0];
		this.heapArray[0] = this.heapArray[this.currentSize - 1];
		this.currentSize--;
		this.swapDown();

		return top;
	}

	public String toString() {
		return Arrays.toString(this.heapArray);
	}

	public static PQItem[] kWayMerge(PQItem[][] input) {
		int k = input.length;
		PriorityQ pq = new PriorityQ(k);
		int currentOutput = 0; // keep track of our place in output
		int arrayFrom;
		int totalVals = 0; // calculate total number of inputs
		int[] currents = new int[k]; // keep track of the current element in
		// each input array
		for (int j = 0; j < k; j++) {
			totalVals += input[j].length;
		}
		PQItem[] output = new PQItem[totalVals];

		for (int i = 0; i < k; i++) { // while there are items left in input
			pq.insert(input[i][currents[i]]); // put one element from each array
			// into the heap
		}
		Arrays.fill(currents, 1); // they all begin at the first element
		// current[i] points to the next element to be inserted into the heap

		while (!pq.isEmpty()) {
			// the first element in that heap is the smallest out of all of them
			// put that element in the output array (??)
			output[currentOutput] = pq.remove();
			// get another element from the array the smallest item came from
			// and put that in the heap
			arrayFrom = output[currentOutput++].arrayIndex;

			// if there are elements left in the array the removed element was
			// from
			if (currents[arrayFrom] < input[arrayFrom].length) {
				// insert an element from that array and update pointer
				pq.insert(input[arrayFrom][currents[arrayFrom]++]);

			}// if

		} // while

		return output;
	}

	public static void main(String[] args) {
		// Integer[] contents = {new Integer(8), new Integer(7), new Integer(4),
		// new Integer(10),
		// new Integer(15), new Integer(2), new Integer(0)};

		int[] contents = { 2, 1, 3, 4, 2, 16, 4 };
		PriorityQ pq = new PriorityQ(7);
		int i = 0;
		while (!pq.isFull()) {
			System.out.println("inserted: " + contents[i]);
			pq.insert(new PQItem(contents[i++], 3));
		}
		System.out.println(Arrays.toString(pq.heapArray));
		while (!pq.isEmpty()) {
			System.out.println("top: " + pq.top().toString());
			System.out.println("removed: " + pq.remove().toString());
		}

		int rows = 9;
		int cols = 7;
		PQItem[][] test = new PQItem[rows][cols];
		for (int k = 0; k < rows; k++) {
			for (int j = 0; j < cols; j++) {
				test[k][j] = new PQItem(k * j, k);
			}
		}

		System.out.println(Arrays.toString(kWayMerge(test)));

	}
}
