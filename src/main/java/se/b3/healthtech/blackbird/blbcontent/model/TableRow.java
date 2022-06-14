package se.b3.healthtech.blackbird.blbcontent.model;

import io.swagger.v3.oas.annotations.media.Schema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema
@DynamoDbBean
public class TableRow {
    @NotNull
    List<String> celldata;

    public List<String> getCelldata() {
        return celldata;
    }

    public void setCelldata(List<String> celldata) {
        this.celldata = celldata;
    }
}
