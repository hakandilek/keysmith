package keysmith.service.db;

import java.util.Locale;

public class HashFunction {

	final int[] seeds;

	final int mask = 0x1F;

	private int seed;

	public HashFunction(int seed) {
		super();
		this.seed = seed;
		seeds = getSeeds(seed);
	}

	public String asString(Integer x) {
		int h = hash(x);
		String hx = hex(h);
		return hx;
	}
	
	public Integer asInteger(String s) {
		return null;
	}
	
	private String hex(int h) {
		return Integer.toHexString(h).toUpperCase(Locale.ENGLISH);
	}

	int hash(int n) {
		return Integer.rotateRight(seed ^ n, 16);
	}

	private static int[] getSeeds(int n) {
		int[] ss = new int[11];
		for (int i = 0; i < ss.length; i++) {
			n = Integer.rotateRight(n, 1);
			ss[i] = n;
		}
		return ss ;
	}

}
