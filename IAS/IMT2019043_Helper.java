package IAS;
class Helper
{

    static int binToDec(String number)
    {
        // this is function to convert a unsigned binary number to its decimal equivalent
        // it will be used to convert addresses to decimal memory locations
        int decimal = 0;
        int n = 0;
        for (int i = number.length() - 1 ; i >= 0; i--) // start from the LSB
        {
            decimal += Character.getNumericValue(number.charAt(i)) * (int) Math.pow(2, n);
            n++;
        }
        return decimal;
    }


    static int sign_binToDec(String number)
    {
        // this is a function to convert a sign-magnitude binary number to its decimal equivalent
        // it will be used to convert data words to decimal numbers, for computation purposes
        if (number.length() == 0)
            return 0;

        int sign = Integer.parseInt(String.valueOf(number.charAt(0))); // sign bit
        int decimal = 0;
        int n = 0;
        for (int i = number.length() - 1 ; i >= 1; i--) // start from the LSB
        {
            decimal += Character.getNumericValue(number.charAt(i)) * (int) Math.pow(2, n);
            n++;
        }

        if (sign == 0) // positive number
            return decimal;
        else // negative number
            return -decimal;
    }


    static String decToBin(int number)
    {
        // this is a function to convert a decimal number to its unsigned binary equivalent
        // it will be used to convert decimal numbers to addresses
        String binary;
        StringBuilder smBinary = new StringBuilder();
        binary = Integer.toBinaryString(number);
        smBinary.append("0".repeat(Math.max(0, 12 - binary.length())));
        return (smBinary + binary);
    }

    static String decToSign_bin(int number)
    {
        // this is a function to convert a decimal number to its sign-magnitude binary equivalent
        // it will be used to convert decimal numbers to data words
        String binary;
        StringBuilder smBinary = new StringBuilder();
        if (number > 0) // positive number
        {
            binary = Integer.toBinaryString(number);
            smBinary.append("0".repeat(40 - binary.length()));
            return (smBinary + binary);
        }
        else // negative number
        {
            binary = Integer.toBinaryString(-number);
            smBinary.append("0".repeat(40 - binary.length() - 1));
            return ("1" + smBinary + binary);
        }
    }
}