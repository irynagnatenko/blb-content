package se.b3.healthtech.blackbird.blbcontent.persistence.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcontent.model.Content;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.model.BatchWriteItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.WriteBatch;

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

    public void insertContent(List<Content> publicationList){
        log.info("writeContent");
        WriteBatch.Builder subBatchBuilder = WriteBatch.builder(Content.class).mappedTableResource(contentTable);
        publicationList.forEach(subBatchBuilder::addPutItem);

        BatchWriteItemEnhancedRequest.Builder batchWriteItemEnhancedRequest = BatchWriteItemEnhancedRequest.builder();
        batchWriteItemEnhancedRequest.addWriteBatch(subBatchBuilder.build());
        dynamoDbEnhancedClient.batchWriteItem(batchWriteItemEnhancedRequest.build());
        System.out.println("done");
    }

}

