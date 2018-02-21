package pharmaceuticals.nl.peptrix.createpeaklist;

public class Base64StringToBytes {

    String base64chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    int inputlength;

    int outputcount = 0;

    int base64_1;

    int base64_2;

    int base64_3;

    int base64_4;

    public int ConvertStringToBytes(byte[] input_output_bytes) {
        inputlength = input_output_bytes.length;
        for (int i = 0; i < inputlength; i++) {
            if (i % 4 == 0) {
                outputcount = 3 * i / 4;
                base64_1 = base64chars.indexOf((char) input_output_bytes[i]);
                base64_2 = base64chars.indexOf((char) input_output_bytes[i + 1]);
                base64_3 = base64chars.indexOf((char) input_output_bytes[i + 2]);
                base64_4 = base64chars.indexOf((char) input_output_bytes[i + 3]);
                input_output_bytes[outputcount] = (byte) (((base64_1 << 2) & 0xfc) | ((base64_2 >> 4) & 0x03));
                outputcount++;
                input_output_bytes[outputcount] = (byte) (((base64_2 << 4) & 0xf0) | ((base64_3 >> 2) & 0x0f));
                outputcount++;
                input_output_bytes[outputcount] = (byte) (((base64_3 << 6) & 0xc0) | (base64_4 & 0x3f));
                if (String.valueOf((char) input_output_bytes[i + 3]).equalsIgnoreCase("=")) {
                    outputcount--;
                }
                if (String.valueOf((char) input_output_bytes[i + 2]).equalsIgnoreCase("=")) {
                    outputcount--;
                }
            }
        }
        if ((outputcount + 1) % 4 == 0) {
            outputcount++;
        } else {
            outputcount = 0;
        }
        return outputcount;
    }
}
