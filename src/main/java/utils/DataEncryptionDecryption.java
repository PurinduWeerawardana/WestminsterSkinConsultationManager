package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import javax.crypto.KeyGenerator;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

public class DataEncryptionDecryption {

    // Reference: https://github.com/Spicin/imageencryptiondecryptioinAndroidHire/blob/main/Myclass.java
    // added functionality to encrypt and decrypt files using AES algorithm with a secret string key
    public static String encryptDataFromFile(String sourcePath, String outputPath) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            Key key = keyGenerator.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            CipherInputStream imageToEncrypt = new CipherInputStream(new FileInputStream(sourcePath), cipher);
            FileOutputStream encryptedImage = new FileOutputStream(outputPath);
            int i;
            while((i= imageToEncrypt.read())!=-1) {
                encryptedImage.write(i);
            }
            imageToEncrypt.close();
            encryptedImage.flush();
            encryptedImage.close();
            return encodedKey;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void decryptData(String sourcePath, String outputPath, String encodedKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
            Key originalKey = new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            CipherInputStream dataToDecrypt = new CipherInputStream(new FileInputStream(sourcePath), cipher);
            FileOutputStream decryptedData = new FileOutputStream(outputPath);
            int j;
            while ((j = dataToDecrypt.read()) != -1) {
                decryptedData.write(j);
            }
            dataToDecrypt.close();
            decryptedData.flush();
            decryptedData.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static String decryptData(String sourcePath, String encodedKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
            Key originalKey = new javax.crypto.spec.SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
            cipher.init(Cipher.DECRYPT_MODE, originalKey);
            CipherInputStream dataToDecrypt = new CipherInputStream(new FileInputStream(sourcePath), cipher);
            StringBuilder decryptedData = new StringBuilder();
            int j;
            while ((j = dataToDecrypt.read()) != -1) {
                decryptedData.append((char) j);
            }
            dataToDecrypt.close();
            return decryptedData.toString();
        }catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String encryptData(String data, String outputPath) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            Key key = keyGenerator.generateKey();
            String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] dataBytes = data.getBytes();
            cipher.update(dataBytes);
            byte[] encryptedData = cipher.doFinal();
            FileOutputStream encryptedFile = new FileOutputStream(outputPath);
            for (byte b: encryptedData) {
                encryptedFile.write(b);
            }
            encryptedFile.flush();
            encryptedFile.close();
            return encodedKey;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    }

