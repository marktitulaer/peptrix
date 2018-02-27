package pharmaceuticals.nl.peptrix.createpeaklist;

public class Base64Coder {

	Base64StringToBytes base64_string_to_bytes;

	Base64BytesToNumbers base64_bytes_to_numbers;

	int outputcount = 0;

	byte[] input_output_bytes;

	Base64Coder() {
		base64_string_to_bytes = new Base64StringToBytes();
		base64_bytes_to_numbers = new Base64BytesToNumbers();
	}

	public Spectrum decode(String Base64_data, String precision, String Byteorder) {
		Spectrum spectrum = null;
		input_output_bytes = Base64_data.getBytes();
		if (input_output_bytes.length % 4 == 0) {
			outputcount = base64_string_to_bytes.ConvertStringToBytes(input_output_bytes);
			if (precision.equalsIgnoreCase("32") && Byteorder.equalsIgnoreCase("network")) {
				spectrum = base64_bytes_to_numbers.convert_single_precision_byteorder_network(outputcount,
						input_output_bytes);
			}
			if (precision.equalsIgnoreCase("64") && Byteorder.equalsIgnoreCase("network")) {
				spectrum = base64_bytes_to_numbers.convert_double_precision_byteorder_network(outputcount,
						input_output_bytes);
			}
		}
		return spectrum;
	}
}
