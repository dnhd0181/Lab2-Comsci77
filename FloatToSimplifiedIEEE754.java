import java.util.*;

/**
 * Floating-point to simplified IEEE 754 representation (1 sign bit, 5 exponent
 * bits, 8 significand bits)
 *
 * Steps:
 * 1. Extract the sign bit (1 for negative, 0 for positive)
 * 2. Convert the absolute value of the number to binary scientific notation
 * (0.1XXXXXXX * 2^exp)
 * 3. Compute the biased exponent (exp + 15)
 * 4. Extract the significand (first 8 bits after the binary point)
 * 5. Print the components
 */
public class FloatToSimplifiedIEEE754 {
    public static String floatToSimplifiedIEEE754(double num) {
        if (num == 0)
            return "000000000000"; // Special case for zero

        // Step 1: Determine the sign bit
        int signBit = num < 0 ? 1 : 0;
        double absNum = Math.abs(num);
        System.out.println("The Sign Bit is: " + signBit);

        // Step 2: Calculate the exponent
        int exponent = (int) Math.floor(Math.log(absNum) / Math.log(2));
        int biasedExponent = exponent + 15; // Bias of 15 for 5-bit exponent
        if (biasedExponent < 0 || biasedExponent > 31)
            return "Overflow/Underflow";
        System.out.println("The Exponent is: " + biasedExponent + " (biased), actual exponent: " + exponent);

        // Step 3: Normalize the significand (0.1XXXXXXX)
        double significandValue = absNum / Math.pow(2, exponent); // Should be in form 1.XXXX
        significandValue -= 1; // Convert to form 0.XXXXXX

        // Step 4: Extract exactly 8 bits after the binary point
        StringBuilder significand = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            significandValue *= 2;
            int bit = (int) Math.floor(significandValue);
            significand.append(bit);
            significandValue -= bit;
        }
        System.out.println("The Significand is: 0." + significand.toString());

        // Step 5: Convert to binary representation
        String exponentBits = String.format("%5s", Integer.toBinaryString(biasedExponent)).replace(' ', '0');
        return signBit + " | " + exponentBits + " | " + significand.toString();
    }

    public static void main(String[] args) {
        System.out.println("Binary Representation: " + floatToSimplifiedIEEE754(10.5)); // Example positive number
        System.out.println();
        System.out.println("Binary Representation: " + floatToSimplifiedIEEE754(-3.75)); // Example negative number
    }
}
