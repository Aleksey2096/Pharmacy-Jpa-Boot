package by.academy.pharmacy2.service.util;

import org.junit.jupiter.api.Test;

import static by.academy.pharmacy2.TestConstant.STRONG_PASSWORD;
import static by.academy.pharmacy2.TestConstant.UPDATED_STRING;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordUtilTest {
	private final PasswordUtil passwordUtil = new PasswordUtil();

	@Test
	public void testPasswordHashingPositive() {
		String hashedPassword = passwordUtil.encode(STRONG_PASSWORD);
		assertTrue(passwordUtil.matches(STRONG_PASSWORD, hashedPassword));
	}

	@Test
	public void testPasswordHashingNegative() {
		String hashedPassword = passwordUtil.encode(STRONG_PASSWORD);
		assertFalse(passwordUtil.matches(STRONG_PASSWORD + UPDATED_STRING, hashedPassword));
	}
}
