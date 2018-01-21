package com.seimos.commons.web.formbuilder.domain;

import javax.persistence.Embeddable;

/**
 * @author moesio
 * @date 2018-01-09 15:16:54
 *
 */
@Embeddable
public class Deep extends ExtendingDeep {
	private String another;

	public String getAnother() {
		return another;
	}

	public void setAnother(String another) {
		this.another = another;
	}
}