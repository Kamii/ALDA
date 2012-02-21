public class ClosestPair{
    private Set<Point> plane = new HashSet<Point>();

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
    public void addPoint(Point p)

    public void findClosestPair{

    }

}
