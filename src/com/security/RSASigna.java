package com.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.encoders.Hex;

public class RSASigna {
	
	public static String src="RSA ����ǩ���㷨";
	public static void main(String[] args) {
		jdkRSA();
	}
	
	public static void jdkRSA(){
		try {
			//��ʼ����Կ
			KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(1024); //512-60��������
			KeyPair keyPair=keyPairGenerator.generateKeyPair();
			RSAPublicKey rsaPublicKey=(RSAPublicKey) keyPair.getPublic();
			RSAPrivateKey rsaPrivateKey=(RSAPrivateKey) keyPair.getPrivate();
			
			//ִ��ǩ��
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
			PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Signature signature=Signature.getInstance("MD5withRSA");
			signature.initSign(privateKey);
			signature.update(src.getBytes());
			byte[] result=signature.sign();
			
			System.out.println("jdk rsa sign :"+ org.apache.commons.codec.binary.Hex.encodeHexString(result));
			//System.out.println("jdk rsa sign :"+Base64.encodeBase64String(result));
			
			//3����֤ǩ��
			X509EncodedKeySpec X509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			keyFactory=KeyFactory.getInstance("RSA");
			PublicKey publicKey=keyFactory.generatePublic(X509EncodedKeySpec);
			signature=Signature.getInstance("MD5withRSA");
			signature.initVerify(publicKey);
			signature.update(src.getBytes());
			boolean bool =signature.verify(result);
			System.out.println("jdk rsa verify: " +bool);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
