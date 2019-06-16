package qiaw99;
import java.util.Random;
import java.util.Scanner;

//Aufgabe 4
class Circle{		
	private double x;
	private double y;
	private double radius;
	public Circle(double x,double y,double radius){
		this.x=x;
		this.y=y;
		this.radius=radius;
	}
	public double getArea(){
		return Math.PI * Math.pow(radius,2);
	}
	public Circle(){
		this(0,0,10);
	}
	public Circle clone(){
		return new Circle(this.x,this.y,this.radius);
	}
	public int compareTo(Circle c){
		if(this.getArea()<c.getArea()){
			return -1;
		}else if(this.getArea()==c.getArea()){
			return 0;
		}else{
			return 1;
		}
	}
	public boolean contains(Circle c){
		if(Math.sqrt(Math.pow(this.x-c.x,2)+Math.pow(this.y-c.y,2))+c.radius<=this.radius){
			return true;
		}else{
			return false;
		}
	}
	public boolean overlaps(Circle c){
		if(Math.sqrt(Math.pow(this.x-c.x,2)+Math.pow(this.y-c.y,2))>c.radius+this.radius){
			return false;
		}else{
			return true;
		}
	}
	public static Rectangle smallestBoundingRectangle(Circle [] circles){
		double temp1=circles[0].x-circles[0].radius;
		double temp2=circles[0].x+circles[0].radius;
		double temp3=circles[0].y-circles[0].radius;
		double temp4=circles[0].y+circles[0].radius;
		for(Circle c:circles){		//smallst x-coorinate
			if(c.x-c.radius<temp1){
				temp1=c.x-c.radius;
			}
		}
		for(Circle c:circles){	//biggest x-coorinate
			if(c.x+c.radius>temp2){
				temp2=c.x+c.radius;
			}
		}
		for(Circle c:circles){	//smallst y-coorinate
			if(c.y-c.radius<temp3){
				temp3=c.y-c.radius;
			}
		}
		for(Circle c:circles){	//biggest y-coorinate
			if(c.y+c.radius>temp4){
				temp4=c.y+c.radius;
			}
		}
		return new Rectangle(temp1,temp3,temp2-temp1,temp4-temp3);
	}
}

class Rectangle{
	private double x;
	private double y;
	private double height;
	private double width;
	public Rectangle(double x,double y,double width,double height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	public String getInfo(){
		return "("+this.x+","+this.y+") with width "+this.width+" and height "+this.height;
	}
}
class TestCircle{
	public static void test(){
		System.out.println("The first circle is:(0,0,10)");
		System.out.println("The second circle is cloned c1");
		System.out.println("The third circle is:(0,0,20)");
		System.out.println("The forth circle is:(1,1,10)");
		Circle c1=new Circle(0,0,10);
		Circle c2=c1.clone();
		Circle c3=new Circle(0,0,20);
		Circle c4=new Circle(1,1,10);
		System.out.println("The result of c1 comparing to the cloned circle c2:");
		int i=c1.compareTo(c2);
		System.out.println(i);
		System.out.println("c3 contains c1?");
		System.out.println(c3.contains(c1));
		System.out.println("c4 overlaps with c1?");
		System.out.println(c4.overlaps(c1));
		Circle [] circles=new Circle []{c1,c3,c4};
		System.out.println("There are 3 circles in the list, c1 c3 c4!");
		System.out.println("Infos about the rectangle:");
		System.out.println(new Circle().smallestBoundingRectangle(circles).getInfo());
	}	
}

//Aufgabe 6
class ZInteger<A,B>{
	public final int first;		// a)
	public final int second;	//unmutable
	public ZInteger(Integer a,Integer b){	//e)
		this.first=a;
		this.second=b;
	}
	public ZInteger add(ZInteger z){	//b)
		return new ZInteger <A,B> (z.first+this.first,z.second+this.second).simplify();
	}
	public ZInteger sub(ZInteger z){
		return new ZInteger <A,B> (this.first+z.second,this.second+z.first).simplify();
	}
	public ZInteger mul(ZInteger z){
		return new ZInteger <A,B> (this.second*z.first+this.first*z.second,this.second*z.second+this.first*z.first).simplify(); 
	}
	public ZInteger simplify(){			//c)
		int a=this.first;
		int b=this.second;
		if(a>=0){
			while(a>0){
				a-=1;
				b-=1;
			}
		}else{
			while(a<0){
				a+=1;
				b+=1;
			}
		}
		while(a>0){
			a-=1;
			b+=1;
		}
		return new ZInteger(a,b);
	}
	public boolean equal(ZInteger z){		//d)
		if(this.first+this.second==z.first+z.second){
			return true;
		}else{
			return false;
		}
	}
	public boolean smaller(ZInteger z){
		if(this.second-this.first<z.second-z.first){
			return true;
		}else{
			return false;
		}
	}
	public boolean smallerOrEqual(ZInteger z){
		if(this.smaller(z) || this.equal(z)){
			return true;
		}else{
			return false;
		}
	}
	public boolean bigger(ZInteger z){
		if(this.second-this.first>z.second-z.first){
			return true;
		}else{
			return false;
		}
	}
	public boolean biggerOrEqual(ZInteger z){
		if(this.equal(z) || this.bigger(z)){
			return true;
		}else{
			return false;
		}
	}
	public String toString(){	//f)
		return "("+this.first+","+this.second+")";
	}
}

class TestZInteger{		//g)
	public TestZInteger(){}
	public static void test(){
		System.out.println("Test von (2,4) und (3,9)");
		ZInteger z1=new ZInteger(2,4);
		ZInteger z2=new ZInteger(3,9);
		System.out.println("Addition:");
		System.out.println(z1.add(z2).toString());
		System.out.println("Substraktion:");
		System.out.println(z1.sub(z2).toString());
		System.out.println("Multiplikation:");
		System.out.println(z1.mul(z2).toString());
		System.out.println("Comparing with 2 ZIntegers:");
		System.out.println("Bigger?");
		System.out.println(z1.bigger(z2));
		System.out.println("Bigger or equal?");
		System.out.println(z1.biggerOrEqual(z2));
		System.out.println("Equal?");
		System.out.println(z1.equal(z2));
		System.out.println("Smaller?");
		System.out.println(z1.smaller(z2));
		System.out.println("Smaller or equal?");
		System.out.println(z1.smallerOrEqual(z2));
	}
}

public class test{
	public static final Random gen= new Random();
	public static final int ROUNDS =10000;
	public static final Scanner scan=new Scanner(System.in);
	
