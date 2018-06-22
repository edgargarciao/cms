package co.ufps.edu.dto;

public class ResultDB {

	private int result;
	
	private long key;

	public ResultDB() {
		
	}
	
	public ResultDB(int result, long key) {
		super();
		this.result = result;
		this.key = key;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}
	
	
}
