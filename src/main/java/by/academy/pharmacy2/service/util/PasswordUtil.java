package by.academy.pharmacy2.service.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordUtil implements PasswordEncoder {
    /**
     * Generates a user-specified number of random bytes.
     */
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    /**
     * Indicates how many iterations that this algorithm run for, increasing the
     * time it takes to produce the hash.
     */
    private static final int ITERATIONS = 100000;
    /**
     * Output length of the hashed function.
     */
    private static final int KEY_LENGTH = 128;
    /**
     * Indicates the number of bytes in salt array.
     */
    private static final int SALT_SIZE = 16;
    /**
     * The standard name of the requested secret-key algorithm.
     */
    private static final String PBKDF_2_WITH_HMAC_SHA_1 = "PBKDF2WithHmacSHA1";

    /**
     * Creates combination of random salt plus hashed password.
     *
     * @param password initial password input by user.
     * @return hashed version of the password.
     */
    @Override
    public String encode(final CharSequence password) {
        char[] chars = password.toString().toCharArray();
        byte[] salt = new byte[SALT_SIZE];
        SECURE_RANDOM.nextBytes(salt);
        byte[] hash = generateHash(chars, salt);
        return Hex.encodeHexString(salt) + Hex.encodeHexString(hash);
    }

    /**
     * Checks whether input password is right or not.
     *
     * @param inputPassword  last version of the password input by user.
     * @param storedPassword initial password which hashed version was saved in
     *                       database.
     * @return true if inputPassword is the same as initial password.
     */
    @Override
    public boolean matches(final CharSequence inputPassword, final String storedPassword) {
        try {
            byte[] salt = Hex.decodeHex(storedPassword.substring(0, storedPassword.length() / 2));
            byte[] hash = Hex.decodeHex(storedPassword.substring(storedPassword.length() / 2));
            byte[] testHash = generateHash(inputPassword.toString().toCharArray(), salt);
            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0;
        } catch (DecoderException newE) {
            newE.printStackTrace();
            return false;
        }
    }

    private byte[] generateHash(final char[] newChars, final byte[] salt) {
        byte[] hash = null;
        try {
            PBEKeySpec spec = new PBEKeySpec(newChars, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF_2_WITH_HMAC_SHA_1);
            hash = skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException newE) {
            newE.printStackTrace();
        }
        return hash;
    }
}
