package regm.wsdlbuilder;

import java.io.File;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Command line interface to wsdl-builder.
 * 
 * @author Gunnar Morling
 * 
 */
public class WsdlBuilder {

	private static Logger logger = Logger.getLogger(WsdlBuilder.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// create the CLI options
		Options options = new Options();

		for (Option oneOption : CliOptions.getAllOptions()) {
			options.addOption(oneOption);
		}

		// parse the command line input
		CommandLineParser parser = new GnuParser();

		CommandLine line = null;
		try {
			line = parser.parse(options, args);
		}
		catch (ParseException e) {
			logger.severe("Couldn't parse command line arguments. Reason: " + e.getMessage());
			return;
		}

		if (line.hasOption(CliOptions.HELP.getOpt())) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("wsdlbuilder", options);
			return;
		}
		// validate presence of required options
		for (Option property : CliOptions.getAllOptions()) {
			if (property.isRequired() && !line.hasOption(property.getOpt())) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("wsdlbuilder", options);
				return;
			}
		}

		// set up the converter with provided options
		SchemaToWsdlConverter converter = new SchemaToWsdlConverter();

		if (line.hasOption(CliOptions.XSD_DIRECTORY.getOpt())) {
			converter.setXsdDirectory(new File(line.getOptionValue(CliOptions.XSD_DIRECTORY
				.getOpt())));
		}

		if (line.hasOption(CliOptions.WSDL_DIRECTORY.getOpt())) {
			converter.setWsdlDestDirectory(new File(line.getOptionValue(CliOptions.WSDL_DIRECTORY
				.getOpt())));
		}

		// do the actual conversion
		try {
			converter.convert();
		}
		catch (SchemaConversionException e) {
			logger.severe("Schema conversion failed. Reason: " + e.getMessage() + ". Cause: "
				+ e.getCause());
		}

	}

}
