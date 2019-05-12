

import java.util.Random;

public class Utils {

	private static Random R = new Random();
	
	public static final byte[] getBytes(long i) {
		byte[] message = new byte[8];
		
		for (int j = 0; j < 8; j++) {
			message[j] = (byte) (i >> (j * 8)); 
		}
		
		return message;
	}
	
	public static final boolean equals(byte[] bytes1, byte[] bytes2) {
		if (bytes1.length != bytes2.length) {
			return false;
		}
		
		for (int i = 0; i < bytes1.length; i++) {
			if (bytes1[i] != bytes2[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Generates a random long from 0 (inclusive) to max (exclusive)
	 * @param max the limit on the size of the returned long
	 * @return a random long greater than or equal to zero and less than max
	 */
	public static final long randomLong(long max) {
		return (long) (R.nextDouble() * max);
	}

	public static int maxBit(long value) {
		for (int i = 0; i <= Long.SIZE; i++, value >>>= 1) {
			if (value == 0) {
				return i;
			}
		}
		
		throw new IllegalStateException();
	}

	/**
	 * Generates 10 random digits that do not start with zero or 1.
	 * @return a phone number consisting of 10 numerical digits
	 */
	public static String randomPhoneNumber() {
		long firstNumber = (randomLong(8) + 2) * 100_000_0000L;
		long otherNumbers = randomLong(100_000_0000L);
		
		return Long.toString(firstNumber + otherNumbers);
	}
}
