package org.tech.demo.amazon.persistence.model;

import org.junit.Test;
import org.meanbean.test.BeanTester;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/**
 * Use MeanBean Bean Tester for getter/setter testing
 * Use EqualsVerifier for equals/hashcode verification
 * Arg Const must be tested manually.
 *
 */
public class FileUploadInfoTest {

	@Test
	public void testPojo() {
		new BeanTester().testBean(FileUploadInfo.class);
		EqualsVerifier.forClass(FileUploadInfo.class).withIgnoredFields("id").suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
	}

}
