package regm;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import regm.wsdlbuilder.SchemaConversionException;
import regm.wsdlbuilder.SchemaToWsdlConverter;

public class WsdlBuilderTask extends Task {

	private String xsdDirectory = ".";

	private String wsdlDirectory = ".";

	public void execute() {

		SchemaToWsdlConverter converter = new SchemaToWsdlConverter();

		converter.setXsdDirectory(new File(xsdDirectory));
		converter.setWsdlDestDirectory(new File(wsdlDirectory));

		try {
			converter.convert();
		}
		catch (SchemaConversionException e) {
			throw new BuildException("Schemas couldn't be converted.", e);
		}
	}

	public void setXsdDirectory(String xsdDirectory) {

		this.xsdDirectory = xsdDirectory;
	}

	public void setWsdlDirectory(String wsdlDirectory) {

		this.wsdlDirectory = wsdlDirectory;
	}

}
