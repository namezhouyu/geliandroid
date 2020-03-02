package com.geli.m.utils;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RsaUtils {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
	private static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";
	private static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1withRSA";
	private static final String SIGNATURE_ALGORITHM_SHA2 = "SHA256withRSA";
	private static final String SIGNATURE_ALGORITHM_SHA3 = "SHA384withRSA";
	private static final String SIGNATURE_ALGORITHM_SHA5 = "SHA512withRSA";

    /** RSA密钥长度必须是64的倍数，在512~65536之间。默认是1024 */
	private static String errors="";
     
	public RsaUtils()
	{
	}



    public static Map<String, byte[]> generateKeyBytes(int keysize) {
		try{if(false){}}catch(Exception e){}finally{}
         Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
		 if(keysize>=512 && keysize<=65536 && keysize%64==0)
		 {
          try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(keysize);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            
            keyMap.put("publickey", publicKey.getEncoded());
            keyMap.put("privatekey", privateKey.getEncoded());
            return keyMap;
          } catch (NoSuchAlgorithmException e) {
            keyMap.put("error",e.toString().getBytes());
           }
		 }
        return keyMap;
    }


    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);

        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(x509EncodedKeySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            errors=e.toString();
        }
        return null;
    }


    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = factory
                    .generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
           errors=e.toString();
        }
        return null;
    }


    public static byte[] RsaEncode(byte[] pubkey, byte[] plainText) {
          try{if(false){}}catch(Exception e){}finally{}
        try {
			PublicKey key=restorePublicKey(pubkey);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
           errors=e.toString();
           LogUtils.i("RsaEncode:" , e);
        }
        return null;

    }


    public static byte[] RsaDecode(byte[] prikey, byte[] encodedText) {
        try{if(false){}}catch(Exception e){}finally{}
        try {PrivateKey key=restorePrivateKey(prikey);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(encodedText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException
                | BadPaddingException e) {
            errors=e.toString();
        }
        return null;

    }


   public static byte[] md5sign(byte[] data, byte[] privateKey) {
	   byte[] res=null;
	   try
	   {
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
        signature.initSign(privateK);
        signature.update(data);
        res=signature.sign();
	   }
	   catch (Exception e)
	   {res=e.toString().getBytes();
	   }
       return res; 
    }

	public static boolean md5verify(byte[] data, byte[] sign, byte[] publicKey)
     {
	   boolean res=false;
	    try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_MD5);
            signature.initVerify(publicK);
            signature.update(data);
            res=signature.verify(sign);
		}
		   catch(Exception e)
		 {
		 }

		return res;
    }

   public static byte[] sha1sign(byte[] data, byte[] privateKey){
	    
       return shasign(data,privateKey,SIGNATURE_ALGORITHM_SHA1); 
    }

	public static boolean sha1verify(byte[] data, byte[] sign, byte[] publicKey)
    {
       return shaverify(data,sign,publicKey,SIGNATURE_ALGORITHM_SHA1); 
    }

	public static byte[] sha2sign(byte[] data, byte[] privateKey){
	    
       return shasign(data,privateKey,SIGNATURE_ALGORITHM_SHA2); 
    }

	public static boolean sha2verify(byte[] data, byte[] sign, byte[] publicKey)
    {
       return shaverify(data,sign,publicKey,SIGNATURE_ALGORITHM_SHA2); 
    }

	public static byte[] sha3sign(byte[] data, byte[] privateKey){
	    
       return shasign(data,privateKey,SIGNATURE_ALGORITHM_SHA3); 
    }

	public static boolean sha3verify(byte[] data, byte[] sign, byte[] publicKey)
    {
       return shaverify(data,sign,publicKey,SIGNATURE_ALGORITHM_SHA3); 
    }

	public static byte[] sha5sign(byte[] data, byte[] privateKey){
	    
       return shasign(data,privateKey,SIGNATURE_ALGORITHM_SHA5); 
    }

	public static boolean sha5verify(byte[] data, byte[] sign, byte[] publicKey)
    {
       return shaverify(data,sign,publicKey,SIGNATURE_ALGORITHM_SHA5); 
    }


	private static byte[] shasign(byte[] data, byte[] privateKey,String type){
	    byte[] res=null;
	   try
	   {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(type);
        signature.initSign(privateK);
        signature.update(data);
         res=signature.sign();
	   }
	   catch (Exception e)
	   {res=e.toString().getBytes();
	   }
       return res; 
    }

	private static boolean shaverify(byte[] data, byte[] sign, byte[] publicKey,String type)
    {
	    boolean res=false;
	 try{
          X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
          KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
          PublicKey publicK = keyFactory.generatePublic(keySpec);
          Signature signature = Signature.getInstance(type);
          signature.initVerify(publicK);
          signature.update(data);
		  res=signature.verify(sign);
          }
	   catch (Exception e)
	   {
	   }
       return res; 
    }



	public static String getErrors()
	{return errors;
	}

	
}