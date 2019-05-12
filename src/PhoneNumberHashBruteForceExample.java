

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import fr.cryptohash.SHA256;

/**
 * Example of code to brute force all possible phone numbers to demonstrate 
 * the lack of security in trying to hash phone numbers.
 * 
 * @author Zachary Chandler
 */
public class PhoneNumberHashBruteForceExample {

	public static final long MAX_PHONE_NUMBER = 1_000_000_0000L;
	public static final int DIGEST_LENGTH = 32;
	
	private byte[] secret = new byte[DIGEST_LENGTH];
	private byte[] guess = new byte[DIGEST_LENGTH];
	private boolean finished = false;
	private long i;
	
	public static void main(String[] args) {
		PhoneNumberHashBruteForceExample instance = new PhoneNumberHashBruteForceExample();
		instance.attempt(Utils.randomPhoneNumber());
	}
	
	public synchronized void attempt(String phoneNumber) {
		SHA256 hash = new SHA256();
		hash.update(Utils.getBytes(Long.parseLong(phoneNumber)));
		hash.digest(secret, 0, DIGEST_LENGTH);
		
		new Thread(new DisplayProgress()).start();
		
		for (i = 0; i < MAX_PHONE_NUMBER; i++) {
			hash.update((byte) (i >> (8 * 0)));
			hash.update((byte) (i >> (8 * 1)));
			hash.update((byte) (i >> (8 * 2)));
			hash.update((byte) (i >> (8 * 3)));
			hash.update((byte) (i >> (8 * 4)));
			hash.update((byte) (i >> (8 * 5)));
			hash.update((byte) (i >> (8 * 6)));
			hash.update((byte) (i >> (8 * 7)));
			hash.digest(guess, 0, DIGEST_LENGTH);
			
			if (Arrays.equals(secret, guess)) {
				break;
			}
		}
		
		finished = true;
	}
	
	private class DisplayProgress implements Runnable {
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			
			System.out.println("Attempting to find: " + Arrays.toString(secret));
			
			while (!finished) {
				System.out.printf("%02d%% : %02d bits : %010d searched\n", i * 100 / MAX_PHONE_NUMBER, Utils.maxBit(i), i);
				
				try {
					TimeUnit.SECONDS.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
					break;
				}
			}
			
			long runtime = System.currentTimeMillis() - start;

			System.out.println("Found: " + Arrays.toString(guess));
			System.out.println("Number: " + i);
			System.out.printf("Runtime: %dm %ds %dms\n", 
					runtime / 1000 / 60, 
					runtime / 1000 % 60, 
					runtime % 1000);
			
			System.out.printf("Searched ~%d%% of possible phone numbers.", i);
		}
	}
}
