package regm.wsdlbuilder;

import java.util.Arrays;
import java.util.List;

public class Properties {

	public final static Property XSD_DIRECTORY = new Property("x", "xsdDirectory", true,
		"Directory containing XSD files to process. Defaults to currrent working directory.", false);

	public final static Property WSDL_DIRECTORY = new Property(
		"w",
		"wsdlDirectory",
		true,
		"Directory, to which the generated WSDL files shall be written. Defaults to currrent working directory.",
		false);

	public final static Property HELP = new Property("h", "help", false,
		"Displays this help text.", false);

	private final static List<Property> allProperties;

	static {
		allProperties = Arrays.asList(XSD_DIRECTORY, WSDL_DIRECTORY, HELP);
	}

	public final static List<Property> getAllProperties() {

		return allProperties;
	}
}
