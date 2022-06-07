package se.b3.healthtech.blackbird.blbcontent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcontent.api.request.CreateContentRequest;
import se.b3.healthtech.blackbird.blbcontent.model.Content;
import se.b3.healthtech.blackbird.blbcontent.persistence.service.ContentDbHandler;

import java.util.List;

@Slf4j
@Service
public class ContentService {

    private static final String DELIMITER = "#";

    private final ContentDbHandler contentDbHandler;

    public ContentService(ContentDbHandler contentDbHandler) {
        this.contentDbHandler = contentDbHandler;
    }

    public String createContent(CreateContentRequest request) {
        log.info("In the content service class: init");
        List<Content> contentList = createContentList(request.contentList(), request.partitionKey());

        contentDbHandler.insertContent(contentList);

        return null;
    }

    List<Content> createContentList(List<Content> contentList, String partitionKey) {

        for (Content content : contentList) {
            content.setId(partitionKey);
            content.setVersionKey("CONTENT" + DELIMITER + content.getUuid() + DELIMITER + "V" + content.getVersionNumber() +
                    DELIMITER + "C" + content.getCommitNumber());
        }

        return contentList;

    }
}
