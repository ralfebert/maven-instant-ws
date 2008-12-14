package regm.wsdlbuilder;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SchemaInfoLoader {

	private Logger logger = Logger.getLogger(SchemaInfo.class.getName());
	
	private static final String NAMESPACE_SUFFIX = "/types";

	private static final Pattern REQUEST_NAME_PATTERN = Pattern.compile("(.+)(Request)");
	
	private static final Pattern RESPONSE_NAME_PATTERN = Pattern.compile("(.+)(Response)");

	private static final Pattern FAULT_NAME_PATTERN = Pattern.compile("(.+)Fault");
	
	/**
	 * Returns a schema info representing the specified XSD.
	 * 
	 * @param xsd An input stream containing the contents of an XSD file.
	 * 
	 * @return A schema info.
	 */
	public SchemaInfo getSchemaInfo(InputStream xsd) {
		
		Document doc = null;
		
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(xsd);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		String targetNamespaceURI = getTargetNamespace(doc);
		String name = getName(targetNamespaceURI);
		Set<Operation> operations = getOperations(doc);
		
		SchemaInfo theValue = new SchemaInfo();
		
		theValue.setTargetNamespaceURI(targetNamespaceURI);
		theValue.setName(name);
		theValue.setOperations(operations);
		
		return theValue;		
	}

	private Set<Operation> getOperations(Document doc) {
		Map<String, Operation> operationsByName = 
			new LinkedHashMap<String, Operation>();
		
		@SuppressWarnings("unchecked")
		List<Element> elements = 
			(List<Element>)doc.getRootElement().getChildren(
				"element",
				doc.getRootElement().getNamespace());
		
		for (Element element : elements) {

			String elementName = element.getAttributeValue("name");

			Matcher requestNameMatcher = REQUEST_NAME_PATTERN.matcher(elementName);
			Matcher responseNameMatcher = RESPONSE_NAME_PATTERN.matcher(elementName);
			Matcher faultNameMatcher = FAULT_NAME_PATTERN.matcher(elementName);
			
			if (requestNameMatcher.matches()) {
				
				Operation operation = 
					createOperationIfAbsent(operationsByName, requestNameMatcher.group(1));
				
				operation.setHasParameter(true);
				operation.setInputElementNameSameAsOperationName(false);
			}
			else if(!responseNameMatcher.matches() && !faultNameMatcher.matches()) {
				
				Operation operation = 
					createOperationIfAbsent(operationsByName, elementName);
				
				operation.setHasParameter(true);
				operation.setInputElementNameSameAsOperationName(true);
			}

			if (responseNameMatcher.matches()) {
				Operation operation = 
					createOperationIfAbsent(operationsByName, responseNameMatcher.group(1));
				
				operation.setHasReturnValue(true);
			}

			if (faultNameMatcher.matches()) {
				Operation operation = 
					createOperationIfAbsent(operationsByName, faultNameMatcher.group(1));
				
				operation.setHasFault(true);
			}

		}
		
		Set<Operation> theValue = new LinkedHashSet<Operation>();
		
		for (Operation operation : operationsByName.values()) {
			if(operation.hasParameter()) {
				theValue.add(operation);
			}
			else {
				logger.warning("Ignoring operation " + operation + ", since no input element for it was found.");
			}
		}
		
		return theValue;
	}

	private String getName(String targetNamespaceURI) {
		
		String name = targetNamespaceURI;
		
		name = name.substring(0, name.length() - NAMESPACE_SUFFIX.length());
		name = name.substring(name.lastIndexOf('/') + 1);
		name = StringUtils.capitalize(name);
		
		return name;
	}

	private String getTargetNamespace(Document doc) {
		
		String targetNamespaceURI = doc.getRootElement().getAttributeValue("targetNamespace");
		
		try {
			new URI(targetNamespaceURI);
		}
		catch (URISyntaxException e) {
			new RuntimeException(e);
		}
		
		if (!targetNamespaceURI.endsWith(NAMESPACE_SUFFIX)) {
			throw new RuntimeException(
				"Invalid namespace \""
					+ targetNamespaceURI
					+ "\": Namespace URIs for Wsdl Builder web service XSDs need to end with "
					+ NAMESPACE_SUFFIX);
		}
		
		return targetNamespaceURI;
	}

	private Operation createOperationIfAbsent(
		Map<String, Operation> operationsByName, String opName) {
		
		Operation operation = operationsByName.get(opName);
		
		if (operation == null) {
			operation = new Operation();
			operation.setName(opName);
			operationsByName.put(opName, operation);
		}
		
		return operation;
	}
}
