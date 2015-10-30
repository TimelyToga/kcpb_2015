import java.lang.Math;

/**
 * My Implementation of a HashMap for the 2015 KPCB Fellows Application
 * 
 * @author Tim Blumberg
 *
 */
public class HMap {
	
	private int numItems;
	private int size;
	private Object[] values;
	
	public HMap(int size){
		this.numItems = 0;
		this.size = size;
		values = new Object[size];
	}
	
	public boolean set(String key, Object value){
		int arrayKey = getHash(key);
		if(values[arrayKey] == null){
			values[arrayKey] = value;
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
			values[arrayKey] = null;
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
