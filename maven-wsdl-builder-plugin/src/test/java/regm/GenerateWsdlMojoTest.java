package regm;

import java.io.File;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GenerateWsdlMojoTest extends AbstractMojoTestCase {

	private GenerateWsdlMojo mojo;

	@Before
	public void setUp() throws Exception {

		super.setUp();

		File testPom = new File(getBasedir(), "src/test/resources/plugin-config.xml");
		mojo = (GenerateWsdlMojo) lookupMojo("generate-wsdl", testPom);
		assertNotNull(mojo);
	}

	@After
	public void tearDown() throws Exception {

	}

	/**
	 * Executes the XSD -> WSDL conversion. Verifications are done using the
	 * verification plugin.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testExecute() throws Exception {

		mojo.execute();
	}

}
