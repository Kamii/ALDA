import java.util.*;
import java.awt.Point;

public class ClosestPair{
	/**
	 *ArrayListen som innehåller koordinaterna
	 */
	private ArrayList<Point> plane = new ArrayList<Point>();

	/**
	 *Comparator i X-led
	 */
	private class CmpX implements Comparator<Point>{
		public int compare(Point p1, Point p2){
			if(p1.x==p2.x)
				return p1.y-p2.y;
			else
				return p1.x-p2.x;
		}
	}
	/**
	 *Comparator i Y-led
	 */
	private class CmpY implements Comparator<Point>{
		public int compare(Point p1, Point p2){
			if(p1.y==p2.y)
				return p1.x-p2.x;
			else
				return p1.y-p2.y;
		}
	}
	public void addPoint(Point p){
		plane.add(p);
	}
	public String toString(){
		String str= "";
		for(Point p : plane){
			str+= "X: " + p.getX() + " Y: " + p.getY()+ "\n";
		}
		return str;
	}
	public int getPlaneSize(){
		return plane.size();
	}

	/**
	 *Räknar ut avståndet mellan två punkter
	 */
	private double getDistance(Point p1, Point p2){
		return Math.sqrt( Math.pow((p1.getX()-p2.getX()), 2.0) + Math.pow((p1.getY()-p2.getY()), 2.0) );
	}

	/**
	 *Initeringsmetodför findClosestPair
	 */
	public double findClosestPair(){
		Collections.sort(plane, new CmpX());
		return findClosestPair(0, plane.size()-1);
	}

	/**
	 *Huvudmetod
	 */
	private double findClosestPair(int intervalStart, int intervalEnd){
		if(intervalEnd-intervalStart<=2){ 
			return findClosestInInterval(intervalStart, intervalEnd);
		} 
		else{
			int middle = ((intervalStart+intervalEnd)/2);
	//		double middleX = (plane.get(intervalStart).getX()+plane.get(intervalEnd).getX())/2;
			double middleX = (plane.get(middle).getX()+plane.get(middle+1).getX())/2;
			double left = findClosestPair(intervalStart, middle);
			double right = findClosestPair(middle+1, intervalEnd); 
			double smallest = (left < right ? left : right);
//			int leftBound=middle; 
//			int rightBound=middle+1; 

			ArrayList<Point> strip = new ArrayList<Point>();

			for(Point p : plane)
			{
				if(p.getX() > (middleX - smallest) && p.getX() < (middleX + smallest))
				{
					strip.add(p);
				}
			}

			Collections.sort(strip, new CmpY());

			for (int i=0; i<strip.size(); i++) 
				for (int j=i+1; j< strip.size(); j++) 
				{
					if ((strip.get(j).y - strip.get(i).y) > smallest) {  
						break; 
					} 
					else 
					{ 
						double tmp = getDistance(strip.get(i), strip.get(j));
						if(tmp < smallest)
							smallest = tmp;
					} 
				}

	//		System.out.println("DETTA ÄR SMALLEST:"+"\t"+smallest);

			return smallest;
		}

	}
	/**
	 *Brute force algoritm för basecase och testning av algoritmen
	 */
	public double findClosestInInterval(int intervalStart, int intervalEnd){
		double delta=Double.MAX_VALUE;
		for(int i=intervalStart; i<intervalEnd+1; i++){
			Point tmp = plane.get(i);
			for(int j = i+1; j<intervalEnd+1; j++ ){
				double newDelta=getDistance(tmp, plane.get(j));
				if(newDelta<delta){
					delta=newDelta;
				}
			}
		}
		return delta;
	}

	public double bruteForce()
	{
		double delta = Double.MAX_VALUE;
		for(int i=0; i<plane.size(); i++)
		{
			for(int j=1; j<plane.size(); j++)
			{
				double newDelta = getDistance(plane.get(i), plane.get(j));
				if(newDelta < delta)
					delta=newDelta;
			}
		}
		return delta;
	}

	public static void main(String[] args){
		ClosestPair cp = new ClosestPair();
			

//		cp.addPoint(new Point(2,6));
//		cp.addPoint(new Point(3,7));
//		cp.addPoint(new Point(4,5));
//		cp.addPoint(new Point(4,3));
//		cp.addPoint(new Point(5,6));
//		cp.addPoint(new Point(7,6));
//		cp.addPoint(new Point(8,3));
//		cp.addPoint(new Point(9,6));
//		cp.addPoint(new Point(11,8));
//		cp.addPoint(new Point(12,3));

//						cp.addPoint(new Point(12,40));
//						cp.addPoint(new Point(1,4));
//						cp.addPoint(new Point(52,7));
//						cp.addPoint(new Point(245,6754));
//						cp.addPoint(new Point(12245,25));
//						cp.addPoint(new Point(114,432));
//						cp.addPoint(new Point(746,2));
//						cp.addPoint(new Point(452,54));
//						cp.addPoint(new Point(1325,625));
//						cp.addPoint(new Point(7876,96));
//						cp.addPoint(new Point(12,22));
//						cp.addPoint(new Point(245,765));
//						cp.addPoint(new Point(13,657));
//						cp.addPoint(new Point(345,63));

		double fcii = cp.findClosestInInterval(0,cp.getPlaneSize()-1);
		System.out.println("\n----------");
		System.out.println("kontroll : "+fcii);

		double fcp = cp.findClosestPair();
		System.out.println("SVAR: "+fcp);
		System.out.println("\n----------");

		double fcii1 = cp.findClosestInInterval(0,cp.getPlaneSize()-1);
		System.out.println("\n----------");
		System.out.println("kontroll : "+fcii1);
		//Testfall: (1,10) (100,10) (90,300), (15,700) , (55,2000), (42,9000)
	}
}

