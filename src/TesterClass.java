import java.util.Random;
public class TesterClass {
	
	final static int NUM_TESTS = 1;
	final static int LOAD_INTERVAL_BOUND = 90;
	final static int MIN_LOAD = 10;
	static int numPassed = 0;

	public static void main(String[] args){		
		TesterClass t = new TesterClass();
		Random rgen = new Random();
		
		////// Unit Testing \\\\\\
		// Can Store a value
		HMap testMap = new HMap(10);
		Item first = t.new Item("my_brain", 20);
		
		assertEquals("Setting Item", testMap.set("FIRST_KEY", first));
		assertEquals("Get Item", first.equals((Item) testMap.get("FIRST_KEY")));

		// Stress Test
		int tMisses, tPlacements, tTrials, tDeletes, tSets, tGets;
//		for(int a = 0; a < NUM_TESTS; a++){
//			int curHMapSize = rgen.nextInt(LOAD_INTERVAL_BOUND) + MIN_LOAD;
//			int cMisses, cPlacements, cTrials, cDeletes, cSets, cGets;
//
//			int curNumTrials = rgen.nextInt(curHMapSize) + curHMapSize;
//			HMap curHMap = new HMap(curHMapSize);
//			for(int b = 0; b < curNumTrials; b++){
//				
//			}
//		}
		
	}
	
	private static void assertEquals(String name, boolean condition){
		System.out.print(name + ": ");
		if(condition){
			numPassed++;
			System.out.print("PASSED");
		} else {
			System.out.print("FAILED");
		}
		System.out.print("\n");
	}
	
	class Item {
		String name;
		int monthsOld;
		
		public Item(String n, int m){
			name = n;
			monthsOld = m;
		}
	}
	
}
