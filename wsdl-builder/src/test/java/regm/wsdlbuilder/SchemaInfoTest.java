package regm.wsdlbuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

public class SchemaInfoTest {

	private SchemaInfo schema;

	@Before
	public void setup() {

		InputStream schemaFile = SchemaInfoTest.class.getResourceAsStream("calendertypes.xsd");
		assertNotNull(schemaFile);
		schema = new SchemaInfo(schemaFile);
	}

	@Test
	public void testGetOperations() {

		assertTrue(schema.getOperations().contains(new Operation("GetDaysTill", true, true, true)));
		assertTrue(schema.getOperations().contains(new Operation("Other", true, true, false)));
		assertEquals(2, schema.getOperations().size());
	}

	@Test
	public void testGetName() {

		assertEquals("Calendar", schema.getName());
	}

	@Test
	public void testGetTypesNamespace() {

		assertEquals("http://www.javamagazin.de/mvnjaxws/calendar/types", schema
			.getTypesNamespace());
	}

}