	public static int mul(int a, int b){	//Aufgabe 1
		//Die Komplexitaet ist: log2(n)
		int res=b;		//c1
		if(a==1){
			return b;
		}else{		
			int temp1=a;
			int temp2=b;
			while(a>0){		//log n
				a=a>>1;
				b=b<<1;
				if(a%2!=0){
					res+=b;
				}
			}
			if(temp1%2==0){
				return res-temp2;
			}else{
				return res;
			}
		}
	}
	
	public static int chooseAnotherDoor(int door1, int door2){
		int res;
		do
			res=gen.nextInt(3);
		while(res==door1 || res==door2);
		return res;
	}
	
	public static void test(){		//Aufgabe 2
		int switching=0;
		int staying=0;
		int a=0;	//switching
		for(int j=0;j<ROUNDS;j++){
			int help=gen.nextInt(2);
			int prize=gen.nextInt(3);
			int userChoice1=gen.nextInt(3);
			int hostChoice=chooseAnotherDoor(prize,userChoice1);
			int userChoice2=chooseAnotherDoor(userChoice1,hostChoice);
			if(userChoice1==prize)
				staying++;
			if(userChoice2==prize)
				switching++;
			if(help==1){	//switch
				if(userChoice2==prize){
					a++;
				}
			}	
			if(help==2){	//staying
				if(userChoice1==prize){
					a++;
				}	
			}
		}
		System.out.println("There are 10000 Rounds.");
		System.out.println(switching+" wins by always switching"+" with "+(double)switching/ROUNDS+"%");
		System.out.println(staying+" wins by never swithing"+" with "+(double)staying/ROUNDS+"%");
		System.out.println(staying+" wins by random switching"+" with "+(double)a/ROUNDS+"%");//?
	}
	
	public static void main(String args[]){
		System.out.println("*********Aufgabe 1*********");		
		System.out.println("Wie ist der Multiplikator?");
		int n=scan.nextInt();
		System.out.println("Wie ist der Multiplikand?");
		int m=scan.nextInt();
		System.out.println("Das Ergebnis: "+mul(n,m));
		System.out.println("*********Aufgabe 2*********");		
		test();		
		System.out.println("*********Aufgabe 4*********");
		TestCircle.test();
		System.out.println("*********Aufgabe 6*********");
		TestZInteger.test();		
	}
}
