package regm.wsdlbuilder;

/**
 * Model class, that represents one operation of a web service.
 * 
 * @author Gunnar Morling
 *
 */
public class Operation {

	/**
	 * The name of the operation, mandatory.
	 */
	private String name;

	private boolean hasParameter;

	/**
	 * Whether the operation has a return value or not.
	 */
	private boolean hasReturnValue;

	/**
	 * Whether the operation declares a fault or not.
	 */
	private boolean hasFault;

	public Operation() {

	}

	public Operation(String name, boolean hasParameter, boolean hasReturnValue, boolean hasFault) {

		super();
		this.hasFault = hasFault;
		this.hasParameter = hasParameter;
		this.hasReturnValue = hasReturnValue;
		this.name = name;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public boolean isHasParameter() {

		return hasParameter;
	}

	public void setHasParameter(boolean hasParameter) {

		this.hasParameter = hasParameter;
	}

	public boolean isHasReturnValue() {

		return hasReturnValue;
	}

	public void setHasReturnValue(boolean hasReturnValue) {

		this.hasReturnValue = hasReturnValue;
	}

	public boolean isHasFault() {

		return hasFault;
	}

	public void setHasFault(boolean hasFault) {

		this.hasFault = hasFault;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + (hasFault ? 1231 : 1237);
		result = prime * result + (hasParameter ? 1231 : 1237);
		result = prime * result + (hasReturnValue ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (hasFault != other.hasFault)
			return false;
		if (hasParameter != other.hasParameter)
			return false;
		if (hasReturnValue != other.hasReturnValue)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

}
