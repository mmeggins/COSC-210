//************************************************************************
//************************************************************************
public class PriorityQueue implements QueueInterface, java.io.Serializable {
	private Node firstNode;
	private Node lastNode;
	//********************************************************************
	public PriorityQueue() {
		firstNode = null;
		lastNode = null;
	}
	//********************************************************************
    //Method:       priorityEnqueue
    //Description:  This method enqueues items in a queue in prioritized order.
    //Parameters:   Comparable newEntry
    //Returns:      nothing
    //Calls:        compareTo()
    //              getData()
    //              getNextNode()
    //              setNextNode()
    //Globals:      none
	public void priorityEnqueue(Comparable newEntry) {
        Node newNode = new Node(newEntry);
        Node currentNode = firstNode;
		Node nodeBefore = null;

		while ((currentNode != null) && (newEntry.compareTo(currentNode.getData()) > 0)) {
			nodeBefore = currentNode;
			currentNode = currentNode.getNextNode();
		}
        
		if (isEmpty() || (nodeBefore == null)) {
			newNode.setNextNode(firstNode);
			firstNode = newNode;
		}
		else {
			Node nodeAfter = nodeBefore.getNextNode();
			newNode.setNextNode(nodeAfter);
			nodeBefore.setNextNode(newNode);
		}
    }
    //********************************************************************
    public void enqueue(Object newEntry) {
		Node newNode = new Node(newEntry, null);
		if (isEmpty())
			firstNode = newNode;
		else
			lastNode.setNextNode(newNode);
		lastNode = newNode;
	}
	//********************************************************************
	public Object dequeue() {
		Object front = null;
		if (!isEmpty()) {
			front = firstNode.getData();
			firstNode = firstNode.getNextNode();
			if (firstNode == null)
				lastNode = null;
		}
		return front;
	}
	//********************************************************************
	public Object getFront() {
		Object front = null;
		if (!isEmpty())
			front = firstNode.getData();
		return front;
	}
	//********************************************************************
	public boolean isEmpty() {
		return firstNode == null;
	}
	//********************************************************************
	public void clear() {
		firstNode = null;
		lastNode = null;
	}
	//********************************************************************
	//********************************************************************
	private class Node {
		private Object data;
		private Node next;

		private Node(Object dataPortion) {
			data = dataPortion;
			next = null;	
		}
		
		private Node(Object dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;	
		}
		
		private Object getData() {
			return data;
		}
		
		private void setData(Object newData) {
			data = newData;
		}
		
		private Node getNextNode() {
			return next;
		}
		
		private void setNextNode(Node nextNode) {
			next = nextNode;
		}
	}
	//********************************************************************
	//********************************************************************
}
//************************************************************************
//************************************************************************