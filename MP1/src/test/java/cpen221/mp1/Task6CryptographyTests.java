package cpen221.mp1;


import cpen221.mp1.cryptanalysis.Cryptography;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

    public class Task6CryptographyTests {

        private static Document testDocument1;
        private static Document testDocument2;

        @BeforeAll
        public static void setupTests() throws MalformedURLException {
            testDocument1 = new Document("THE WOMEN", "resources/1sentence.txt");
            testDocument2 = new Document("THE WOMEN", "resources/brazen.txt");
        }

        /**
         * Cannot choose length of primes because period can only be 1 or length at that time
         **/

        /**If there exist a test that just false at 1 character in decryption, that test falls into a special case that
         * theres no way to fix. This can be change by adjusting period and amplitude.
         * Explaining special case:c_i+Asin(i * 2 * pi / period + pi / 4)=B1
         * We were given I(element of array) as an integer for that i
         * Called B2=I-Asin(i * 2 * pi / period + pi / 4)
         * if -1<B1<0, B2 will be round down to get correct B1
         * if 0<B1<1, B2 will be round up to get correct B1
         * However, there is no way we can differentiate this two case based on just the array of ints to implement if.
         * So sometimes, one character will be wrong when decrypting.
         * If this is wrong, I would be appreciated for your feedback. Thank you!
         */

        @Test
        public void testEncryptionLengthSmallerOrEqualText() {
            int[] Expected={129,26,114,-13,132,33};
            Assertions.assertArrayEquals(Expected, Cryptography.encrypt(testDocument1,6,2,64));
        }

        @Test
        public void testEncryptionLengthLargerText() {
            int[] Expected={129,26,114,-13,132,33,122,23,123,-13,77,-13};
            Assertions.assertArrayEquals(Expected, Cryptography.encrypt(testDocument1,12,2,64));
        }

        @Test
        public void testDecryptionText1To100CharA64() {
            Assertions.assertEquals("THE ", Cryptography.decrypt(Cryptography.encrypt(testDocument2, 4, 2, 64)));
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A pictur", Cryptography.decrypt(Cryptography.encrypt(testDocument2, 100, 2, 64)));
        }

        @Test
        public void testDecryptionText1To100CharA128() {
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A pictur", Cryptography.decrypt(Cryptography.encrypt(testDocument2, 100, 20, 128)));
        }

        @Test
        public void testDecryptionText1To100CharA256() {
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A pictur", Cryptography.decrypt(Cryptography.encrypt(testDocument2, 100, 50, 256)));
        }

        @Test
        public void testDecryptionText1To100CharA512() {
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A pictur", Cryptography.decrypt(Cryptography.encrypt(testDocument2,100,50,512)));

        }

        @Test
        public void testDecryptionText100To400CharA64() {
            int[] Input={129,26,114,-13,132,33};
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A picture fell off the wall and shattered. Water logged, droplets dripped from her nose, her hair, and her eyelashes, pooling at her feet. She scowled. Her new high heels were utterly ruined. Courtney hid a chuckle behind her hand. Pointing the remote contro", Cryptography.decrypt(Cryptography.encrypt(testDocument2,350,2,64)));
        }

        @Test
        public void testDecryptionText100To400CharA128() {
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A picture fell off the wall and shattered. Water logged, droplets dripped from her nose, her hair, and her eyelashes, pooling at her feet. She scowled. Her new high heels were utterly ruined. Courtney hid a chuckle behind her hand. Pointing the remote contro", Cryptography.decrypt(Cryptography.encrypt(testDocument2,350,2,128)));
        }

        @Test
        public void testDecryptionText100To400CharA256() {
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A picture fell off the wall and shattered. Water logged, droplets dripped from her nose, her hair, and her eyelashes, pooling at her feet. She scowled. Her new high heels were utterly ruined. Courtney hid a chuckle behind her hand. Pointing the remote contro", Cryptography.decrypt(Cryptography.encrypt(testDocument2,350,2,256)));
        }

        @Test
        public void testDecryptionText100To400CharA512() {
            Assertions.assertEquals("THE WOMEN  \"Ugh!\" Stephanie Dwyer slammed her apartment door and stomped her foot. \"Creep!\" A picture fell off the wall and shattered. Water logged, droplets dripped from her nose, her hair, and her eyelashes, pooling at her feet. She scowled. Her new high heels were utterly ruined. Courtney hid a chuckle behind her hand. Pointing the remote contro", Cryptography.decrypt(Cryptography.encrypt(testDocument2,350,2,512)));
        }

        /**
         * Since the formula to calculate amplitude will become more exact with longer length, in over 1000 situation,
         * the amplitude will become more precise than in 100 to 400 char. Therefore, if 100 to 400 situation is correct,
         * over 400 should also be correct
         */

    }

