package com.hsy.cas.utils;


import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

/**
 * 国密加密工具类
 */
public class SM3Util {

	public static final String SM3Encode(String str) {
		byte[] md = new byte[32];
		byte[] msg1 = str.getBytes();
		SM3Digest sm3 = new SM3Digest();
		sm3.update(msg1, 0, msg1.length);
		sm3.doFinal(md, 0);
		return new String(Hex.encode(md));
	}

	public static void main(String[] args){
	}
}
