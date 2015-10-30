import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class TesterClass {
	
	final static int NUM_STRESS_TESTS = 1000;
	final static int LOAD_INTERVAL_BOUND = 900;
	final static int MIN_LOAD = 100;
	
	final static int NUM_ACTIONS = 3;
	final static int GET = 0;
	final static int SET = 1;
	final static int DELETE = 2;
	
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
		int unitTests = numTests;
		int unitPassed = numPassed;
		
		System.out.println("Reading in word list");
		String[] wordList = inputFile();

		// Stress Tests
		int tGMisses = 0, tDMisses = 0, tPlacements = 0, tDeletes = 0, tSets = 0, tGets = 0;
		for(int a = 0; a < NUM_STRESS_TESTS; a++){
			if(a % 10 == 0) System.out.printf("Currently on Stress Test %d...\n", a);
			
			int curHMapSize = rgen.nextInt(LOAD_INTERVAL_BOUND) + MIN_LOAD;

			int curNumTrials = rgen.nextInt(curHMapSize) + curHMapSize;
			HMap curHMap = new HMap(curHMapSize);
			HashMap<String, Item> containedWords = new HashMap<String, Item>();
			containedWords.put("Hey", first);
			
			// Cur Test
			for(int b = 0; b < curNumTrials; b++){
				int curAction = rgen.nextInt(NUM_ACTIONS);
				switch(curAction) {
				case GET:
					tGets++;
					if(containedWords.size() == 0){
						// Generate new one, should return null;
						String randKey = genRandomWord(rgen, wordList);
						if(!assertEquals("Get fake", curHMap.get(randKey) == null)) tGMisses++;
					} else {
						// Get random key from list, should return proper value
						String randKey = genRandomWord(rgen, containedWords.keySet().toArray(new String[containedWords.size()]));
						if(!assertEquals("Get real", containedWords.get(randKey).equals((Item) curHMap.get(randKey)))) tGMisses++;
					}
				case SET:
					tSets++;
					String randKey = genRandomWord(rgen, wordList);
					Item curItem = t.new Item(randKey, rgen.nextInt(100));
					containedWords.put(randKey, curItem);
					assertEquals("Set", curHMap.set(randKey, curItem));
				case DELETE:
					tDeletes++;
					if(containedWords.size() == 0){
						// Gen new one, should return null;
						String randKey1 = genRandomWord(rgen, wordList);
						if(!assertEquals("Delete null",curHMap.delete(randKey1) == null)) tDMisses++;
					} else {
						// Get random key from list, should return proper value
						String randKey1 = genRandomWord(rgen, containedWords.keySet().toArray(new String[containedWords.size()]));
						if(!assertEquals("Delete real", containedWords.get(randKey1).equals((Item) curHMap.delete(randKey1)))) tDMisses++;
						containedWords.remove(randKey1);
						assertEquals("Delete worked", curHMap.get(randKey1) == null);
					}
				}
			}
		}
		
		// ALL TESTS PASSED?
		String passFail = (unitPassed == unitTests) ? "PASSED" : "FAILED";
		float unitFailRate = (float) unitPassed/unitTests * 100;
		System.out.printf("\n\nUNIT TEST RESULTS: (%d / %d) %s\n", unitPassed, unitTests, passFail);
		System.out.printf("FAIL RATE: %.3f%%\n\n", unitFailRate);

		
		int stressPassed = numPassed - unitPassed;
		int stressTests = numTests - unitTests;
		float stressFailRate = (float) stressPassed/stressTests * 100;
		float gMissRate = (float) tGMisses/tGets * 100;
		float dMissRate = (float) tDMisses/tDeletes * 100;
		System.out.printf("STRESS TEST RESULTS: (%d / %d) PASSED\n\n", stressPassed, stressTests, passFail);
		System.out.printf("TOTAL GETS: %d\n", tGets);
		System.out.printf("TOTAL SETS: %d\n", tSets);
		System.out.printf("TOTAL DELETES: %d\n\n", tDeletes);
		System.out.printf("TOTAL GET MISSES: %d %.2f%%\n", tGMisses, gMissRate);
		System.out.printf("TOTAL DELETE MISSES: %d %.2f%%\n\n", tDMisses, dMissRate);
		System.out.printf("STRESS TEST FAIL RATE: %.3f%%\n\n", stressFailRate);
	}
	
	private static boolean assertEqualsPrint(String name, boolean condition, boolean print){
		numTests++;
		if(condition){
			numPassed++;
			if(print) System.out.println(name + ": PASSED");
			return true;
		} else {
			if(print) System.out.println(name + ": FAILED");
			return false;
		}
	}
	
	private static boolean assertEquals(String name, boolean condition){
		return assertEqualsPrint(name, condition, false);
	}
	
	
	/**
	 * Adapted from http://stackoverflow.com/questions/16100175/store-text-file-content-line-by-line-into-array
	 * @return String[] of the words in the file
	 */
	private static String[] inputFile(){
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("big.txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        String str;

        ArrayList<String> list = new ArrayList<String>();
        try {
			while((str = in.readLine()) != null){
			    list.add(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return list.toArray(new String[0]);
	}
	
	public static String genRandomWord(Random rgen, String[] wordList){
		return wordList[rgen.nextInt(wordList.length)];
	}
	
	
	/**
	 * Test Item Object to put in my HMap Implementation
	 * 
	 * @author Tim
	 *
	 */
	class Item {
		String name;
		int monthsOld;
		
		public Item(String n, int m){
			name = n;
			monthsOld = m;
		}
	}
	
}
