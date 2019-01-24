package com.qianxuefeng.base;

/**
 * @author Shawn Qian
 * @since 2018年10月10日
 */
public class ExtendFather {
    private String privateKey = "father-privateKey";
    protected String protectedKey = "father-protectedKey";
    public String publicKey = "father-publicKey";

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getProtectedKey() {
        return protectedKey;
    }

    public void setProtectedKey(String protectedKey) {
        this.protectedKey = protectedKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}