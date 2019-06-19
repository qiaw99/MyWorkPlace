import java.sql.Time;
import java.util.Iterator;

class NotEnoughFuelException extends Exception{
    private String retCd;	
    private String msgDes;	
    public NotEnoughFuelException(){
        super();
    }
    public NotEnoughFuelException(String message){
        super(message);
        msgDes=message;
    }
    public NotEnoughFuelException(String retCd, String msgDes){
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

class NotEnoughCapacityException extends Exception{
    private String retCd;	
    private String msgDes;	
    public NotEnoughCapacityException(){
        super();
    }
    public NotEnoughCapacityException(String message){
        super(message);
        msgDes=message;
    }
    public NotEnoughCapacityException(String retCd, String msgDes){
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

class Driver <T>{
    private Driver<T> driver;
    private Vehicle v;
    //private Vehicle[] cars;
    public Time drive(int km,int speed) throws NotEnoughFuelException{
        if(v.isEmpty()){
            throw new NotEnoughFuelException("Not enough fuel!");
        }
        v.Rest-=km/(double)speed*v.liter;
        return new Time(km/speed);
    }
    public int tankUp(){
        v.Rest=v.Groesse;
        return (int)v.Rest;
    }
    public void tank(int liter)throws NotEnoughCapacityException{
        if(v.Rest+liter>v.Groesse){
            throw new NotEnoughCapacityException("Too much fuel!");
        }
        v.Rest=v.Rest+liter;
    }
}

abstract class Vehicle{
    public int Klasse;
    public String Marke;
    public double Groesse;
    public double Liter;
    public double Rest;
    public Vehicle(){}
    public Vehicle(int Klasse,String Marke,double Groesse,double Liter,double Rest){
        this.Klasse=Klasse;
        this.Marke=Marke;
        this.Groesse=Groesse;
        this.Liter=Liter;
        this.Rest=Rest;
    }
    public abstract boolean isEmpty();
    public abstract boolean isFull();
}

class first extends Vehicle{
    private String Klasse;
    private String Marke;
    private double Groesse;
    private double Liter;
    private double Rest;
    public first(){
        this.Klasse="B";
        this.Marke="Ford";
        this.Groesse=200.0;
        this.Liter=12.0;
        this.Rest=200;
    }
    public boolean isEmpty(){
        return Rest==0.0;
    }
    public boolean isFull(){
        if (Rest== Groesse){    // 1 -> Full
            return 1;
        }else{                  // 0 -> not Full
            return 0;
        }
    }
}

class second extends Vehicle{
    private String Klasse;
    private String Marke;
    private double Groesse;
    private double Liter;
    private double Rest;
    public second(){
        this.Klasse="B";
        this.Marke="BMW";
        this.Groesse=250.0;
        this.Liter=15.0;
        this.Rest=245;
    }
    public boolean isEmpty(){
        return Rest==0.0;
    }
    public boolean isFull(){
        if (Rest== Groesse){    // 1 -> Full
            return 1;
        }else{                  // 0 -> not Full
            return 0;
        }
    }
}
class TestVehicle{
    public void test(){
        first f=new first();
        second s=new second();
        
    }
}

//Knoten
class ListNode<T>{
    public T element;
    public ListNode <T> next;
    public ListNode(){
        this(null);
    }
    public ListNode(T element){
        this.element=element;
    }
    public ListNode(T element,ListNode<T> next){
        this.element=element;
        this.next=next;
    }
}

interface Queue<T>{
    public void enqueue(T element);
    public T dequeue() throws EmptyQueueException;
    public T head() throws EmptyQueueException;
    public boolean empty();
    public String toString();
}

//Warteschlange implementieren
class ListQueue <T> implements Queue <T> {
    private ListNode <T> head;
    private ListNode <T> current;
    public ListQueue(){
        this.head = null;
    }
    public boolean empty() {
        return (this.head == null);
    }
    public void enqueue(T newElement){
        if(empty())
            head = new ListNode <T> (newElement);
        else {
			current = head;	
            while (current.next != null) 
				current = current.next;
			current.next = new ListNode <T> (newElement);
		}
    }
    public T dequeue() throws EmptyQueueException {
        if (empty())
            throw new EmptyQueueException ("List is empty. Nothing to dequeue!");
        T element = head.element;
        head = head.next;
        return element;
    }
    public T head() throws EmptyQueueException{
        if(empty())
            throw new EmptyQueueException("List is empty. There is no head!");
        return head.element;
    }
    public String toString(){	
        this.current=this.head;
        String s = "";
        while (this.current != null) {
            s = s + current.element + " ";
            this.current = this.current.next;
        }
        return s;
    }
}

interface Iterator<T>{
    public boolean hasNext();
    public T next();
    class QueueIterator implements Iterable<QueueIterator>{
        public Iterator<QueueIteratot> iterator(){
            return new MyIterator();
        }
        class MyIterator implements Iterator<QueueIterator>{
            ListQueue <String> str = new ListQueue <String>();
            ListQueue <String> st=str;
            public boolean hasNext(){
                return;
            }
            public T next(){
                return ;
            }
        }
    }
}

interface Iterable<E>{
    Iterator<E> iterator();
}

class TestListQueue throws Exception{
    public static void test(){
        ListQueue <String> str = new ListQueue <String>();
        str.enqueue("Hello");
        str.enqueue("1");
        str.enqueue("2");
        str.enqueue("3");
        str.enqueue("4");
        str.enqueue("5");
        str.enqueue("6");
        System.out.println("Here is the queue: ");
        System.out.println(str.toSting());
        System.out.println("Dequeue: "+str.dequeue());
        System.out.println("Dequeue: "+str.dequeue());
    }
}
public class U8{
    public static void main(String args[]) throws Exception{
        TestListQueue.test();
    }
}



