import java.util.*;
import java.awt.Point;

/**
 * Klassen för Closest-Pair problem.
 * @author Leon Hennings
 * @author Kamyar Sajjadi
 */
public class ClosestPair{

	// ArrayList för samtliga punkter
	private ArrayList<Point> plane = new ArrayList<Point>();

	/**
	 * Comperator för att jämföra i X-led
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
	 * Comparator för att jämföra i Y-led
	 */
	private class CmpY implements Comparator<Point>{
		public int compare(Point p1, Point p2){
			if(p1.y==p2.y)
				return p1.x-p2.x;
			else
				return p1.y-p2.y;
		}
	}

	/**
	 * Metod för att lägga till punkter i arrayen
	 * @param p Som är en Point som ska läggas till i plane
	 */
	public void addPoint(Point p){
		plane.add(p);
	}

	/**
	 * toString för att få en bra överblick över listan.
	 * Denna metod är mest användbar när man ska debugga.
	 * @return str
	 */
	public String toString(){
		String str= "";
		for(Point p : plane){
			str+= "(" + p.x + p.y+ ")" + "\n";
		}
		return str;
	}

	/**
	 * Metod för att få storleken av listan. 
	 * @return the size of the array
	 */
	public int getPlaneSize(){
		return plane.size();
	}

	/**
	 * Räknar ut avståndet mellan två punkter.
	 * @param p1 En av punkterna
	 * @param p2 den andra punkten
	 * @return en double med avståndet från punkt p1 och punkt p2
	 */
	private double getDistance(Point p1, Point p2){
		return Math.sqrt( Math.pow((p1.getX()-p2.getX()), 2.0) + Math.pow((p1.getY()-p2.getY()), 2.0) );
	}

	/**
	 * Den publika metoden för att hitta de kortaste avståndet. 
	 * denna metod kommer att sortera listan i X-led och sedan 
	 * anropa den privata findClosestPair.
	 */
	public double findClosestPair(){
		Collections.sort(plane, new CmpX());
		return findClosestPair(0, plane.size()-1);
	}

	/**
	 * Den privata metoden för att hitta det korstaste avståndet. 
	 * @param intervalStart 
	 * @param instervalEnd
	 * @return smallest Kortaste avståndet. 
	 */
	private double findClosestPair(int intervalStart, int intervalEnd){
		// Basecase som vi når när listan har mindre än 3 element i sig. 
		if(intervalEnd-intervalStart<=2){ 
			return findClosestInInterval(intervalStart, intervalEnd);
		} 
		// Här kommer uppdelningen av listorna att ske. Vardera sida
		// av listan kommer att anropas med findClosestPair. 
		else
		{
			// Detta är mitten av den lista som vi arbetar på just nu. 
			int middle = ((intervalStart+intervalEnd)/2);
			// Detta är mitten fast i X-led.
			double middleX = (plane.get(intervalStart).getX()+plane.get(intervalEnd).getX())/2;
			// De rekursiva anropen. 
			double left = findClosestPair(intervalStart, middle);
			double right = findClosestPair(middle+1, intervalEnd); 
			// Sätter smallest till det minsta av left och right
			double smallest = (left < right ? left : right);

			// Här går vi igenom elementen för att hitta de element som ska ligga i 
			// mittenArean (strip). Dessa ska ligga i intervallet som vi har nämnt i beskrivningen. 
			// Sedan sorteras denna lista i Y-led.
			ArrayList<Point> strip = new ArrayList<Point>();

			for(Point p : plane)
				if(p.getX() > (middleX - smallest) && p.getX() < (middleX + smallest))
					strip.add(p);

			Collections.sort(strip, new CmpY());

			// En BruteForce metod för att kontrollera de element som vi lagt in 
			// i strip. Denna metod nämner Weiss i boken på sida 409. 
			for (int i=0; i<strip.size(); i++) 
				for (int j=i+1; j< strip.size(); j++) 
					if ((strip.get(j).y - strip.get(i).y) > smallest) 
						break; 
					else 
					{ 
						double tmp = getDistance(strip.get(i), strip.get(j));
						if(tmp < smallest)
							smallest = tmp;
					} 
			return smallest;
		}
	}

	/**
	 * Brute force algoritm för basecase och testning av algoritmen.
	 * @param instervalStart
	 * @param intervalEnd
	 * @return delta som är det minsta avståndet mellan de närmaste punkterna. 
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

	public static void main (String[]args)
	{
		ClosestPair cp = new ClosestPair();
		Random r = new Random();

		for(int i = 0; i<1000; i++)
			cp.addPoint(new Point(r.nextInt(1000), r.nextInt(1000)));

	cp.findClosestPair();
//		cp.findClosestInInterval(0, cp.getPlaneSize()-1);
	}
}




