package sample;

public class Encyption
{
    private String key = "C9 78 96 ED 07 55 D5 6B EF 38 BF 8B 1E 0D 48 A2 B8 AE 9E 43 F0 C2 7E 4F B1 E5 60 38 A2 C4 4D 65";

    public Encyption()
    { }

    public String encrypt(String text) throws Exception
    {
        return AESEncryption.encryptData(text, key);
    }

    public String decrypt(String encryptedText) throws Exception
    {
        return AESEncryption.decryptData(encryptedText, key);
    }
}
