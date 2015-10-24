package com.seimos.commons.web.formbuilder;

/**
 * @author moesio @ gmail.com
 * @date Dec 20, 2014 7:35:53 AM
 */
public class SelectOption {
	private String value;
	private String text;

	public String getValue() {
		return value;
	}

	public SelectOption setValue(String value) {
		this.value = value;
		return this;
	}

	public String getText() {
		return text;
	}

	public SelectOption setText(String label) {
		this.text = label;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value + " - " + text;
	}
}
