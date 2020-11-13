import java.math.BigInteger;

public class javatest {

    public static BigInteger findNumber(Chooser c) {
        BigInteger mid = BigInteger.ONE;
        BigInteger lowBound = BigInteger.ONE;
        BigInteger highBound = BigInteger.ONE;
        BigInteger result = BigInteger.ZERO;
        while (c.guess(highBound).equals("lower")) {
            highBound = highBound.multiply(BigInteger.valueOf(2));
        }
        while(lowBound.compareTo(highBound) < 0 || lowBound.compareTo(highBound) == 0) {
            mid = (lowBound.add(highBound)).divide(BigInteger.valueOf(2));
            if(c.guess(mid).equals("higher")) {
                highBound = mid.subtract(BigInteger.ONE);
            } else {
                lowBound = mid.add(BigInteger.ONE);
                result = mid;
            }
        }
        return result;
    }
}

public class Chooser {

    private final BigInteger target = BigInteger.valueOf(5247);

    public String guess(BigInteger number) {
        if(target.equals(number)) {
            return "correct";
        }
        if(target.compareTo(number) > 0) {
            return "lower";
        }
        if(target.compareTo(number) < 0) {
            return "higher";
        }
    }
}
