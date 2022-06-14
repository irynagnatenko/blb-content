package se.b3.healthtech.blackbird.blbcontent.model;

import io.swagger.v3.oas.annotations.media.Schema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Schema
@DynamoDbBean
public class ListContent {
    @NotNull
    ListType listType;
    @NotNull
    List<String> rows;

    public ListType getListType() {
        return listType;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }

    public List<String> getRows() {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }
}
