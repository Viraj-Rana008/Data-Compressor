package HuffmanCompressor;

public final class Pair<T1, T2> {
	protected final T1 first;
	protected final T2 second;
	
	public Pair(T1 fst, T2 sec) {
		this.first = fst;
		this.second = sec;
	}
	
	public T1 getFirst() {
		return first;
	}
	
	public T2 getSecond() {
		return second;
	}

}
