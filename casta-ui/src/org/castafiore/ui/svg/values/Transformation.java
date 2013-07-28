package org.castafiore.ui.svg.values;

import java.awt.Point;
import java.io.Serializable;

public class Transformation implements Serializable{
	
	private String s;
	
	private Transformation (){
		
	}
	
	public static Transformation Matrix(int a, int b, int c, int d, int e, int f){
		Transformation t = new Transformation();
		t.s =
		 "matrix(" + a + "," + b + "," + c + "," + d + "," + e + "," + f + ")";
		
		return t;
	}
	
	public static Transformation Translate(int x){
		Transformation t = new Transformation();
		t.s ="translate(" + x + ")";
		 
		
		return t;
	}
	
	public static Transformation Translate(int x, int y){
		Transformation t = new Transformation();
		t.s ="translate(" + x + "," + y +")";
		 
		
		return t;
	}
	
	public static Transformation Scale(int x){
		Transformation t = new Transformation();
		t.s ="scale(" + x + ")";
		 
		
		return t;
	}
	
	public static Transformation Scale(int x, int y){
		Transformation t = new Transformation();
		t.s ="scale(" + x + "," + y +")";
		 
		
		return t;
	}
	
	public static Transformation Rotate(int x){
		Transformation t = new Transformation();
		t.s ="rotate(" + x + ")";
		 
		
		return t;
	}
	
	public static Transformation Rotate(int x, Point origin){
		Transformation t = new Transformation();
		t.s ="rotate(" + x + "," + origin.getX() + "," + origin.getY() + ")";
		 
		
		return t;
	}
	
	public static Transformation valueOf(String s){
		Transformation t = new Transformation();
		t.s =s;
		 
		
		return t;
	}

	@Override
	public String toString() {
		return s;
	}
	
	

}
