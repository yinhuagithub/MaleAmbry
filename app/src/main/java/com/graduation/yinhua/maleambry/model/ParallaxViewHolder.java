package com.graduation.yinhua.maleambry.model;

/**
 * ParallaxViewHolder.java
 * Description: 存储自定义视差ImageView动画参数
 *
 * Created by yinhua on 2016/11/7.
 * git：https://github.com/yinhuagithub/MaleAmbry
 */
public class ParallaxViewHolder {
    private int index;
    private float alphaIn;
    private float alphaOut;
    private float xIn;
    private float xOut;
    private float yIn;
    private float yOut;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public float getAlphaIn() {
        return alphaIn;
    }

    public void setAlphaIn(float alphaIn) {
        this.alphaIn = alphaIn;
    }

    public float getAlphaOut() {
        return alphaOut;
    }

    public void setAlphaOut(float alphaOut) {
        this.alphaOut = alphaOut;
    }

    public float getxIn() {
        return xIn;
    }

    public void setxIn(float xIn) {
        this.xIn = xIn;
    }

    public float getxOut() {
        return xOut;
    }

    public void setxOut(float xOut) {
        this.xOut = xOut;
    }

    public float getyIn() {
        return yIn;
    }

    public void setyIn(float yIn) {
        this.yIn = yIn;
    }

    public float getyOut() {
        return yOut;
    }

    public void setyOut(float yOut) {
        this.yOut = yOut;
    }
}
