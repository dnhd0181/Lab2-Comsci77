/**
 * Floating-point to the Simple Model representation (1 sign bit, 5 exponent
 * bits, 8 significand bits, bias-15)
 *
 * Steps:
 * 1. Extract the sign bit (1 for negative, 0 for positive)
 * 2. Convert the absolute value of the number to binary
 * scientific notation (0.1XXXXXXX * 2^exp)
 * 3. Compute the biased exponent (exp + 15)
 * 4. Extract the significand (first 8 bits after the binary point)
 */

public class SimpleFloatConverter {
    public static String FloatToTheSimpleModel(double inputValue) {
        if (inputValue == 0.0)
            return "0 | 0 | 00000000"; // Handle zero case

        int signBit = (inputValue < 0) ? 1 : 0;
        double value = Math.abs(inputValue);

        int exponent = 0;
        double significand = value;

        // Normalize to 1.XXXXXXX format
        while (significand >= 2.0) {
            significand /= 2.0;
            exponent++;
        }
        while (significand < 1.0) {
            significand *= 2.0;
            exponent--;
        }

        // Adjust the Exponent to normalize the Significand to 0.1XXXXXXX format
        exponent += 1;
        // Adjust exponent with bias
        exponent += 15;

        if (exponent < 0 || exponent > 31) {
            throw new ArithmeticException("Value out of representable range");
        }

        // Extract 8-bit significand (including leading 1 and next 7 bits)
        int significandBits = (int) (significand * 128);

        // Convert to binary string format
        String exponentBits = String.format("%5s", Integer.toBinaryString(exponent)).replace(' ', '0');
        String significandStr = String.format("%8s", Integer.toBinaryString(significandBits)).replace(' ', '0');

        return signBit + " | " + exponentBits + " | " + significandStr;
    }

    public static void main(String[] args) {
        System.out.println(FloatToTheSimpleModel(32));
        System.out.println(FloatToTheSimpleModel(-26.625));
        System.out.println(FloatToTheSimpleModel(-376.095));
    }
}
