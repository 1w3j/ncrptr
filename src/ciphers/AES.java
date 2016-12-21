package ciphers;

import javax.crypto.*;
import java.io.*;
import javax.crypto.spec.*;
import javax.swing.JProgressBar;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author iqbal
 */
public class AES {

    private final static int AES_KEYLENGTH = 128;
    private final static int AES_BLOCKSIZE = AES_KEYLENGTH/8;
    private static final IvParameterSpec IV = new IvParameterSpec(DatatypeConverter.parseBase64Binary("aWFtbG9yZHZvbGRlbW9ydA=="));
    
    public static synchronized void encryptFile(String path, String key, JProgressBar bar) throws BadPaddingException, Exception{
        File file = new File(path);
        String originalPath = file.getCanonicalPath();
        System.out.println("Original file to encrypt: "+originalPath+"\tSize: "+file.length());
        if (file.isFile() && file.exists()) {
            File tmpFile = new File(file.getParentFile(), file.getName()+"~tmp");
            byte[] tmpBuffer = new byte[1024];
            int len; long progress=0l;
            FileInputStream fis = null;
            OutputStream out = null;
            Cipher AEScipher;
            try{
                fis = new FileInputStream(file);
                out = new FileOutputStream(tmpFile);
                AEScipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                AEScipher.init(Cipher.ENCRYPT_MODE, makeSecretKey(key), IV);
                out = new CipherOutputStream(out, AEScipher);//fixed 18-12-16 09:30:00
                bar.setString("Encrypting "+file.getName()+"...");
                while ((len = fis.read(tmpBuffer)) >= 0) {
                    out.write(tmpBuffer, 0, len);
                    progress+=(long)len;
                    bar.setValue((int)(progress * 100.0 / file.length() + 0.5));//finding the percentage %
                }
            }finally{
                if(fis!=null)fis.close();
                if(out!=null){
                    out.flush();
                    out.close();
                }
            }
            if(tmpFile.exists()){
                if(file.delete()){//"renaming" the temp file
                    FileInputStream fis2 = null;
                    FileOutputStream fos2 = null;
                    progress=0l;
                    bar.setValue(0);
                    bar.setString("Replacing "+tmpFile.getName()+"...");
                    try{
                        fis2 = new FileInputStream(tmpFile);
                        fos2 = new FileOutputStream(originalPath);
                        while((len = fis2.read(tmpBuffer)) != -1){
                            fos2.write(tmpBuffer,0,len);
                            progress+=(long)len;
                            if(!bar.isIndeterminate())bar.setValue((int)(progress * 100.0 / tmpFile.length() + 0.5));
                        }
                    }finally{
                        if(fis2!=null) fis2.close();
                        if(fos2!=null){
                            fos2.flush();
                            fos2.close();
                        }
                    }
                    if(!tmpFile.delete()){
                        throw new Exception("Couldn't delete "+tmpFile);
                    }
                }else{
                    throw new Exception("Couldn't delete "+file);
                }
            }
            System.out.println("Succesfully encrypted!");
            System.out.println("\t\t\t\t\t Encrypted file Size: "+file.length());
        } else {
            if (file.isDirectory()) {
                throw new Exception("Cannot encrypt a directory");
            }
            if (!file.exists()) {
                throw new Exception(file.getName() + " doesn't exist");
            }
        }
    }
    
    public static synchronized void decryptFile(String path, String key, JProgressBar bar) throws BadPaddingException, Exception{
        File file = new File(path);
        String originalPath = file.getCanonicalPath();
        System.out.println("Original file to decrypt: "+originalPath+"\tSize: "+file.length());
        if (file.isFile() && file.exists()) {
            File tmpFile = new File(file.getParentFile(), file.getName()+"~tmp");
            byte[] tmpBuffer = new byte[1024];
            int len; long progress=0l;
            InputStream in = null;
            FileOutputStream fos = null;
            Cipher AEScipher;
            try{
                in = new FileInputStream(file);
                fos = new FileOutputStream(tmpFile);
                AEScipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                AEScipher.init(Cipher.DECRYPT_MODE, makeSecretKey(key), IV);
                in = new CipherInputStream(in, AEScipher);//fixed 18-12-16 09:30:00
                bar.setString("Decrypting "+file.getName()+"...");
                while ((len = in.read(tmpBuffer)) >= 0) {
                    fos.write(tmpBuffer, 0, len);
                    progress+=(long)len;
                    if(!bar.isIndeterminate())bar.setValue((int)(progress * 100.0/file.length() + 0.5));
                }
            }
            finally{
                if(in!=null)in.close();
                if(fos!=null){
                    fos.flush();
                    fos.close();
                }
            }
            if(tmpFile.exists()){
                if(file.delete()){//old file
                    FileInputStream fis2 = null;
                    FileOutputStream fos2 = null;
                    progress=0l;
                    bar.setValue(0);
                    bar.setString("Replacing "+tmpFile.getName()+"...");
                    try{
                        fis2 = new FileInputStream(tmpFile);
                        fos2 = new FileOutputStream(originalPath);
                        while((len=fis2.read(tmpBuffer))!=-1){
                            fos2.write(tmpBuffer,0,len);
                            progress+=(long)len;
                            bar.setValue((int)(progress * 100.0 / tmpFile.length() + 0.5));
                        }
                    }finally{
                        if(fis2!=null) fis2.close();
                        if(fos2!=null){
                            fos2.flush();
                            fos2.close();
                        }
                    }
                    if(!tmpFile.delete()){
                        throw new Exception("Couldn't delete "+tmpFile);
                    }
                }else{
                    throw new Exception("Couldn't delete "+file);
                }
            }
            System.out.println("Succesfully decrypted!");
            System.out.println("\t\t\t\t\t Decrypted file Size: "+file.length());
        } else {
            if (file.isDirectory()) {
                throw new Exception("Cannot decrypt a directory");
            }
            if (!file.exists()) {
                throw new Exception(file.getName() + " doesn't exist");
            }
        }
    }

    private static SecretKey makeSecretKey(String key) {
        byte[] bKey = fillUpWithBytes(key);
        return new SecretKeySpec(bKey, "AES");
    }

    private static byte[] fillUpWithBytes(String key) {
        byte[] bKey = key.getBytes();
        while (bKey.length != AES_BLOCKSIZE) {
            if (bKey.length < AES_BLOCKSIZE) {
                key += "0";
                bKey = key.getBytes();
            }
            if (bKey.length > AES_BLOCKSIZE) {
                key = key.substring(0, AES_BLOCKSIZE);
                bKey = key.getBytes();
            }
        }
        return bKey;
    }
}