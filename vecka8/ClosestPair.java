import java.util.*;
import java.Math.*
public class ClosestPair{
    private ArrayList<Point> plane = new ArrayList<Point>();

    private class Point implements Comparable<Point>{
        private int x;
        private int y;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
        public int compareTo(Point p){
            return x - p.getX();
        }
    }
    public void addPoint(Point p){
        plane.add(p);
    }
    
    /**
    *
    *
    */
    private double getDistance(Point p1, Point p2){
        return Math.sqrt( Math.pow(p1.getX()-p2.getX()) + Math.pow(p1.getY()-p2.getY()) )
    }
    /**
     *Initeringsmetodför findClosestPair
     */
    public double findClosestPair(){
        Collections.sort(plane);
        //return findClosestPair(plane.get(0).getX(), plane.get(plane.size()-1).getX());
        return findClosestPair(0, plane.size()-1);
    }
    /**
     *Huvudmetod
     */
    private double findClosestPair(int intervalStart, int intervalEnd){
        /**
        *Basecase: det är två eller tre koordinater i intervallet
        *
        */
        if(intervalEnd-intervalStart<=2){ 
            double delta;
            for(int i=intervalStart; i<intervalEnd+1; i++){
                Point tmp = plane.get(i);
                    for(int j = i+1;; j<intervalEnd+1; j++ ){
                        double newDelta=getDistance(tmp, plane.get(j));
                        if(newDelta<delta)
                            delta=newDelta;
                    }
            } 
        } 
        //+1 för att middle ska bli första elementet i högerdelen för både jämn och ojämn storlek på array
        int middle = ((intervalStart+intervalEnd)/2);
        double left = findClosestPair(intervalStart, middle);
        double right = findClosestPair(middle+1, intervalEnd); 
        double offset = Math.floor(left < right ? offset = left : offset = right);
        double both = findClosestPair(middle-offset, middle+offset);
        if(both<offset)
            return both;
        else
            return offset;
    }
}
