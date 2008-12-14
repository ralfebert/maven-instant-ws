package regm.wsdlbuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class SchemaInfoLoaderTest {

	private SchemaInfo schema;

	@Before
	public void setup() {

		InputStream schemaFile = SchemaInfoLoaderTest.class.getResourceAsStream("test.xsd");
		assertNotNull(schemaFile);
		
		schema = new SchemaInfoLoader().getSchemaInfo(schemaFile);
	}

	@Test
	public void testGetOperations() {

		assertEquals(4, schema.getOperations().size());
		
		assertTrue(schema.getOperations().contains(new Operation("ParamAndReturnValueAndFaultTest", true, true, true, false)));
		assertTrue(schema.getOperations().contains(new Operation("ParamAndReturnValueTest", true, true, false, false)));
		assertTrue(schema.getOperations().contains(new Operation("NoReturnValueTest", true, false, false, false)));
		assertTrue(schema.getOperations().contains(new Operation("DocLitWrappedTest", true, true, false, true)));
	}

	@Test
	public void testGetName() {

		assertEquals("Test", schema.getName());
	}

	@Test
	public void testGetTargetNamespaceURI() {

		assertEquals("http://www.javamagazin.de/mvnjaxws/test/types", schema.getTargetNamespaceURI());
	}

}
