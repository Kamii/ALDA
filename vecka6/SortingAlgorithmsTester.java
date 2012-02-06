package alda.sort;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SortingAlgorithmsTester {

	/**
	 * Filen som resultaten ska skrivas p�.
	 */
	private static final String REPORT_FILE_NAME = "results.txt";

	/**
	 * S�tt denna till true f�r mer information under k�rning
	 */
	private static final boolean PRINT_RESULTS_DURING_RUN = true;

	/**
	 * S�tt denna till true f�r att f� felmeddelande om n�gon algoritm
	 * misslyckas med en sortering. V�ldigt nyttigt under utveckling, men
	 * fungerar inte s� bra om n�gon av algoritmerna inte �r implementerad �n.
	 */
	private static final boolean CHECK_SORTING = false;

	/**
	 * Tv� omvandlingskonstanter som normalt kan ignoreras
	 */
	private static final long NANO_SECONDS_PER_MILLI_SECOND = 1000 * 1000;
	private static final long NANO_SECONDS_PER_SECOND = 1000 * NANO_SECONDS_PER_MILLI_SECOND;

	/**
	 * En testkonfigration avbryts n�r ett test har tagit mer �n s� h�r mycket
	 * tid.
	 * 
	 * En sekund �r naturligtvis f�r lite. �ka det till n�got l�mpligt f�r er.
	 */
	private static final double MAX_TIME_FOR_TEST_IN_SECONDS = 1.0;

	/**
	 * Vilka storlekar p� listorna som ska anv�ndas. Kommentera bort eller �ndra
	 * p� siffrorna om ni vill kontrollera vissa speciella storlekar.
	 */
	private static final int[] SIZES = { 100, 500, 1000, 2000, 4000, 10000,
			20000, 50000, 100000, 500000, 1000000, 5000000

	// Min javakonfiguration f�r slut p� minne h�r.
	// G�r att st�lla upp om man vill testa riktigt stora datam�ngder
	// ,10000000, 100000000
	};

	/**
	 * Kommentera bort de sorteringsalgoritmer du inte vill k�ra
	 */
	private static final Sorter[] SORTERS = { new QuickSorterMedianOfThree(),
			new QuickSorterRandom(), new QuickSorterFirstElement(),
			new MergeSorter(), new InsertionSorter(), new SelectionSorter(),
			new BubbelSorter(), new JavaCollectionsSorter() };

	// ////////////////////////////////////////////////////////////
	// Koden nedanf�r ska ni normalt sett inte beh�va bry er om. //
	// ////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		Collection<TestConfiguration> configurations = createConfigurations();
		runTests(configurations);
		createReport(configurations);
		System.out.println("Done, check result file for test data");
	}

	private static final DataGenerator[] DATA_GENERATORS = {
			new IntegerGenerator(), new ComplexGenerator() };
	private static final ListCreator[] LIST_CREATORS = {
			new ArrayListCreator(), new LinkedListCreator() };
	private static final ListConfigurationStrategy[] ORIGINAL_SORTING_STRATEGIES = {
			new RandomList(), new SortedList(), new ReverseSortedList() };
	private static final ListConfigurationStrategy[] DUPLICATION_STRATEGIES = {
			new UnchangedList(), new FewDuplicatesList(),
			new ManyDuplicatesList() };

	private static Collection<TestConfiguration> createConfigurations() {
		List<TestConfiguration> configurations = new ArrayList<TestConfiguration>();

		for (Sorter s : SORTERS)
			for (DataGenerator dg : DATA_GENERATORS)
				for (ListCreator lc : LIST_CREATORS)
					for (ListConfigurationStrategy oss : ORIGINAL_SORTING_STRATEGIES)
						for (ListConfigurationStrategy ds : DUPLICATION_STRATEGIES)
							configurations.add(new TestConfiguration(s, dg, lc,
									oss, ds));

		return configurations;
	}

	private static void runTests(Collection<TestConfiguration> configurations) {
		for (TestConfiguration configuration : configurations) {
			System.out.println(configuration);
			for (int size : SIZES) {
				if (PRINT_RESULTS_DURING_RUN) {
					System.out.print(size + "\t");
					System.out.println(String.format("%f",
							configuration.run(size)));
				} else {
					configuration.run(size);
				}
			}
		}
	}

	private static void createReport(
			Collection<TestConfiguration> configurations) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					REPORT_FILE_NAME));
			for (TestConfiguration configuration : configurations) {
				writer.write(configuration.toString());
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("IOException occured during report generation");
			e.printStackTrace();
		}
	}

	private static class Test {

		private int size;
		private double time;

		public Test(int size, double time) {
			this.size = size;
			this.time = time;
		}

		public String toString() {
			return String.format("%d\t%f", size, time);
		}
	}

	private static class TestConfiguration {

		private Sorter sorter;
		private DataGenerator datagenerator;
		private ListCreator listcreator;
		private ListConfigurationStrategy sortingstrategy;
		private ListConfigurationStrategy duplicationstrategy;

		private double timeToRunLastTest = Double.MIN_VALUE;
		private List<Test> tests = new ArrayList<Test>();

		public TestConfiguration(Sorter sorter, DataGenerator datagenerator,
				ListCreator listcreator,
				ListConfigurationStrategy sortingstrategy,
				ListConfigurationStrategy duplicationstrategy) {
			this.sorter = sorter;
			this.datagenerator = datagenerator;
			this.listcreator = listcreator;
			this.sortingstrategy = sortingstrategy;
			this.duplicationstrategy = duplicationstrategy;
		}

		public double run(int size) {
			double testTime = -1.0;
			if (timeToRunLastTest < MAX_TIME_FOR_TEST_IN_SECONDS) {
				System.gc();
				List list = listcreator.createList();
				datagenerator.generate(list, size);
				duplicationstrategy.configure(list);
				sortingstrategy.configure(list);
				System.gc();

				try {
					timeToRunLastTest = testTime = sorter.sort(list);
				} catch (Exception e) {
					System.err.println("Exception caught while sorting");
					e.printStackTrace();
				}

				checkSorting(list);

				tests.add(new Test(size, testTime));
			} else {
			}
			return testTime;
		}

		private void checkSorting(List list) {
			if (!CHECK_SORTING)
				return;
			Iterator<Comparable> i = list.iterator();
			Comparable c1 = i.next();
			while (i.hasNext()) {
				Comparable c2 = i.next();
				if (c1.compareTo(c2) > 0)
					throw new IllegalStateException(
							"List not sorted correctly by configuration "
									+ this + " " + list);
			}
		}

		public String toString() {
			String header = "" + sorter + datagenerator + listcreator
					+ sortingstrategy + duplicationstrategy;
			if (tests.isEmpty())
				return header;

			StringBuilder builder = new StringBuilder();
			for (Test t : tests) {
				builder.append(header);
				builder.append(t);
				builder.append("\n");
			}
			return builder.toString();
		}
	}

	private static class Complex implements Comparable<Complex> {
		private static final Random RND = new Random();
		private static final int DATA_SIZE = 20;

		private int[] data = new int[DATA_SIZE];

		public Complex() {
			for (int n = 0; n < DATA_SIZE; n++) {
				if (RND.nextBoolean())
					data[n] = RND.nextInt(2);
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Complex) {
				Complex c = (Complex) obj;
				for (int n = 0; n < DATA_SIZE; n++) {
					if (data[n] != c.data[n])
						return false;
				}
				return true;
			} else {
				return false;
			}
		}

		@Override
		public int compareTo(Complex o) {
			for (int n = 0; n < DATA_SIZE; n++) {
				if (data[n] != o.data[n])
					return data[n] - o.data[n];
			}
			return 0;
		}
	}

	private static abstract class Named {
		public String toString() {
			return getClass().getSimpleName() + "\t";
		}
	}

	private static abstract class DataGenerator extends Named {
		public abstract void generate(List l, int size);
	}

	private static class ComplexGenerator extends DataGenerator {

		@Override
		public void generate(List l, int size) {
			for (int n = 0; n < size; n++) {
				l.add(new Complex());
			}
		}

	}

	private static class IntegerGenerator extends DataGenerator {

		@Override
		public void generate(List l, int size) {
			for (int n = 0; n < size; n++) {
				l.add(new Integer(n));
			}
		}

	}

	private static abstract class ListCreator extends Named {
		public abstract List createList();
	}

	private static class ArrayListCreator extends ListCreator {

		@Override
		public List createList() {
			return new ArrayList();
		}

	}

	private static class LinkedListCreator extends ListCreator {
		@Override
		public List createList() {
			return new LinkedList();
		}

	}

	private static abstract class ListConfigurationStrategy extends Named {
		public abstract void configure(List l);
	}

	private static class RandomList extends ListConfigurationStrategy {

		@Override
		public void configure(List l) {
			Collections.shuffle(l);
		}

	}

	private static class SortedList extends ListConfigurationStrategy {
		@Override
		public void configure(List l) {
			Collections.sort(l);
		}

	}

	private static class ReverseSortedList extends ListConfigurationStrategy {
		@Override
		public void configure(List l) {
			Collections.sort(l);
			Collections.reverse(l);
		}

	}

	private static class UnchangedList extends ListConfigurationStrategy {
		@Override
		public void configure(List l) {
			// Do nothing
		}

	}

	private static abstract class DuplicationConfigurationStrategy extends
			ListConfigurationStrategy {

		private static final Random RND = new Random();

		private void swap(List l, int index1, int index2) {
			Object o = l.get(index1);
			l.set(index1, l.get(index2));
			l.set(index2, o);
		}

		public void createDuplicates(List l, int maxNoDuplicates) {
			int count = 0;
			do {
				count++;
				swap(l, RND.nextInt(l.size()), RND.nextInt(l.size()));
			} while (count < maxNoDuplicates);
		}
	}

	private static class FewDuplicatesList extends
			DuplicationConfigurationStrategy {
		@Override
		public void configure(List l) {
			createDuplicates(l, l.size() / 100);
		}

	}

	private static class ManyDuplicatesList extends
			DuplicationConfigurationStrategy {
		@Override
		public void configure(List l) {
			createDuplicates(l, l.size() / 5);
		}
	}

}
