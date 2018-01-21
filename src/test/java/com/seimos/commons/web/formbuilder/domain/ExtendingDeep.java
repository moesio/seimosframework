package com.seimos.commons.web.formbuilder.domain;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * @author moesio
 * @date 2018-01-10 22:24:15
 *
 */
@Embeddable
@MappedSuperclass
public class ExtendingDeep {
	private String superDeep;

	public String getSuperDeep() {
		return superDeep;
	}

	public void setSuperDeep(String superDeep) {
		this.superDeep = superDeep;
	}
}
