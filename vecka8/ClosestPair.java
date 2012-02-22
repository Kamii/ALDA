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
            return p1.x-p2.x;
        }
        public boolean equals(Point p1, Point p2){
            return p1.x==p2.x;
        }
    }
    /**
    *Comparator i Y-led
    */
    private class CmpY implements Comparator<Point>{
        public int compare(Point p1, Point p2){
            return p1.y-p2.y;
        }
        public boolean equals(Point p1, Point p2){
            return p1.y == p2.y;
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
        if(p1.equals(p2))
            return 0.0;
        return Math.sqrt( Math.pow((double)(p1.getX()-p2.getX()), 2.0) + Math.pow((p1.getY()-p2.getY()), 2.0) );
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
        /**
         *Basecase: det är två eller tre koordinater i intervallet
         */
        if(intervalEnd-intervalStart<=2){ 
            return findClosestInInterval(intervalStart, intervalEnd);
        } 
        else{
            System.out.println(intervalStart +" " +intervalEnd);
        //+1 för att middle ska bli första elementet i högerdelen för både jämn och ojämn storlek på array
            int middle = ((intervalStart+intervalEnd)/2);
            double middleX = (plane.get(intervalStart).getX()+plane.get(intervalEnd).getX())/2;
        //recurse left
            double left = findClosestPair(intervalStart, middle);
        //recurse right
            double right = findClosestPair(middle+1, intervalEnd); 
        //smallest of the two
            double smallest = (left < right ? left : right);
            int leftBound=middle; 
            int rightBound=middle+1; 

//Hitta mittrännan
            while(plane.get(leftBound).getX()>middleX-smallest && leftBound>intervalStart){
                leftBound--;
                System.out.println("left: "+leftBound);
            }
            while(plane.get(rightBound).getX()<middleX+smallest && rightBound<intervalEnd){
                rightBound++;
                System.out.println("right: "+rightBound);
            }

            
            /**
            *Skapa listor för prickarna som ligger mindre än smallest ifrån middleX
            *
            */
            ArrayList<Point> leftList = new ArrayList<Point>();
            ArrayList<Point> rightList = new ArrayList<Point>();

            for(int i=leftBound ; plane.get(i).getX()<=middleX; i++){
                leftList.add(plane.get(i));
            } 
            Collections.sort(leftList, new CmpY());
            for(int i=rightBound ; plane.get(i).getX()>middleX; i--){
                rightList.add(plane.get(i));
            } 
            Collections.sort(rightList, new CmpY());

            /**
            *Jämför de till vänster med de till höger
            */
            double both=Double.MAX_VALUE;
            for(Point p : leftList){
                for(Point q : rightList){
                    if(Math.abs(p.y-q.y)>smallest){
                        break;
                    }
                    double newDelta=getDistance(p,q);
                    if(newDelta<both)
                        both=newDelta;
                    
                }
            }

            if(both<smallest)
                return both;
            else
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
                if(Math.abs(tmp.getX()-plane.get(j).getX())>delta){
                    break;
                }
                double newDelta=getDistance(tmp, plane.get(j));
                if(newDelta<delta){
                    delta=newDelta;
                }
            }
        }
        return delta;
    }
    public static void main(String[] args){
        ClosestPair cp = new ClosestPair();
        cp.addPoint(new Point(1,10));
        cp.addPoint(new Point(100,10));
        cp.addPoint(new Point(90,300));
        cp.addPoint(new Point(15,700));
        cp.addPoint(new Point(55,2000));
        cp.addPoint(new Point(42,9000));
        //System.out.println(cp.toString());        
        double fcii1 = cp.findClosestInInterval(0,cp.getPlaneSize()-1);
        System.out.println("\n----------");
        System.out.println("kontroll : "+fcii1);
        double fcp = cp.findClosestPair();
        System.out.println("SVAR: "+fcp);
        System.out.println("\n----------");

        //System.out.println(cp.toString());        
        double fcii = cp.findClosestInInterval(0,cp.getPlaneSize()-1);
        System.out.println("\n----------");
        System.out.println("kontroll : "+fcii);

        //Testfall: (1,10) (100,10) (90,300), (15,700) , (55,2000), (42,9000)
    }
}

