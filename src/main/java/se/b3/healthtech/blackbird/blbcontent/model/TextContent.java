package se.b3.healthtech.blackbird.blbcontent.model;

import io.swagger.v3.oas.annotations.media.Schema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.validation.constraints.NotNull;

@Schema
@DynamoDbBean
public class TextContent {
    @NotNull
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
