public class HullStack<T> {
	
	private Node<T> head;
	private int size = 0;
	
	private static class Node<T> {
		T item;
		Node<T> next;
	}
	
	public void push(T item) {
		Node<T> newNode = new Node<T>();
		newNode.item = item;
		newNode.next = head;
		head = newNode;
		size++;
	}
	
	public T pop() {
		if (head == null) return null;
		T item = head.item;
		head = head.next;
		size--;
		return item;
	}

	public int size() {	return size; }
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	@Override
	public String toString() {
		String s = "";
		Node<T> tmp = head;
		while (tmp != null) {
			s += tmp.item.toString() + " -> ";
			tmp = tmp.next;
		}
		return s;
	}

	public T peek() {
		if (head == null) return null;
		T item = head.item;
		return item;
	}
	
	public T sneeky_peek() {
		if (head.next == null) return null;
		T item = head.next.item;
		return item;
	}
	
	
	public static void main(String args[]) {
	}

}
