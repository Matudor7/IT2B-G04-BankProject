package nl.inholland.it2bank.service;
import java.math.BigInteger;
import java.util.Random;

public class IBANGenerator {
    public String generateRandomIBAN() {
        String countryCode = "NL";
        // Generate a random BBAN (Basic Bank Account Number)
        String bban = generateRandomBBAN();

        // Combine the country code and the BBAN to form the IBAN
        String iban = countryCode + "00" + bban;

        // Calculate the checksum using the MOD-97 algorithm
        int checksum = calculateChecksum(iban);

        // Format the IBAN with the correct checksum
        String formattedIban = countryCode + String.format("%02d", checksum) + bban;

        return formattedIban;
    }

    private static String generateRandomBBAN() {
        Random random = new Random();
        StringBuilder bbanBuilder = new StringBuilder();

        // Generate a random string of digits for the BBAN
        for (int i = 0; i < 14; i++) {
            int digit = random.nextInt(10);
            bbanBuilder.append(digit);
        }

        return bbanBuilder.toString();
    }

    private static int calculateChecksum(String iban) {
        String modifiedIban = iban.substring(4) + iban.substring(0, 2) + "00";
        BigInteger ibanNumber = new BigInteger(modifiedIban);
        BigInteger checksum = ibanNumber.mod(BigInteger.valueOf(97));

        return 98 - checksum.intValue();
    }
}


