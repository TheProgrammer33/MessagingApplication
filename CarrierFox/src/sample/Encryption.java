package sample;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encryption
{
    private KeyPair userKeyPair;
    private PrivateKey partnerPrivKey;
    private PublicKey partnerPubKey;

    public Encryption() throws NoSuchAlgorithmException
    {
        try {
            generateKeyPair();
        } catch (IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public String encryptMessage(String message)
    {
        byte[] encryptedMessage = encrypt(partnerPubKey, message);

        return Base64.getMimeEncoder().encodeToString(encryptedMessage);
    }

    public String decryptMessage(byte[] encryptedMessage)
    {
        //byte[] encryptedMessageBytes = Base64.getMimeDecoder().decode(encryptedMessage);

        return decrypt(this.userKeyPair.getPrivate(), encryptedMessage);
    }

    private void generateKeyPair() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException
    {
        FileOutputStream privateKeyOutputStream = new FileOutputStream("src/sample/resources/private.pem");
        FileOutputStream publicKeyOutputStream = new FileOutputStream("src/sample/resources/public.pem");

        String privateKeyHeader = "-----BEGIN RSA PRIVATE KEY-----\n";
        String privateKeyFooter = "\n-----END RSA PRIVATE KEY-----";

        String publicKeyHeader = "-----BEGIN PUBLIC KEY-----\n";
        String publicKeyFooter = "\n-----END PUBLIC KEY-----";

        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");

        keyGenerator.initialize(2048);

        KeyPair keyPair = keyGenerator.generateKeyPair();

        privateKeyOutputStream.write(privateKeyHeader.getBytes());
        privateKeyOutputStream.write(Base64.getMimeEncoder().encodeToString(keyPair.getPrivate().getEncoded()).getBytes());
        privateKeyOutputStream.write(privateKeyFooter.getBytes());

        publicKeyOutputStream.write(publicKeyHeader.getBytes());
        publicKeyOutputStream.write(Base64.getMimeEncoder().encodeToString(keyPair.getPublic().getEncoded()).getBytes());
        publicKeyOutputStream.write(publicKeyFooter.getBytes());

        userKeyPair = keyPair;

        String tempPrivKey = Files.readString(Paths.get("src/sample/resources/encryptionTesting/private_pkcs8.pem"));
        String tempPubKey = Files.readString(Paths.get("src/sample/resources/encryptionTesting/public.pem"));

        /*
        tempPubKey = tempPubKey.replace("-----BEGIN PULBIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        tempPrivKey = tempPrivKey.replace("-----BEGIN PRIVATE RSA KEY-----", "")
                .replace("-----END PRIVATE RSA KEY-----", "")
                .replaceAll("\\s", "");

        byte[] privKeyDER = Base64.getMimeDecoder().decode(tempPrivKey);
        byte[] pubKeyDER = Base64.getMimeDecoder().decode(tempPubKey);
         */

        PemObject pem = new PemReader(new StringReader(tempPrivKey)).readPemObject();
        byte[] der = pem.getContent();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(der);

        partnerPrivKey = (PrivateKey) keyFactory.generatePrivate(keySpec);

        pem = new PemReader(new StringReader(tempPubKey)).readPemObject();
        der = pem.getContent();
        X509EncodedKeySpec keySpec1 = new X509EncodedKeySpec(der);

        partnerPubKey = (PublicKey) keyFactory.generatePublic(keySpec1);

        privateKeyOutputStream.close();
    }

    private static byte[] encrypt(Key pubkey, String text) {
        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA", "BC");
            rsa.init(Cipher.ENCRYPT_MODE, pubkey);
            return rsa.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decrypt(Key decryptionKey, byte[] buffer) {
        try {
            Cipher rsa;
            rsa = Cipher.getInstance("RSA", "BC");
            rsa.init(Cipher.DECRYPT_MODE, decryptionKey);
            byte[] utf8 = rsa.doFinal(buffer);
            return new String(utf8, "UTF8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
