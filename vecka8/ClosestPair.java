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
        public boolean equals(Point other){
            if(x==other.getX() && y==other.getY())
                return true;
            else
                return false;
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
            return findClosestInInterval(intervalStart, intervalEnd);
        } 
        else{
            System.out.println(intervalStart +" " +intervalEnd);
            //+1 för att middle ska bli första elementet i högerdelen för både jämn och ojämn storlek på array
            int middle = ((intervalStart+intervalEnd)/2);
            int middleX = (plane.get(intervalStart).getX()+plane.get(intervalEnd).getX())/2;
            //recurse left
            double left = findClosestPair(intervalStart, middle);
            //recurse right
            double right = findClosestPair(middle+1, intervalEnd); 
            //smallest of the two
            double smallest = (left < right ? left : right);
            int offset = (int)Math.ceil(smallest);
            System.out.println("offset: "+offset);
            int leftBound=middle; 
            int rightBound=middle+1; 

//ALT 1
            while(plane.get(leftBound).getX()<middleX+smallest && leftBound<intervalStart){
                leftBound--;
                System.out.println("left: "+leftBound);
            }
            while(plane.get(rightBound).getX()<middleX+smallest && rightBound<intervalEnd){
                rightBound++;
                System.out.println("right: "+rightBound);
            }
            //hitta kordinaterna som ligger mindre än smallest från mitten
//ALT 2
//            for(int l = leftBound; plane.get(l).getX()>middleX-smallest && l>intervalStart; leftBound=l--)
//            {System.out.println("l: "+l);
//                System.out.println(plane.get(l).getX());}
//            for(int r = rightBound; plane.get(r).getX()<middleX+smallest && r<=intervalEnd; rightBound=r++)
//            {System.out.println("r: "+r);
//                System.out.println(plane.get(r).getX());}

            /**
            *Jämför de till vänster med de till höger
            *
            */
            double both=Double.MAX_VALUE;
            for(int i=leftBound ; plane.get(i).getX()<=middleX ;i++){
                Point tmp = plane.get(i);
                for(int j = rightBound; plane.get(j).getX()>middleX; j--){
                    if(Math.abs(tmp.getX()-plane.get(j).getX())>smallest 
                        || Math.abs(tmp.getY()-plane.get(j).getY())>smallest)
                        break;
                    double newDelta=getDistance(tmp, plane.get(j));
                    if(newDelta<both)
                        both=newDelta;
                }
            }

            //both= findClosestPair(leftBound,rightBound);
            if(both<smallest)
                return both;
            else
                return smallest;
        }
    }
    public double findClosestInInterval(int intervalStart, int intervalEnd){
        double delta=Double.MAX_VALUE;
        for(int i=intervalStart; i<intervalEnd+1; i++){
            Point tmp = plane.get(i);
            for(int j = i+1; j<intervalEnd+1; j++ ){
                if(Math.abs(tmp.getX()-plane.get(j).getX())>delta)
                    break;
                double newDelta=getDistance(tmp, plane.get(j));
                if(newDelta<delta)
                    delta=newDelta;
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
        System.out.println(cp.toString());        
        double fcp = cp.findClosestPair();

        System.out.println(cp.toString());        
        System.out.println("----------");
        System.out.println(fcp);
        System.out.println("----------");
        double fcii = cp.findClosestInInterval(0,cp.getPlaneSize()-1);
        System.out.println(fcii);

        //Testfall: (1,10) (100,10) (90,300), (15,700) , (55,2000), (42,9000)
    }
}

