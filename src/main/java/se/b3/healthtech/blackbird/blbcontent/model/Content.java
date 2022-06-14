package se.b3.healthtech.blackbird.blbcontent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import se.b3.healthtech.blackbird.blbcontent.enums.ContentType;
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
    private ContentType contentType;
    private HeaderContent headerContent;
    private TextContent textContent;
    private ListContent listContent;
    private TableContent tableContent;

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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public HeaderContent getHeaderContent() {
        return headerContent;
    }

    public void setHeaderContent(HeaderContent headerContent) {
        this.headerContent = headerContent;
    }

    public TextContent getTextContent() {
        return textContent;
    }

    public void setTextContent(TextContent textContent) {
        this.textContent = textContent;
    }

    public TableContent getTableContent() {
        return tableContent;
    }

    public ListContent getListContent() {
        return listContent;
    }

    public void setListContent(ListContent listContent) {
        this.listContent = listContent;
    }

    public void setTableContent(TableContent tableContent) {
        this.tableContent = tableContent;
    }
}

