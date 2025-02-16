// Huy Nguyen

import java.util.Scanner;

public class SignedMagnitudeTwosComplement {

    public static int signedMagnitudeToTwosComplement(int signedMagnitude) {
        if (signedMagnitude == 0) {
            return 0;
        }
        int signBit = signedMagnitude >> 31;
        int magnitude = signedMagnitude & 0x7FFFFFFF; // Get the magnitude (ignore sign bit)

        if (signBit == 0) { // Positive number
            return magnitude;
        } else { // Negative number
            return (~magnitude + 1) & 0xFFFFFFFF; // Convert to 2's complement
        }
    }

    public static int twosComplementToSignedMagnitude(int twosComplement) {
        if (twosComplement == 0) {
            return 0;
        }
        int signBit = (twosComplement >> 31) & 1;
        if (signBit == 0) { // Positive number
            return twosComplement;
        } else { // Negative number
            int magnitude = (~twosComplement + 1) & 0xFFFFFFFF; // Get magnitude
            return (1 << 31) | magnitude; // Set sign bit
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter '1' to convert signed magnitude to 2's complement, '2' for the reverse, or 'q' to quit: ");
            String choice = scanner.nextLine();

            if (choice.equals("q")) {
                break;
            }

            try {
                if (choice.equals("1")) {
                    System.out.print("Enter signed magnitude (as a 32-bit integer): ");
                    int signedMagnitude = Integer.parseInt(scanner.nextLine());
                    if (signedMagnitude < -2147483648 || signedMagnitude > 2147483647) {
                        throw new NumberFormatException("Input must be a 32-bit signed integer.");
                    }
                    int result = signedMagnitudeToTwosComplement(signedMagnitude);
                    System.out.println("2's complement: " + result);

                } else if (choice.equals("2")) {
                    System.out.print("Enter 2's complement (as a 32-bit integer): ");
                    int twosComplement = Integer.parseInt(scanner.nextLine());
                    if (twosComplement < -2147483648 || twosComplement > 2147483647) {
                        throw new NumberFormatException("Input must be a 32-bit signed integer.");
                    }
                    int result = twosComplementToSignedMagnitude(twosComplement);
                    System.out.println("Signed magnitude: " + result);
                } else {
                    System.out.println("Invalid choice. Please enter '1', '2', or 'q'.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
