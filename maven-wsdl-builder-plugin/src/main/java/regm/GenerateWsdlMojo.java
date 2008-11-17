package regm;

import java.io.File;
import java.util.logging.Handler;
import java.util.logging.Logger;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import regm.wsdlbuilder.SchemaConversionException;
import regm.wsdlbuilder.SchemaToWsdlConverter;

/**
 * Goal which generates WSDL files for XSD files in a given folder.
 * 
 * @goal generate-wsdl
 * @phase generate-sources
 */
public class GenerateWsdlMojo extends AbstractMojo {

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${xsdDirectory}"
	 * @required
	 */
	private File xsdDirectory;

	/**
	 * Location of the file.
	 * 
	 * @parameter expression="${wsdlDestDirectory}"
	 * @required
	 */
	private File wsdlDestDirectory;

	public GenerateWsdlMojo() {

	}

	public void execute() throws MojoExecutionException {

		Handler handler = new MojoLoggingHandler(getLog());
		Logger logger = Logger.getLogger("regm");

		logger.addHandler(handler);
		logger.setUseParentHandlers(false);

		SchemaToWsdlConverter converter = new SchemaToWsdlConverter();

		converter.setXsdDirectory(xsdDirectory);
		converter.setWsdlDestDirectory(wsdlDestDirectory);

		try {
			converter.convert();
		}
		catch (SchemaConversionException e) {
			throw new MojoExecutionException("Schemas couldn't be converted.", e);
		}

		logger.setUseParentHandlers(true);
		logger.removeHandler(handler);
	}

}