package com.mail.util;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class PassUtil {
	public static String digestString(String pass, String algorithm )
            throws NoSuchAlgorithmException  {
        MessageDigest md;
        ByteArrayOutputStream bos;
        try {
            md = MessageDigest.getInstance(algorithm);
            byte[] digest = md.digest(pass.getBytes("iso-8859-1"));
            bos = new ByteArrayOutputStream();
            OutputStream encodedStream = MimeUtility.encode(bos, "base64");
            encodedStream.write(digest);
            return bos.toString("iso-8859-1");
        } catch (IOException ioe) {
            throw new RuntimeException("Fatal error: " + ioe);
        } catch (MessagingException me) {
            throw new RuntimeException("Fatal error: " + me);
        }
    }
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		String pass="sakura233";
		System.out.println(digestString(pass,"SHA"));
	}
}
