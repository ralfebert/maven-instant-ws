package regm.wsdlbuilder;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;

public class SchemaToWsdlConverterTest {
	
	private String expectedWsdl;
	
	private SchemaToWsdlConverter converter;
	
	@Before
	public void setup() throws Exception {

		XMLUnit.setIgnoreWhitespace(true);
		
		File expectedWsdlFile = new File("src/test/resources/regm/wsdlbuilder/expected.wsdl");
		assertTrue("Expected WSDL file wasn't found: " + expectedWsdlFile, expectedWsdlFile.exists());
		expectedWsdl = FileUtils.readFileToString(expectedWsdlFile);
		
		converter = new SchemaToWsdlConverter();
		converter.setXsdDirectory(new File("src/test/resources/regm/wsdlbuilder"));
		converter.setWsdlDestDirectory(new File("target/test"));
		converter.setForceRegeneration(true);
	}

	@Test
	public void testConvert() throws Exception {
		
		converter.convert();

	    File actualWsdlFile = new File("target/test/calendertypes.wsdl");
		assertTrue("Generated WSDL file wasn't found: " + actualWsdlFile, actualWsdlFile.exists());
		String actualWsdl = FileUtils.readFileToString(actualWsdlFile);
		
	    Diff diff = new Diff(expectedWsdl, actualWsdl);
	    assertTrue("Generated WSDL differs from expected one: " + diff.toString(), diff.identical());
	}	

}
