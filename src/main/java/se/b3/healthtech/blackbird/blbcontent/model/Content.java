package se.b3.healthtech.blackbird.blbcontent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import se.b3.healthtech.blackbird.blbcontent.enums.ContentType;
import se.b3.healthtech.blackbird.blbcontent.enums.PublicationType;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import javax.validation.constraints.NotNull;

@Schema
@DynamoDbBean
public class Content extends BaseObject implements Cloneable {
    @JsonIgnore
    private String id;
    @JsonIgnore
    private String versionKey;
    @NotNull
    private String uuid;
    @NotNull
    ContentType contentType;
    @NotNull
    PublicationType publicationType;
    @NotNull
    private String text;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbSortKey
    public String getVersionKey() {
        return versionKey;
    }

    public void setVersionKey(String versionKey) {
        this.versionKey = versionKey;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        BaseObject cloned = (BaseObject) super.clone();
        return cloned;
    }
}
