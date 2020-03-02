package com.geli.m.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Comparator;

/**
 *
 */
public class SortAttrBean implements Parcelable {

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

    public SortAttrBean(int specId, String specName, int attributeId, String attributeName) {
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

    protected SortAttrBean(Parcel in) {
        this.specId = in.readInt();
        this.specName = in.readString();
        this.attributeId = in.readInt();
        this.attributeName = in.readString();
    }

    public static final Creator<SortAttrBean> CREATOR = new Creator<SortAttrBean>() {
        @Override
        public SortAttrBean createFromParcel(Parcel source) {
            return new SortAttrBean(source);
        }

        @Override
        public SortAttrBean[] newArray(int size) {
            return new SortAttrBean[size];
        }
    };

    @Override
    public String toString() {
        return "SortAttrBean{" +
                "specId=" + specId +
                ", specName='" + specName + '\'' +
                ", attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                '}';
    }

    /**
     * 排序
     */
    public static class SortAttributeComparator implements Comparator {

        public int compare(Object o1, Object o2){
            SortAttrBean s1 = (SortAttrBean)o1;
            SortAttrBean s2 = (SortAttrBean)o2;

            int result = s1.getSpecId() > s2.getSpecId() ? 1 :
                    (s1.getSpecId() == s2.getSpecId() ? 0 : -1);
            return result;
        }
    }
}
