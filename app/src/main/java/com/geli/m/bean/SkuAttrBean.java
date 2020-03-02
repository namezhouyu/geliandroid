package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;

/**
 *
 */
public class SkuAttrBean implements Parcelable {

    private int specId;
    private String specName;
    private int attributeId;
    private String attributeName;

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public SkuAttrBean(int specId, String specName, int attributeId, String attributeName) {
        this.specId = specId;
        this.specName = specName;
        this.attributeId = attributeId;
        this.attributeName = attributeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.specId);
        dest.writeString(this.specName);
        dest.writeInt(this.attributeId);
        dest.writeString(this.attributeName);
    }

    protected SkuAttrBean(Parcel in) {
        this.specId = in.readInt();
        this.specName = in.readString();
        this.attributeId = in.readInt();
        this.attributeName = in.readString();
    }

    public static final Parcelable.Creator<SkuAttrBean> CREATOR = new Parcelable.Creator<SkuAttrBean>() {
        @Override
        public SkuAttrBean createFromParcel(Parcel source) {
            return new SkuAttrBean(source);
        }

        @Override
        public SkuAttrBean[] newArray(int size) {
            return new SkuAttrBean[size];
        }
    };

    @Override
    public String toString() {
        return "SkuAttrBean{" +
                "specId=" + specId +
                ", specName='" + specName + '\'' +
                ", attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                '}';
    }

    /**
     * 排序
     */
    public static class SkuAttributeComparator implements Comparator {

        public int compare(Object o1, Object o2){
            SkuAttrBean s1 = (SkuAttrBean)o1;
            SkuAttrBean s2 = (SkuAttrBean)o2;

            int result = s1.getSpecId() > s2.getSpecId() ? 1 :
                    (s1.getSpecId() == s2.getSpecId() ? 0 : -1);
            return result;
        }
    }
}
