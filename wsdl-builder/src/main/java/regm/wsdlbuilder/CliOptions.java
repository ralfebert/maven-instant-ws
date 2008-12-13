package regm.wsdlbuilder;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.Option;

/**
 * The options, which can be specified using the command line interface to WSDL
 * builder.
 * 
 * @author Gunnar Morling
 *
 */
public class CliOptions {

	public final static Option XSD_DIRECTORY = new Option("x", "xsdDirectory", true,
		"Directory containing XSD files to process. Defaults to currrent working directory.");

	public final static Option WSDL_DIRECTORY = new Option(
		"w",
		"wsdlDirectory",
		true,
		"Directory, to which the generated WSDL files shall be written. Defaults to currrent working directory.");

	public final static Option HELP = new Option("h", "help", false,
		"Displays this help text.");

	private final static List<Option> allOptions;

	static {
		allOptions = Arrays.asList(XSD_DIRECTORY, WSDL_DIRECTORY, HELP);
	}

	public final static List<Option> getAllOptions() {

		return allOptions;
	}

}
