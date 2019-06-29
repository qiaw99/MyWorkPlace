//übungsgruppe: Qianli und Nazar
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
        Driver d = new Driver(f);
        System.out.println("*****First car*****");
        System.out.println("The attributs of first car before driving:");
        System.out.println(f.getInfo());
        System.out.println("If drivses the car 1000km with 60 km/h, the needed time:");
        try{
        	System.out.println(d.drive(1000,60));
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	System.out.println(f.getInfo());
        }
        System.out.println("Is first car empty?");
        System.out.println(d.v.isEmpty());
        System.out.println("Tank some oil, 10L for example");
        d.tank(10);
        System.out.println(d.v.getInfo());
        System.out.println("Is the first car full?");
        System.out.println(d.v.isFull());
        System.out.println("Then tank up:");
        d.tankUp();
        System.out.println(d.v.getInfo());
        Vehicle f2=new second();
        Driver d2=new Driver(f2); 
        System.out.println();
        System.out.println("*****Second car*****");
        System.out.println("The attributs of second car before driving:");
        System.out.println(f2.getInfo());
        System.out.println("If drivses the car 1000km with 100 km/h, the needed time:");
        try{
        	System.out.println(d2.drive(1000,100));
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	System.out.println(f2.getInfo());
        }
        System.out.println("Is second car empty?");
        System.out.println(d2.v.isEmpty());
        System.out.println("Tank some oil, 20L for example");
        d2.tank(20);
        System.out.println(d2.v.getInfo());
        System.out.println("Is the second car full?");
        System.out.println(d2.v.isFull());
        System.out.println("Then tank up.");
        d2.tankUp();
        System.out.println("Is now full?");
        System.out.println(f2.isFull());
        System.out.println(d2.v.getInfo());
    }
}

class Driver{
    public Vehicle v;
    private Vehicle[] cars;
    private String[] classes;
    public Driver(){}
    public int i;
    public Driver(Vehicle v){
        this.v=v;
        this.i=0;
        //cars[i]=v;
        //classes[i]=v.Klasse;
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
        this.v.Rest-=km/this.v.Liter;
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
    public double getRest(){
    	return this.Rest;
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
        System.out.println(d.drive(100,20));
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
    private ListNode <T> tail;
    private ListNode <T> current;
    
    //Knoten
    class ListNode<T>{
        private T element;
        private ListNode <T> next;
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
    
    public ListQueue(){
        this.head = null;
        this.current=null;
        this.tail=null;
    }
    public boolean empty() {
        return (this.head == null);
    }
    public void enqueue(T newElement){
        if(empty())
            head = tail = new ListNode <T> (newElement);
        else {
            tail= tail.next=new ListNode<T> (newElement);
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
    public Iterator<T> iterator(){		/*Iterator-Schnittstelle implementieren*/
        return new QueueIterator<T>();
    }
    class QueueIterator<E> implements Iterator<T>{	/*Innere Klasse*/
        ListNode <T> current;
        public QueueIterator(){
            current=head;
        }
        public boolean hasNext(){
            return current!=null;
        }
        public T next() throws NoMoreElementsException{
        	T temp = current.element;
        	if(temp==null){
        		throw new NoMoreElementsException("There are no more elements!");
        	}else{
        		current=current.next;
        		return temp;
        	}
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
        Iterator i=str.iterator();
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
        
        for(;i.hasNext();){
        	System.out.println("Queue: "+i.next());
        }
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
