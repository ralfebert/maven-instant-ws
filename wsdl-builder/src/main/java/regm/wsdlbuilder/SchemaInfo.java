package regm.wsdlbuilder;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class SchemaInfo {

	private static final String NAMESPACE_SUFFIX = "/types";

	private static final Pattern REQUEST_NAME_PATTERN = Pattern.compile("(.+)(Request)");

	private static final Pattern RESPONSE_NAME_PATTERN = Pattern.compile("(.+)(Response)");

	private static final Pattern FAULT_NAME_PATTERN = Pattern.compile("(.+)Fault");

	private Document doc;

	private final String targetNamespaceURI;

	private String name;

	private final Map<String, Operation> operationsByName;

	@SuppressWarnings("unchecked")
	public SchemaInfo(InputStream xsd) {

		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(xsd);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		targetNamespaceURI = doc.getRootElement().getAttributeValue("targetNamespace");
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
					+ "\": Namespace URIs for maven-wsdl-builder-plugin web service xsds need to end with "
					+ NAMESPACE_SUFFIX);
		}

		name = targetNamespaceURI;
		name = name.substring(0, name.length() - NAMESPACE_SUFFIX.length());
		name = name.substring(name.lastIndexOf('/') + 1);
		name = StringUtils.capitalize(name);

		operationsByName = new HashMap<String, Operation>();
		for (Element element : (List<Element>) doc.getRootElement().getChildren("element",
			doc.getRootElement().getNamespace())) {

			String name = element.getAttributeValue("name");

			Matcher matcher = REQUEST_NAME_PATTERN.matcher(name);

			if (matcher.matches()) {
				String opName = matcher.group(1);

				Operation operation = operationsByName.get(opName);
				if (operation == null) {
					operation = new Operation();
					operation.setName(opName);
					operationsByName.put(opName, operation);
				}
				operation.setHasParameter(true);
			}

			matcher = RESPONSE_NAME_PATTERN.matcher(name);

			if (matcher.matches()) {
				String opName = matcher.group(1);

				Operation operation = operationsByName.get(opName);
				if (operation == null) {
					operation = new Operation();
					operation.setName(opName);
					operationsByName.put(opName, operation);
				}
				operation.setHasReturnValue(true);
			}

			matcher = FAULT_NAME_PATTERN.matcher(name);

			if (matcher.matches()) {
				String opName = matcher.group(1);

				Operation operation = operationsByName.get(opName);
				if (operation == null) {
					operation = new Operation();
					operation.setName(opName);
					operationsByName.put(opName, operation);
				}
				operation.setHasFault(true);
			}

		}
	}

	public Set<Operation> getOperations() {

		return new HashSet<Operation>(operationsByName.values());
	}

	public String getName() {

		return name;
	}

	public String getTypesNamespace() {

		return targetNamespaceURI;
	}
}
