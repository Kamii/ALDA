import static org.junit.Assert.*;
import java.awt.Point;
import java.util.*;

import org.junit.*;
public class ClosestPairTester{

	@Test
		public void testFindClosestPair() 
		{
			ClosestPair cp = new ClosestPair();
			cp.addPoint(new Point(2,6));
			cp.addPoint(new Point(3,7));
			cp.addPoint(new Point(4,5));
			cp.addPoint(new Point(4,3));
			cp.addPoint(new Point(5,6));
			cp.addPoint(new Point(7,6));
			cp.addPoint(new Point(8,3));
			cp.addPoint(new Point(9,6));
			cp.addPoint(new Point(11,8));
			cp.addPoint(new Point(12,3));
			assertEquals(cp.findClosestInInterval(0,cp.getPlaneSize()-1), cp.findClosestPair(), 0.00);
		}

	/**
	 *   Testfall: (1,10) (100,10) (90, 300), (15, 700) , (55, 2000), (42, 9000)
	 *   två koordinater som är långt ifrån varandra på x-axeln men ändå är närmast
	 */
	@Test
		public void testClosestPairFirstAndLast() 
		{
			ClosestPair cp = new ClosestPair();
			cp.addPoint(new Point(12,40));
			cp.addPoint(new Point(1,4));
			cp.addPoint(new Point(52,7));
			cp.addPoint(new Point(245,6754));
			cp.addPoint(new Point(12245,25));
			cp.addPoint(new Point(114,432));
			cp.addPoint(new Point(746,2));
			cp.addPoint(new Point(452,54));
			cp.addPoint(new Point(1325,625));
			cp.addPoint(new Point(7876,96));
			cp.addPoint(new Point(12,22));
			cp.addPoint(new Point(245,765));
			cp.addPoint(new Point(13,657));
			cp.addPoint(new Point(345,63));
			assertEquals(cp.findClosestInInterval(0,cp.getPlaneSize()-1), cp.findClosestPair(), 0.00);
		}
	
	@Test
		public void randomTest1000()
		{
			ClosestPair cp = new ClosestPair();
			Random r = new Random();

			for(int i = 0; i<100; i++)
				cp.addPoint(new Point(r.nextInt(1000), r.nextInt(1000)));
			assertEquals(cp.findClosestInInterval(0,cp.getPlaneSize()-1), cp.findClosestPair(), 0.00);
		}

	@Test
		public void randomTestr5000()
		{
			ClosestPair cp = new ClosestPair();
			Random r = new Random();

			for(int i = 0; i<5000; i++)
				cp.addPoint(new Point(r.nextInt(5000), r.nextInt(5000)));
			assertEquals(cp.findClosestInInterval(0,cp.getPlaneSize()-1), cp.findClosestPair(), 0.00);
		}

	@Test
		public void randomTestr10000()
		{
			ClosestPair cp = new ClosestPair();
			Random r = new Random();

			for(int i = 0; i<10000; i++)
				cp.addPoint(new Point(r.nextInt(10000), r.nextInt(10000)));
			assertEquals(cp.findClosestInInterval(0,cp.getPlaneSize()-1), cp.findClosestPair(), 0.00);
		}
}
