package se.b3.healthtech.blackbird.blbcontent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.b3.healthtech.blackbird.blbcontent.api.request.CreateContentRequest;
import se.b3.healthtech.blackbird.blbcontent.enums.ContentType;
import se.b3.healthtech.blackbird.blbcontent.model.Content;
import se.b3.healthtech.blackbird.blbcontent.persistence.service.ContentDbHandler;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContentService {

    private static final String DELIMITER = "#";

    private static final String LATEST_KEY = "LATEST";

    private final ContentDbHandler contentDbHandler;

    public ContentService(ContentDbHandler contentDbHandler) {
        this.contentDbHandler = contentDbHandler;
    }

    public String createContent(String key, List<Content> contentList) throws CloneNotSupportedException {
        log.info("In the content service class: init");
        List<Content> resultContentList = createContentList(contentList, key);

        contentDbHandler.insertContent(resultContentList);

        return null;
    }

    List<Content> createContentList(List<Content> contentList, String partitionKey) throws CloneNotSupportedException {
        List<Content> contentsList = new ArrayList<>();
        log.info("1. " + contentList.size() + " " + contentList); // initial list

        for (Content content : contentList) {
            content.setId(partitionKey);
            content.setVersionNumber(1);
            content.setCommitNumber(1);
            getVersionKey(content);
        }

        contentsList.addAll(contentList);
        log.info("2. " + contentsList.size() + " " + contentsList); // added the original files

        List<Content> clonedContentsList = cloneContentsList(contentList);
        getLatestVersionKey(clonedContentsList);

        contentsList.addAll(clonedContentsList);
        log.info("3. " + contentsList.size() + " " + contentsList);

        return contentsList;

    }

    private void getVersionKey(Content content) {
        content.setVersionKey(
                ContentType.CONTENT + DELIMITER + content.getUuid() + DELIMITER + "V" +
                        content.getVersionNumber() + DELIMITER + "C" + content.getCommitNumber());
    }

    private void getLatestVersionKey(List<Content> contentList) {
        for (Content content : contentList) {
            content.setVersionKey(ContentType.CONTENT + DELIMITER + LATEST_KEY + DELIMITER + content.getUuid());
        }
    }
    public List<Content> cloneContentsList(@NotNull List<Content> contentList) throws CloneNotSupportedException {
        List<Content> clonedContentsList = new ArrayList<>();
        for (Content content : contentList) {
            Content clonedContent = (Content) content.clone();
            clonedContentsList.add(clonedContent);
        }
        return clonedContentsList;
    }


    public List<Content> getLatestContent(String key) {
        log.info("ContentService - getLatestContent");
        String versionKey = ContentType.CONTENT.name()+ DELIMITER + LATEST_KEY;
        return contentDbHandler.getContents(key, versionKey);
    }
}
