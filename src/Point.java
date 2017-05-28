

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Point {

    private double V1 = 0;
    private double V2 = 0;
    private double V3 = 0;
    private double V4 = 0;
    private double V5 = 0;
    private double V6 = 0;
    private double V7 = 0;
    private double V8 = 0;
    private double V9 = 0;
    private double A1 = 0;
    private double A2 = 0;
    private double A3 = 0;
    private double A4 = 0;
    private double A5 = 0;
    private double A6 = 0;
    private double A7 = 0;
    private double A8 = 0;
    private double A9 = 0;
    private int cluster_number = 0;

    public Point(double V1,double A1, double V2, double A2, double V3, double A3, double V4, double A4,
    		double V5,double A5,double V6,double A6,double V7,double A7,double V8,double A8,double V9,double A9, int cluster_number)
    {
    	this.V1=V1;
    	this.A1=A1;
    	this.V2=V2;
    	this.A2=A2;
    	this.V3=V3;
    	this.A3=A3;
    	this.V4=V4;
    	this.A4=A4;
    	this.V5=V5;
    	this.A5=A5;
    	this.V6=V6;
    	this.A6=A6;
    	this.V7=V7;
    	this.A7=A7;
    	this.V8=V8;
    	this.A8=A8;
    	this.V9=V9;
    	this.A9=A9;
    	this.cluster_number=cluster_number;
             
                              
                   
    }    
   
    



	public double getV1() {
		return V1;
	}





	public void setV1(double v1) {
		V1 = v1;
	}





	public double getV2() {
		return V2;
	}





	public void setV2(double v2) {
		V2 = v2;
	}





	public double getV3() {
		return V3;
	}





	public void setV3(double v3) {
		V3 = v3;
	}





	public double getV4() {
		return V4;
	}





	public void setV4(double v4) {
		V4 = v4;
	}





	public double getV5() {
		return V5;
	}





	public void setV5(double v5) {
		V5 = v5;
	}





	public double getV6() {
		return V6;
	}





	public void setV6(double v6) {
		V6 = v6;
	}





	public double getV7() {
		return V7;
	}





	public void setV7(double v7) {
		V7 = v7;
	}





	public double getV8() {
		return V8;
	}





	public void setV8(double v8) {
		V8 = v8;
	}





	public double getV9() {
		return V9;
	}





	public void setV9(double v9) {
		V9 = v9;
	}





	public double getA1() {
		return A1;
	}





	public void setA1(double a1) {
		A1 = a1;
	}





	public double getA2() {
		return A2;
	}





	public void setA2(double a2) {
		A2 = a2;
	}





	public double getA3() {
		return A3;
	}





	public void setA3(double a3) {
		A3 = a3;
	}





	public double getA4() {
		return A4;
	}





	public void setA4(double a4) {
		A4 = a4;
	}





	public double getA5() {
		return A5;
	}





	public void setA5(double a5) {
		A5 = a5;
	}





	public double getA6() {
		return A6;
	}





	public void setA6(double a6) {
		A6 = a6;
	}





	public double getA7() {
		return A7;
	}





	public void setA7(double a7) {
		A7 = a7;
	}





	public double getA8() {
		return A8;
	}





	public void setA8(double a8) {
		A8 = a8;
	}





	public double getA9() {
		return A9;
	}





	public void setA9(double a9) {
		A9 = a9;
	}





	public int getCluster_number() {
		return cluster_number;
	}





	public void setCluster_number(int cluster_number) {
		this.cluster_number = cluster_number;
	}



	public void setCluster(int n) {
        this.cluster_number = n;
    }
    
    public int getCluster() {
        return this.cluster_number;
    }
    
    //Calculates the distance between two points.
    protected static double distance(Point object, Point object2) {
        return Math.sqrt(Math.pow((object2.getA1() - object.getA1()), 2)+ Math.pow((object2.getA2() - object.getA2()), 2)+ Math.pow((object2.getA3() - object.getA3()), 2)
        		+ Math.pow((object2.getA4() - object.getA4()), 2)+ Math.pow((object2.getA5() - object.getA5()), 2)+ Math.pow((object2.getA6() - object.getA6()), 2)
        		+ Math.pow((object2.getA7() - object.getA7()), 2)+ Math.pow((object2.getA8() - object.getA8()), 2)+ Math.pow((object2.getA9() - object.getA9()), 2)
        		+ Math.pow((object2.getV1() - object.getV1()), 2)+ Math.pow((object2.getV2() - object.getV2()), 2)+ Math.pow((object2.getV3() - object.getV3()), 2)
        		+ Math.pow((object2.getV4() - object.getV4()), 2)+ Math.pow((object2.getV5() - object.getV5()), 2)+ Math.pow((object2.getV6() - object.getV6()), 2)
        		+ Math.pow((object2.getV7() - object.getV7()), 2)+ Math.pow((object2.getV8() - object.getV8()), 2)+ Math.pow((object2.getV9() - object.getV9()), 2));
    }
     
}