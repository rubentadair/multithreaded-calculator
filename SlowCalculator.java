import java.util.concurrent.Callable;

public class SlowCalculator implements Callable<Integer> {
    private final long N;

    public SlowCalculator(final long N) {
        this.N = N;
    }

    public Integer call() {
        return calculateNumFactors(N);
    }

    private int calculateNumFactors(final long N) {
        int count = 0;
        for (long candidate = 2; candidate < Math.abs(N); ++candidate) {
            if (Thread.interrupted()) {
                return count;
            }
            if (isPrime(candidate)) {
                if (Math.abs(N) % candidate == 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean isPrime(final long n) {
        for (long candidate = 2; candidate < Math.sqrt(n) + 1; ++candidate) {
            if (n % candidate == 0) {
                return false;
            }
        }
        return true;
    }
}