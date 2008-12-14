package regm.wsdlbuilder;

import java.util.Set;

/**
 * A model class representing information about one schema (XSD) file, including
 * the schema name, the target namespace and the operation names derived from
 * the types defined within the schema.
 * 
 * @author Gunnar Morling
 * 
 */
public class SchemaInfo {

	private String name;

	private String targetNamespaceURI;
	
	private Set<Operation> operations;

	public void setTargetNamespaceURI(String targetNamespaceURI) {
		this.targetNamespaceURI = targetNamespaceURI;
	}
	
	public String getTargetNamespaceURI() {
		return targetNamespaceURI;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {

		return name;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	public Set<Operation> getOperations() {

		return operations;
	}

}