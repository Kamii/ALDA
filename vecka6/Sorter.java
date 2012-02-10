import java.util.List;

public abstract class Sorter<T extends Comparable<? super T>> {

	private static final double NANOSECONDS_PER_SECONDS = 1000000000.0;
	private long lastRunTime;

	protected void swap(List<T> l, int index1, int index2) {
		T tmp = l.get(index1);
		l.set(index1, l.get(index2));
		l.set(index2, tmp);
	}

	
	protected abstract void doSort(List<T> l);

	public  double sort(List<T> list){
		long start=System.nanoTime();
		doSort(list);
		lastRunTime=System.nanoTime()-start;
		return lastRunTimeInSeconds();
	}

	public double lastRunTimeInSeconds() {
		return lastRunTime/NANOSECONDS_PER_SECONDS;
	}

	public String toString(){
		return getClass().getSimpleName();
	}
	
}
