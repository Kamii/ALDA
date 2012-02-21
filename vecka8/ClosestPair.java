import java.util.*;
public class ClosestPair{
    private ArrayList<Point> plane = new ArrayList<Point>();

    private static class Point implements Comparable<Point>{
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
    public String toString(){
        String str= "";
        for(Point p : plane){
            str+= "X: " + p.getX() + " Y: " + p.getY()+ "\n";
        }
        return str;
    }
    
    /**
    *Räknar ut avståndet mellan två punkter
    */
    private double getDistance(Point p1, Point p2){
        return Math.sqrt( Math.pow((double)(p1.getX()-p2.getX()), 2.0) + Math.pow((p1.getY()-p2.getY()), 2.0) );
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
        */
        if(intervalEnd-intervalStart<=2){ 
            double delta=Double.MAX_VALUE;
            for(int i=intervalStart; i<intervalEnd+1; i++){
                Point tmp = plane.get(i);
                    for(int j = i+1; j<intervalEnd+1; j++ ){
                        double newDelta=getDistance(tmp, plane.get(j));
                        if(newDelta<delta)
                            delta=newDelta;
                    }
            } 
            return delta;
        } 
        else{
            //+1 för att middle ska bli första elementet i högerdelen för både jämn och ojämn storlek på array
            int middle = ((intervalStart+intervalEnd)/2);
            double left = findClosestPair(intervalStart, middle);
            double right = findClosestPair(middle+1, intervalEnd); 
            double smallest = (left < right ? left : right);
            int offset = (int)Math.ceil(smallest);
            double both = findClosestPair(middle-offset, middle+offset);
            if(both<offset)
                return both;
            else
                return offset;
        }
    }
    public static void main(String[] args){
        ClosestPair cp = new ClosestPair();
        cp.addPoint(new Point(1,10));
        cp.addPoint(new Point(100,10));
        cp.addPoint(new Point(90,300));
        cp.addPoint(new Point(15,700));
        cp.addPoint(new Point(55,2000));
        cp.addPoint(new Point(42,9000));
        cp.toString();        
        double fcp = cp.findClosestPair();
        
        cp.toString();        
        System.out.println("----------");
        System.out.println(fcp);
       //Testfall: (1,10) (100,10) (90,300), (15,700) , (55,2000), (42,9000)
    }
}

