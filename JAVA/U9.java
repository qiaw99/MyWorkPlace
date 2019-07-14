/*
Übungsgruppe: Qianli und Nazar
*/
class EmptyQueueException extends Exception{
    private String retCd;
    private String msgDes;
    public EmptyQueueException(){
        super();
    }
    public EmptyQueueException(String message){
        super(message);
        msgDes=message;
    }
    public EmptyQueueException(String retCd, String msgDes){
        super();
        this.retCd=retCd;
        this.msgDes=msgDes;
    }
    public String getRetCd(){
        return this.retCd;
    }
    public String getMsgDes(){
        return this.msgDes;
    }
}

class FullQueueException extends Exception{
    private String retCd;
    private String msgDes;
    public FullQueueException(){
        super();
    }
    public FullQueueException(String message){
        super(message);
        msgDes=message;
    }
    public FullQueueException(String retCd, String msgDes){
        super();
        this.retCd=retCd;
        this.msgDes=msgDes;
    }
    public String getRetCd(){
        return this.retCd;
    }
    public String getMsgDes(){
        return this.msgDes;
    }
}

interface Queue<E>{
	public void enqueue(E element)throws FullQueueException;
	public E dequeue() throws EmptyQueueException;
	public E first() throws EmptyQueueException;
	public boolean empty();
	public String toString();
}

interface Iterable<E>{
    Iterator<E> iterator();
}

interface Iterator<T>{
    public boolean hasNext();
    public T next();
}

class simpleQueue<E> implements Queue<E>{
	private int head;
	private int tail;
	private int current;
	private E[] queue;
	
	public simpleQueue(){
		this((E[]) new Object[10]);		/*assert the length of queue is 10*/
	}
	public simpleQueue(E[] queue){
		head=tail=0;
		this.queue=queue;
	}
	public boolean empty(){
		return head==tail;
	}
	public boolean full(){
		return ((tail==queue.length-1)&&(head==0))||(head==(tail+1));
	}
	public void enqueue(E elem) throws FullQueueException{
		if(!full()){
			queue[tail]=elem;
			if(tail==(queue.length-1)){
				tail=0;
			}else{
				tail++;
			}
		}else{
			throw new FullQueueException("There is no space in the queue to enqueue!");
		}
	}
	public E dequeue() throws EmptyQueueException{
		if(!empty()){
			E elem=queue[head];
			if(head==(queue.length-1)){
				head=0;
			}else{
				head++;
			}
			return elem;
		}else{
			throw new EmptyQueueException("There is no element in the queue to dequeue!");
		}
	}
	public E first() throws EmptyQueueException{
		if(empty()){
			throw new EmptyQueueException("There is no element in the queue!");
		}else{
			E elem = queue[head];
			return elem;
		}
	}
	public String toString(){
		current=this.head;
		String s="";
		if(head>tail){
			while(current<queue.length-1){
				s+=queue[current];
				current++;
			}
			s+=queue[current];
			current=0;
			while(current<=tail){
				s+=queue[current];
			}
			return s;
		}else{
			while(current<tail){
				s=s+queue[current]+" ";
				current++;
			}
			return s;
		}	
	}
	private class QueueIterator<T> implements Iterator<E>{	/*Innere Klasse*/
		int current;
		public QueueIterator(){
		    current=head;
		}
		public boolean hasNext(){
		    return queue[current]!=null;
		}
		public E next(){
			E temp = queue[current+1];
			if(current<queue.length-1)
				current++;
			else{
				current=0;
			}
			return temp;

		}
	}
    public Iterator<E> iterator(){		/*Iterator-Schnittstelle implementieren*/
        return new QueueIterator<E>();
    }
}

class TestArrayQueue {
	public static void test() throws Exception{
		simpleQueue <String> str = new simpleQueue <String>();
		Iterator i=str.iterator();
		System.out.println("Enqueue with element \"1\"");
		str.enqueue("1");
		System.out.println("Queue now: "+str.toString());

		System.out.println("Enqueue with element \"2\"");
		str.enqueue("2");
		System.out.println("Queue now: "+str.toString());

		System.out.println("Enqueue with element \"3\"");
		str.enqueue("3");
		System.out.println("Queue now: "+str.toString());

		System.out.println("Enqueue with element \"4\"");
		str.enqueue("4");
		System.out.println("Queue now: "+str.toString());

		System.out.println("Enqueue with element \"5\"");
		str.enqueue("5");
		System.out.println("Queue now: "+str.toString());

		System.out.println("Enqueue with element \"6\"");
		str.enqueue("6");
		System.out.println("Queue now: "+str.toString());
		System.out.println("See the first element");
		System.out.println(str.first());
		System.out.println("What about dequeue?");
		System.out.println(str.dequeue());
		System.out.println("The queue now?");
		System.out.println(str.toString());
		System.out.println("What about dequeue?");
		System.out.println(str.dequeue());
		System.out.println("The queue now?");
		System.out.println(str.toString());
		System.out.println("The result using Iterator:");        
		for(;i.hasNext();){
		    System.out.println("Has next element?");
		    System.out.println(i.hasNext());
		    System.out.println("The element is: "+i.next());
		}
	}
}

