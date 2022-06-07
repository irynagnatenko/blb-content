package se.b3.healthtech.blackbird.blbcontent.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import se.b3.healthtech.blackbird.blbcontent.model.Content;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Slf4j
@Configuration
public class DynamoDbConfig {

    private static final String FULL_TABLE_NAME = "blb_content";

    @Value("${AWS_DYNAMODB_ENDPOINT:http://dynamodb-local:8002}")
    private String amazonDynamoDBEndpoint;
    @Value("${AWS_ACCESS_KEY_ID:foo}")
    private String amazonAWSAccessKey;
    @Value("${AWS_SECRET_ACCESS_KEY:bar}")
    private String amazonAWSSecretKey;
    @Value("${AWS_REGION:eu-north-1}")
    private String awsRegion;

    @Bean(name = "dynamoDbClient")
    public DynamoDbClient dynamoDbClient() {
        log.info("Creating DynamoDbClient");
        Region region = Region.of(awsRegion);
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(amazonDynamoDBEndpoint))
                // The region is meaningless for local DynamoDb but required for client builder validation
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey)))
                .build();
    }

    @Bean(name = "dynamoDbEnhancedClient")
    @DependsOn({"dynamoDbClient"})
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        log.info("Creating DynamoDbEnhancedClient");
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(dynamoDbClient())
                .build();
    }

    @Bean(name = "contentTable")
    @DependsOn({"dynamoDbEnhancedClient"})
    public DynamoDbTable<Content> dynamoContentTable() {
        log.info("Initializing dynamoContentTable");
        return dynamoDbEnhancedClient().table(FULL_TABLE_NAME, TableSchema.fromBean(Content.class));
    }

}
