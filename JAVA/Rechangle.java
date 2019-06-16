package qiaw99;

class Rechangle{
	private int x;
	private int y;
	private int width;
	private int height;
	public Rechangle(){
		this.x=0;
		this.y=0;
		this.width=10;
		this.height=10;
	}
	public Rechangle(int x,int y, int width,int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	public int area(){
		return this.width*this.height;
	}
	public boolean identical(Rechangle r){
		if(this.x==r.x){
			if(this.y==r.y){
				if(this.width==r.width){
					if(this.height==r.height){
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	public int compareTo(Rechangle r){
		if(this.area()<r.area()){
			return -1;
		}else if(this.area()==r.area()){
			return 0;
		}else{
			return 1;
		}
	}
	public boolean contains(Rechangle r){
		if(this.x<=r.x && this.y<=r.y && this.x+this.width>=r.x+r.width && this.y+this.height>=r.y+r.height){
			return true;
		}else{
			return false;
		}
	}
	public boolean overlaps(Rechangle r){
		if(this.contains(r)){
			return true;
		}else if(r.y<=(this.y+this.height) && (r.x+r.width)>=this.x){
			return true;
		}else if(r.y<=(this.y+this.height) && r.x<=(this.x+this.width)){
			return true;
		}else if((r.x+r.width)>=this.x &&(r.y+r.height)>=this.y){
			return true;
		}else if(r.x<=(this.x+this.width) && (r.y+r.height)>=this.y){
			return true;
		}else{
			return false;
		}
	}
	public Rechangle section(Rechangle r){
		if(!this.overlaps(r)){
			return new Rechangle(0,0,0,0);
		}else{
			if(this.contains(r)){
				return r;
			}else{
				if(r.y<=(this.y+this.height) && (r.x+r.width)>=this.x){
					return new Rechangle(this.x,r.y,r.width-this.x,this.y+this.height-r.y);
				}else if(r.y<=(this.y+this.height) && r.x<=(this.x+this.width)){
					return new Rechangle(r.x,r.y,this.x+this.width-r.x,this.y+this.height-r.y);
				}else if((r.x+r.width)>=this.x &&(r.y+r.height)>=this.y){
					return new Rechangle();
				}else{
					return new Rechangle(this.x,this.y,r.x+r.width-this.x,r.y+r.height-this.y);
				}
			}
		}
	}
}

public class U1 {
	public static void main(String args[]){
	}
}