class PriorityQueue <P extends Comparable<P>, Data>{
	private int num;
	private Node wurzel;
	private Object [] heap;
	
	public PriorityQueue(){
		num=0;
		wurzel=null;
		heap=new Object[10];
		heap[0]=num;
	}
	
	private int left(int pos){
		return 2*pos;
	}
	
	private int right(int pos){
		return 2*pos+1;
	}
	
	private int heapsize(Object [] heap){
		return (int)heap[0];
	}
	
	public void dec(Object [] heap){
		heap[0]=heapsize(heap)-1;
	}
	
	private void maxHeapify(Object [] heap,int pos){
		int leftT=left(pos);
		int rightT=right(pos);
		int biggest;
		if(leftT<=heapsize(heap) && (((Node)heap[leftT]).priority.compareTo(((Node)heap[pos]).priority))>0){
			biggest=leftT;
		}else{
			biggest=pos;
		}
		if(rightT<=heapsize(heap) && (((Node)heap[rightT]).priority.compareTo(((Node)heap[biggest]).priority))>0){
			biggest=rightT;
		}
		if(biggest!=pos){
			Node temp;
			temp=(Node)heap[pos];
			heap[pos]=heap[biggest];
			heap[biggest]=temp;
			maxHeapify(heap,biggest);
		}
	}
	
	public void buildPriorityQueue(Object[]heap){
		for(int i=heapsize(heap)/2;i>0;i--){
			maxHeapify(heap,i);
		}
	}
	
	public boolean empty(){
		return wurzel==null;
	}
	
	public Data dequeue() throws EmptyQueueException{
		if(!empty()){
			Data temp=((Node)heap[1]).data;
			heap[1]=heap[heapsize(heap)];
			buildPriorityQueue(heap);
			num--;
			heap[0]=num;
			return temp;
		}else{
			throw new EmptyQueueException("There is no element in the heap!");
		}
	}
	
	public Data highest() throws EmptyQueueException{
		if(!empty()){
			Data temp=((Node)heap[1]).data;
			return temp;
		}else{
			throw new EmptyQueueException("There is no element in the heap!");
		}
	}
	
	public void enqueue(P priority, Data data){
		if(wurzel==null){
			wurzel=new Node(data,priority);
			heap[1]=wurzel;
			num++;
			heap[0]=num;
		}else{
			Node temp=new Node(data,priority);
			heap[(heapsize(heap))+1]=temp;
			buildPriorityQueue(heap);
			num++;
			heap[0]=num;
		}
	}
	
	public void getInfo(){
		String s="";
		System.out.println("There are "+num+" elements in the heap.");
		for(int i=1;i<=num;i++){
			System.out.println("Data: "+((Node)heap[i]).data+" Priority: "+((Node)heap[i]).priority);
		}
	}
	
	private class Node{
		private Data data;
		private P priority;
		public Node(Data data,P priority){
			this.data=data;
			this.priority=priority;
		}
	}
}

class SimulateMessageTraffic{
	public static void test() throws Exception{
		PriorityQueue<Integer,String> queue=new PriorityQueue<Integer,String>();
		System.out.println("Is the heap empty?");
		System.out.println(queue.empty());
		System.out.println("enqueue: priority: 5 , data: \"1\"");
		queue.enqueue(5,"1");
		System.out.println("enqueue: priority: 3 , data: \"2\"");
		queue.enqueue(3,"2");
		System.out.println("enqueue: priority: 6 , data: \"6\"");
		queue.enqueue(6,"6");
		System.out.println("enqueue: priority: 1 , data: \"4\"");
		queue.enqueue(1,"4");
		System.out.println();
		System.out.println("The information of heap structure:");
		queue.getInfo();
		System.out.println();
		System.out.println("Highest priority?");
		System.out.println(queue.highest());
		System.out.println("Dequeue?");
		System.out.println(queue.dequeue());
		queue.getInfo();
	}
}

public class U9{
	public static void main(String args[]) throws Exception{
		System.out.println("********************Aufgabe 1********************");
		SimulateMessageTraffic.test();
		System.out.println("********************Aufgabe 2********************");
		TestArrayQueue.test();
	}
}



