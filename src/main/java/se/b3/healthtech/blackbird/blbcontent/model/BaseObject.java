package se.b3.healthtech.blackbird.blbcontent.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.validation.constraints.NotNull;

@DynamoDbBean
public class BaseObject implements Cloneable {
    @NotNull
    private int versionNumber;
    @NotNull
    private int commitNumber;
    @NotNull
    private long created;
    @NotNull
    private String createdBy;

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getCommitNumber() {
        return commitNumber;
    }

    public void setCommitNumber(int commitNumber) {
        this.commitNumber = commitNumber;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override  // Deep clone
    public Object clone()throws CloneNotSupportedException{
        BaseObject clonedObject = (BaseObject) super.clone();
        return clonedObject;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                ", versionNumber=" + versionNumber +
                ", commitNumber=" + commitNumber +
                ", created=" + created +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
