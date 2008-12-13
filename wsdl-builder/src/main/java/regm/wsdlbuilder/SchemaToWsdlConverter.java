package regm.wsdlbuilder;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.logging.Logger;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 * @author Gunnar Morling
 *
 */
public class SchemaToWsdlConverter {

	private Logger logger = Logger.getLogger(SchemaToWsdlConverter.class.getName());

	private File xsdDirectory;

	private File wsdlDestDirectory;

	/**
	 * If true, a WSDL for a given XSD will always be generated, also if this
	 * XSD wasn't modified since the last WSDL generation. 
	 */
	private boolean forceRegeneration = false;
	
	public SchemaToWsdlConverter() {

		xsdDirectory = new File(".");
		wsdlDestDirectory = new File(".");
	}

	public void convert() throws SchemaConversionException {

		if (!xsdDirectory.exists()) {
			logger.info("Specified input directory " + xsdDirectory.getAbsolutePath()
				+ " doesn't exist.");
			return;
		}

		File[] schemaFiles = xsdDirectory.listFiles((FileFilter) new SuffixFileFilter(".xsd"));

		if (schemaFiles == null || schemaFiles.length == 0) {
			logger.info("Nothing to do, no XSDs found in " + xsdDirectory.getAbsolutePath());
			return;
		}

		if (!wsdlDestDirectory.exists()) {
			wsdlDestDirectory.mkdirs();
		}

		StringTemplateGroup group = new StringTemplateGroup("generate-wsdl-templates");

		for (File schemaFile : schemaFiles) {

			try {

				if (!wsdlDestDirectory.equals(xsdDirectory)) {

					// don't do anything, if the XSD exists already in the
					// current state in the target dir
					File xsdInWsdlDirectory = new File(wsdlDestDirectory, schemaFile.getName());
					if (!forceRegeneration &&
						xsdInWsdlDirectory.exists()
						&& FileUtils.checksumCRC32(schemaFile) == FileUtils
							.checksumCRC32(xsdInWsdlDirectory)) {

						logger.info("WSDL for " + schemaFile + " already up to date.");
						continue;
					}
				}

				File wsdl = new File(wsdlDestDirectory, schemaFile.getName().replace(".xsd",
					".wsdl"));
				logger.info("Generating " + wsdl.getName() + " for " + schemaFile);

				SchemaInfo schemaInfo = new SchemaInfo(new FileInputStream(schemaFile));

				StringTemplate wsdlTemplate = group.getInstanceOf("templates/wsdl");
				wsdlTemplate.setAttribute("name", schemaInfo.getName());
				wsdlTemplate.setAttribute("servicePrefix", schemaInfo.getName());
				wsdlTemplate.setAttribute("operations", schemaInfo.getOperations());
				wsdlTemplate.setAttribute("typesNamespace", schemaInfo.getTypesNamespace());
				wsdlTemplate.setAttribute("serviceNamespace", schemaInfo.getTypesNamespace()
					.replace("/types", ""));
				wsdlTemplate.setAttribute("schemaFileName", schemaFile.getName());
				wsdlTemplate.setAttribute("typesNamespacePrefix", schemaInfo.getName()
					.toLowerCase());

				FileWriter w = new FileWriter(wsdl);
				w.write(wsdlTemplate.toString());
				w.close();

				if (!wsdlDestDirectory.equals(xsdDirectory)) {
					FileUtils.copyFileToDirectory(schemaFile, wsdlDestDirectory);
				}

			}
			catch (Exception e) {
				throw new SchemaConversionException("Error creating WSDL for " + schemaFile, e);
			}

		}
	}

	public void setXsdDirectory(File xsdDirectory) {

		this.xsdDirectory = xsdDirectory;
	}

	public void setWsdlDestDirectory(File wsdlDestDirectory) {

		this.wsdlDestDirectory = wsdlDestDirectory;
	}

	public void setForceRegeneration(boolean forceRegeneration) {
		this.forceRegeneration = forceRegeneration;
	}	

}
