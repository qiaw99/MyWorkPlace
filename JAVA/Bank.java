package qiaw99;

class Konto{
	protected double Kontostand;
	protected String Kontoinhaber;
	protected String Bankinstitut;
	public Konto(){}
	public Konto(double Kontostand,String Kontoinhaber,String Bankinstitut){
		this.Kontostand=Kontostand;
		this.Kontoinhaber=Kontoinhaber;
		this.Bankinstitut=Bankinstitut;
	}
	
	public String getBankinstitut(){
		return "Bankinstitut ist: "+this.Bankinstitut;
	}
	public String getKontostand(){
		return "Kontozustand: "+this.Kontostand;
	}
	public String getKontoinhaber(){
		return "Kontoinhaber ist: "+this.Kontoinhaber;
	}
	public void einzahlen(double betrag){
		if(betrag<0){
			System.out.println("Betrag kann nicht negativ sein.");
		}
		Kontostand+=betrag;
		System.out.println("Sie haben "+betrag+" Euro eingezahlt.");
		System.out.println("Jetzt ist der Kontostand: "+this.getKontostand());
	}
}
class Sparkonto extends Konto{
	public Sparkonto(double kontostand, String kontoinhaber, String bankinstitut){
		super(kontostand, kontoinhaber, bankinstitut);
	}
}
class Girokonto extends Konto{
	public Girokonto(double kontostand, String kontoinhaber, String bankinstitut){
		super(kontostand, kontoinhaber, bankinstitut);
	}	
	public void auszahlen(double betrag){
		if(betrag<0){
			System.out.println("Betrag kann nicht negativ sein.");
		}
		if(this.Kontostand-betrag>=0){
			System.out.println("Sie haben "+betrag+" Euro ausgezahlt");
			System.out.println("Jetzt ist der Kontostand: "+this.Kontostand);
		}else{
			System.out.println("Sie haben kein genug Geld!");
		}
	}
}

public class Bank {
	public static void main(String args[]){
		Konto k=new Girokonto(20.0,"Qianli","Sparkasse");
		k.einzahlen(10.0);
	}
}
