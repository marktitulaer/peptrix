package pharmaceuticals.nl.peptrix.createpeaklist;

public class Base64BytesToNumbers {

    StringBuffer binary_string_buffer;

    StringBuffer binary_string_buffer2;

    String mantissastring = "";

    String exponentstring = "";

    String binary_string = "";

    String binary_string2 = "";

    double value = 0;

    double mantissa = 1;

    double double_exponent;

    int sign = 0;

    int num;

    int num2;

    int numberofvalues;

    int count;

    int totalpeakscount;

    public Spectrum convert_single_precision_byteorder_network(int outputcount, byte[] input_output_bytes) {
        count = 0;
        totalpeakscount = 0;
        numberofvalues = outputcount / 4;
        totalpeakscount = numberofvalues / 2;
        Spectrum spectrum = new Spectrum(totalpeakscount);
        for (int i = 0; i < outputcount; i++) {
            if (i % 4 == 0) {
                sign = 0;
                double_exponent = 0;
                mantissa = 1;
                num = ((input_output_bytes[i] << 24) & 0xFF000000) | ((input_output_bytes[i + 1] << 16) & 0xFF0000)
                        | ((input_output_bytes[i + 2] << 8) & 0xFF00) | (input_output_bytes[i + 3] & 0xFF);
                binary_string = Integer.toBinaryString(num) + "";
                binary_string_buffer = new StringBuffer();
                for (int j = 0; j < (32 - binary_string.length()); j++) {
                    binary_string_buffer.append("0");
                }
                binary_string_buffer.append(binary_string);
                binary_string = binary_string_buffer.toString().trim();
                if (binary_string.indexOf("1") >= 0) {
                    sign = Integer.parseInt(String.valueOf(binary_string.charAt(0)));
                    exponentstring = binary_string.substring(1, 9);
                    for (int j = 0; j < exponentstring.length(); j++) {
                        if (exponentstring.charAt(exponentstring.length() - 1 - j) == 49) {
                            double_exponent = double_exponent + Math.pow(2, j);
                        }
                    }
                    double_exponent = double_exponent - 127;
                    mantissastring = binary_string.substring(9, 32);
                    for (int j = 0; j < mantissastring.length(); j++) {
                        if (mantissastring.charAt(j) == 49) {
                            mantissa = mantissa + (1 / Math.pow(2, (j + 1)));
                        }
                    }
                    value = Math.pow(-1, sign) * Math.pow(2, double_exponent) * mantissa;
                } else {
                    value = 0;
                }
                if (i % 8 == 0) {
                    spectrum.mass[count] = value;
                } else {
                    spectrum.intensity[count] = value;
                    count++;
                }
            }
        }
        return spectrum;
    }

    public Spectrum convert_double_precision_byteorder_network(int outputcount, byte[] input_output_bytes) {
        count = 0;
        totalpeakscount = 0;
        numberofvalues = outputcount / 8;
        totalpeakscount = numberofvalues / 2;
        Spectrum spectrum = new Spectrum(totalpeakscount);
        for (int i = 0; i < outputcount; i++) {
            if (i % 8 == 0) {
                sign = 0;
                double_exponent = 0;
                mantissa = 1;
                num = ((input_output_bytes[i] << 24) & 0xFF000000) | ((input_output_bytes[i + 1] << 16) & 0xFF0000)
                        | ((input_output_bytes[i + 2] << 8) & 0xFF00) | (input_output_bytes[i + 3] & 0xFF);
                num2 = ((input_output_bytes[i + 4] << 24) & 0xFF000000) | ((input_output_bytes[i + 5] << 16) & 0xFF0000)
                        | ((input_output_bytes[i + 6] << 8) & 0xFF00) | (input_output_bytes[i + 7] & 0xFF);
                binary_string = Integer.toBinaryString(num) + "";
                binary_string_buffer = new StringBuffer();
                for (int j = 0; j < (32 - binary_string.length()); j++) {
                    binary_string_buffer.append("0");
                }
                binary_string_buffer.append(binary_string);
                binary_string2 = Integer.toBinaryString(num2) + "";
                binary_string_buffer2 = new StringBuffer();
                for (int j = 0; j < (32 - binary_string2.length()); j++) {
                    binary_string_buffer2.append("0");
                }
                binary_string_buffer2.append(binary_string2);
                binary_string = binary_string_buffer.toString().trim() + binary_string_buffer2.toString().trim();
                if (binary_string.indexOf("1") >= 0) {
                    sign = Integer.parseInt(String.valueOf(binary_string.charAt(0)));
                    exponentstring = binary_string.substring(1, 12);
                    for (int j = 0; j < exponentstring.length(); j++) {
                        if (exponentstring.charAt(exponentstring.length() - 1 - j) == 49) {
                            double_exponent = double_exponent + Math.pow(2, j);
                        }
                    }
                    double_exponent = double_exponent - 1023;
                    mantissastring = binary_string.substring(12, 64);
                    for (int j = 0; j < mantissastring.length(); j++) {
                        if (mantissastring.charAt(j) == 49) {
                            mantissa = mantissa + (1 / Math.pow(2, (j + 1)));
                        }
                    }
                    value = Math.pow(-1, sign) * Math.pow(2, double_exponent) * mantissa;
                } else {
                    value = 0;
                }
                if (i % 16 == 0) {
                    spectrum.mass[count] = value;
                } else {
                    spectrum.intensity[count] = value;
                    count++;
                }
            }
        }
        return spectrum;
    }

}
