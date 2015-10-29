import java.lang.Math;

public class HMap {
	
	private int numItems;
	private int size;
	private Object[] values;
	
	/* Problem definition
	constructor(size): return an instance of the class with pre-allocated space for the 
		given number of objects.
	boolean set(key, value): stores the given key/value pair in the hash map. Returns a 
		boolean value indicating success / failure of the operation.
	get(key): return the value associated with the given key, or null if no value is set.
	delete(key): delete the value associated with the given key, returning the value on 
		success or null if the key has no value.
	float load(): return a float value representing the load factor (`(items in hash map)/(size of hash map)`) of the data structure
		Since the size of the dat structure is fixed, this should never be greater than 1.
	*/
	
	public HMap(int size){
		this.numItems = 0;
		this.size = size;
		values = new Object[size];
	}
	
	public boolean set(String key, Object value){
		int arrayKey = getHash(key);
		if(values[arrayKey] == null){
			values[arrayKey] = value;
			System.out.println(values[arrayKey]);
			numItems++;
			return true;
		}
		return false;
	}
	
	public Object get(String key){
		int arrayKey = getHash(key);
		return values[arrayKey];
	}
	
	public Object delete(String key){
		int arrayKey = getHash(key);
		Object o = values[arrayKey];
		if(o != null){
			numItems--;
		}
		
		return o;
	}
	
	public float load(){
		return (float) numItems / size; 
	}
	
	private int getHash(String key){
		return Math.abs(key.hashCode()) % size;
	}

}
