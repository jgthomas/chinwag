package server_command;

import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;


/**
 * References
 *
 * (1) Basic guidance on hashing safely in Java
 *
 * https://www.owasp.org/index.php/Hashing_Java
 *
 *
 * (2) Hashing with salts correctly
 *
 * https://crackstation.net/hashing-security.htm
 *
 *
 * (3) Converting between byte arrays and string
 *
 * https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa
 *
 *
 * To STORE a password - Adapted from (2)
 *
 * - Generate a long random salt
 *
 * # byte[] salt = Hasher.genSalt()
 *
 * - Get the hash of the combined password and salt
 *
 * # String hash = Hasher.hashPassword(password, salt)
 *
 * - Save BOTH the salt and the hash in the user's database record
 * # String saltString = Hasher.bytesToString(salt) // easier to save as string
 * # INSERT INTO table (username,hash,salt) etc
 *
 * - DO NOT store the password itself
 *
 *
 * To VALIDATE a password - Adapted from (2)
 *
 * - Retrieve both the salt and the hash from the database
 *
 * - Convert the salt back to a byte array
 * # byte[] saltFromDb = Hasher.stringToBytes(saltRetrievedFromDb)
 *
 * - Get the hash of the password the user entered, and the salt just
 *   retrieved
 * # String hashTest = Hasher.hashPassword(userEnteredPassword, saltFromDb)
 *
 * - Compare the hash you just made with the hash you retrieved from
 *   the database
 *
 * if (hashRetrievedFromDb.equals(hashTest)) {
 *     allow login
 * } else {
 *     GTFO
 * }
 *
 * */
public final class Hasher {
        /*
         * Nice answers here regarding the parts of the algorithm
         *
         * https://crypto.stackexchange.com/questions/35275/whats-the-difference-between-pbkdf-and-sha-and-why-use-them-together
         *
         */
        private static final String ALGORITHM = "PBKDF2WithHmacSHA512";

        /*
         * How many rounds of hashing the algorithm performs.
         *
         * The more of these we do, the slower it is for
         * an attacker attempting to brute-force the password
         * database. However, it is also slower for us to
         * do the original hashing with more iterations.
         *
         * I do not think 1000 iterations is considered secure
         * these days, so we should find a level that balances
         * performance and security.
         *
         * We should maybe store this too, as this has
         * to be the same on storage and retrieval for
         * any given hash.
         *
         */
        private static final int ITERATIONS = 1000;

        /*
         * The length of the key
         */
        private static final int KEY_LENGTH_BITS = 256;

        /*
         * The length of the salt
         */
        private static final int SALT_BYTES = 32;

        /**
         * Generates a hash for the combined password and salt
         *
         * @param password the user's password
         * @param salt a random array of byte to combine with the password to hash
         * @return an array of bytes representing the hashed password and salt
         *
         * */
        static byte[] hashPasswordBytes(String password, byte[] salt) {
                char[] passwordBytes = password.toCharArray();

                try {
                        SecretKeyFactory skf
                                = SecretKeyFactory.getInstance(ALGORITHM);

                        PBEKeySpec spec = new PBEKeySpec(
                                        passwordBytes,
                                        salt,
                                        ITERATIONS,
                                        KEY_LENGTH_BITS);

                        SecretKey key = skf.generateSecret(spec);
                        return key.getEncoded();
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                        throw new RuntimeException(e);
                }
        }

        /**
         * Generates the string representation of a hash for the
         * combined password and salt.
         *
         * This method is for convenience, as its easier to store
         * the hashes as strings in the database than as byte arrays
         *
         * @param password the user's password
         * @param salt a random array of byte to combine with the password to hash
         * @return a string representation of the hashed password and salt
         *
         * */
        static String hashPassword(String password, byte[] salt) {
                return Base64.getEncoder()
                        .encodeToString(hashPasswordBytes(password, salt));
        }

        /**
         * Generates a random byte array to use as a salt.
         *
         * See here for why salts are good
         *
         * https://en.wikipedia.org/wiki/Salt_(cryptography)
         *
         * */
        static byte[] genSalt() {
                SecureRandom random = new SecureRandom();
                byte[] saltBytes = new byte[SALT_BYTES];
                random.nextBytes(saltBytes);
                return saltBytes;
        }

        /**
         * Converts a byte array to a string.
         *
         * For easier storage in the database.
         *
         * @param salt a byte array representing a random salt
         * @return a string representation of the salt
         *
         * */
        static String bytesToString(byte[] salt) {
                return Base64.getEncoder().encodeToString(salt);
        }

        /**
         * Converts a string to a byte array.
         *
         * For when it needs to be in byte array format
         *
         * @param saltString a string representation of a salt
         * @return a byte array of the salt
         *
         * */
        static byte[] stringToBytes(String saltString) {
                return Base64.getDecoder().decode(saltString);
        }
}
