import java.util.Random;
public class TesterClass {
	
	final static int NUM_STRESS_TESTS = 1;
	final static int LOAD_INTERVAL_BOUND = 90;
	final static int MIN_LOAD = 10;
	static int numTests = 0;
	static int numPassed = 0;

	public static void main(String[] args){		
		TesterClass t = new TesterClass();
		Random rgen = new Random();
		
		////// Unit Testing \\\\\\
		// Can Store a value
		HMap testMap = new HMap(25);
		Item first = t.new Item("my_brain", 20);
		String fKey = "FIRST_KEY";
		
		assertEquals("Setting Item", testMap.set(fKey, first));
		assertEquals("Get Item", first.equals((Item) testMap.get(fKey)));
		assertEquals("Get Item Twice", first.equals((Item) testMap.get(fKey)));
		assertEquals("Delete return object", first.equals((Item) testMap.delete(fKey)));
		assertEquals("Delete return null", testMap.delete("Some_key") == null);
		assertEquals("Object actually deleted", (testMap.get(fKey) == null));
		String sKey = "lamps_are_nice";
		Item second = t.new Item("lamp", 25);
		String tKey = "<3 Smeagol";
		Item third = t.new Item("smeagol", 100);
		assertEquals("Set Set Get", testMap.set(sKey, second) 
				&& testMap.set(tKey, third) 
				&& second.equals(testMap.get(sKey)));
		assertEquals("Load has proper value", (((float) 2/25) == testMap.load()));

		// Stress Test
		int tMisses, tPlacements, tTrials, tDeletes, tSets, tGets;
		for(int a = 0; a < NUM_STRESS_TESTS; a++){
			int curHMapSize = rgen.nextInt(LOAD_INTERVAL_BOUND) + MIN_LOAD;
			int cMisses, cPlacements, cTrials, cDeletes, cSets, cGets;

			int curNumTrials = rgen.nextInt(curHMapSize) + curHMapSize;
			HMap curHMap = new HMap(curHMapSize);
			for(int b = 0; b < curNumTrials; b++){
				
			}
		}
		
		// ALL TESTS PASSED?
		String passFail = (numPassed == numTests) ? "PASSED" : "FAILED";
		System.out.printf("\n\nTEST RESULTS: %d / %d %s\n\n", numPassed, numTests, passFail);
		
	}
	
	private static void assertEquals(String name, boolean condition){
		numTests++;
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
