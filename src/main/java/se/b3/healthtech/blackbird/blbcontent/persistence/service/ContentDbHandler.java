package se.b3.healthtech.blackbird.blbcontent.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcontent.model.Content;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
@Service
public class ContentDbHandler {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbTable<Content> contentTable;

    public ContentDbHandler(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbTable<Content> contentTable) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.contentTable = contentTable;
    }

    public void insertContent(List<Content> contentList) {
        log.info("DBhandler writeContent");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(Content.class).mappedTableResource(contentTable);
        contentList.forEach(subBatchBuilder::addPutItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println(" writeContent done");
    }

    public List<Content> getContents(String partitionKey, String versionKey) {
        log.info("ContentDBhandler - getContents");

        List<Content> contentsList = new ArrayList<>();

        QueryConditional queryConditional = QueryConditional.sortBeginsWith(Key.builder()
                .partitionValue(partitionKey)
                .sortValue(versionKey)
                .build());

        Iterator<Content> results = contentTable.query(queryConditional).items().iterator();

        while (results.hasNext()) {
            Content content = results.next();
            contentsList.add(content);
            log.info("ContentId: {}", content.getUuid());
        }
        return contentsList;
    }

    public void deleteContent(List<Content> contentList) {
        log.info("DBhandler - deleteContent");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(Content.class).mappedTableResource(contentTable);
        contentList.forEach(subBatchBuilder::addDeleteItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println("deleteContent - done");
    }

}

