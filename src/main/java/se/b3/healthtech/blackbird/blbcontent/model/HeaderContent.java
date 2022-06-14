package se.b3.healthtech.blackbird.blbcontent.model;

import io.swagger.v3.oas.annotations.media.Schema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.validation.constraints.NotNull;

@Schema
@DynamoDbBean
public class HeaderContent {
    @NotNull
    String text;
    @NotNull
    HeaderType headerType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public HeaderType getHeaderType() {
        return headerType;
    }

    public void setHeaderType(HeaderType headerType) {
        this.headerType = headerType;
    }
}
