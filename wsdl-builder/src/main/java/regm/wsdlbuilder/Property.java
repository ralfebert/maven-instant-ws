package regm.wsdlbuilder;

public class Property {

	private String shortText;
	
	private String longText;
	
	private boolean argumentRequired;
	
	private String description;
	
	private boolean required;

	public Property(String shortText, String longText, boolean argumentRequired, String description,
			 boolean required) {
		super();
		this.argumentRequired = argumentRequired;
		this.description = description;
		this.longText = longText;
		this.required = required;
		this.shortText = shortText;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getLongText() {
		return longText;
	}

	public void setLongText(String longText) {
		this.longText = longText;
	}

	public boolean isArgumentRequired() {
		return argumentRequired;
	}

	public void setArgumentRequired(boolean argumentRequired) {
		this.argumentRequired = argumentRequired;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
	
}
