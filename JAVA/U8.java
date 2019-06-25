
import java.sql.Time;

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

class NoMoreElementsException extends Exception{
    private String retCd;
    private String msgDes;
    public NoMoreElementsException(){
        super();
    }
    public NoMoreElementsException(String message){
        super(message);
        msgDes=message;
    }
    public NoMoreElementsException(String retCd, String msgDes){
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

class TestDriver{
    public static void test() throws Exception{
        Vehicle f = new first();
        System.out.println(f.getInfo());
        Driver d = new Driver(f);
        System.out.println("If drivses the car 100km with 60 km/h, the needed time:");
        try{
        	System.out.println(d.drive(10,60));
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	System.out.println(f.getInfo());
        }
    }
}

class Driver{
    public Vehicle v;
    private Vehicle[] cars;
    private String[] classes;
    public Driver(){}
    public static int i;
    public Driver(Vehicle v){
        this.v=v;
        this.i=0;
        //cars[0]=v;
        //classes[0]=v.Klasse;
    }
    public void addCar(Vehicle v){
        i++;
        cars[i]=v;
        this.addClass(v);
    }
    public void addClass(Vehicle v){
        classes[i]=v.Klasse;
    }
    public Time drive(int km,int speed) throws NotEnoughFuelException{
        int a;
        double b;
        this.v.Rest-=km*this.v.Liter;
        if(this.v.Rest<=0){
            throw new NotEnoughFuelException("Not enough fuel!");
        }
        v.setRest(this.v.Rest);
        if(km/speed>0){
        	a=km/speed;
        }else{
        	a=0;
        }
        b=(km/(double)speed-a)*60;
        Time time=new Time(0);
        time.setHours(a);
        time.setMinutes((int)b);
        time.setSeconds(0);
        return time;
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
    public String Klasse;
    public String Marke;
    public double Groesse;
    public double Liter;
    public double Rest;
    public Vehicle(){}
    public Vehicle(String Klasse,String Marke,double Groesse,double Liter,double Rest){
        this.Klasse=Klasse;
        this.Marke=Marke;
        this.Groesse=Groesse;
        this.Liter=Liter;
        this.Rest=Rest;
    }
    public void setRest(double Rest){
    	this.Rest=Rest;
    }
    public abstract boolean isEmpty();
    public abstract boolean isFull();
    public String getInfo(){
    	return "Klass："+this.Klasse+" Marke: "+this.Marke+"\n"+"Tankgroesse: "+this.Groesse
    			+" Kilometer pro Liter: "+this.Liter+" Rest oil: "+this.Rest;   			
    }
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
        return Rest == Groesse;
    }
    public void setRest(double Rest){
    	this.Rest=Rest;
    }
    public String getInfo(){
    	return "Klass："+this.Klasse+" Marke: "+this.Marke+"\n"+"Tankgroesse: "+this.Groesse
    			+" Kilometer pro Liter: "+this.Liter+" Rest oil: "+this.Rest;   			
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
        return Rest== Groesse;
    }
}
class TestVehicle{
    public void test() throws Exception{
        Vehicle f=new first();
        Vehicle s=new second();
        Driver d=new Driver(f);
        System.out.println(d.drive(10,20));
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
class ListQueue <T> implements Queue <T>,Iterable<T> {
    private ListNode <T> head;
    private ListNode <T> current;
    public ListQueue(){
        this.head = null;
        this.current=null;
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
    public Iterator<T> iterator(){
        return new QueueIterator();
    }
    class QueueIterator implements Iterator<T>{
        ListNode<T> current;
        public QueueIterator(){
            current=head;
        }
        public boolean hasNext(){
            return current.next!=null;
        }
        public T next() throws NoMoreElementsException{
            if(hasNext())
                return current.next.element;
            else
                throw new NoMoreElementsException("There are no more elements!");
        }
    }
}

interface Iterator<T>{
    public boolean hasNext();
    public T next() throws NoMoreElementsException;
}

interface Iterable<E>{
    Iterator<E> iterator();
}

class TestListQueue{
    public static void test() throws Exception{
        ListQueue <String> str = new ListQueue <String>();
        
        System.out.println("Enqueue with element \"Hello\"");
        str.enqueue("Hello");
        System.out.println("Queue now: "+str.toString());

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

        System.out.println("The queue is: ");
        System.out.println(str.toString());
        System.out.println("Dequeue: "+str.dequeue());
        System.out.println("Dequeue: "+str.dequeue());
        System.out.println("Now head is: "+str.head());
    }
}

public class U8{
    public static void main(String args[]) throws Exception{
    	System.out.println("**************Aufgabe 1*************");
        TestDriver.test();
        System.out.println("**************Aufgabe 2*************");
        TestListQueue.test();
    	
    }
}
