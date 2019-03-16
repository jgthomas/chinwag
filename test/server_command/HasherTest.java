package server_command;

import org.junit.Test;
import static org.junit.Assert.*;


public class HasherTest {

        @Test
        public void compareTwoRuns() {
                // salt and password
                String password = "monkey123";
                byte[] salt = Hasher.genSalt();

                // hash the combination once
                String hashedWithSalt_1 = Hasher.hashPassword(password, salt);

                // hash the combination twice
                String hashedWithSalt_2 = Hasher.hashPassword(password, salt);

                // hashes should match
                assertEquals(hashedWithSalt_1, hashedWithSalt_2);
        }

        @Test
        public void compareSingleSaltAsString() {
                // make a salt
                byte[] salt = Hasher.genSalt();

                // convert to string once
                String saltString_1 = Hasher.bytesToString(salt);

                // convert to string again
                String saltString_2 = Hasher.bytesToString(salt);

                // two strings should match
                assertEquals(saltString_1, saltString_2);
        }

        @Test
        public void hashBytesToStringAndBack() {
                // password and salt
                String password = "letmein";
                byte[] salt = Hasher.genSalt();

                // get hash as bytes
                byte[] hashBytes = Hasher.hashPasswordBytes(password, salt);

                // get hash as string
                String hashString = Hasher.hashPassword(password, salt);

                // convert byte hash to string
                String converted = Hasher.bytesToString(hashBytes);

                // should match direct creation of string hash
                assertEquals(hashString, converted);
        }

        @Test
        public void saltBytesToStringAndBack() {
                // make a salt
                byte[] salt = Hasher.genSalt();

                // convert it to string
                String saltString = Hasher.bytesToString(salt);

                // convert that back to byte array
                byte[] saltBackToBytes = Hasher.stringToBytes(saltString);

                // should be same as before
                assertArrayEquals(salt, saltBackToBytes);
        }
}
