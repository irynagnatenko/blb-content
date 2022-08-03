package se.b3.healthtech.blackbird.blbcontent.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
    private static final String DELETED_KEY = "DELETED";

    private final ContentDbHandler contentDbHandler;

    public ContentService(ContentDbHandler contentDbHandler) {
        this.contentDbHandler = contentDbHandler;
    }

    public String addContentList(String key, List<Content> contentList) throws CloneNotSupportedException {
        log.info("In the content service class: init");
        List<Content> resultContentList = createContentList(contentList, key);
        contentDbHandler.insertContent(resultContentList);

        return null;
    }

    List<Content> createContentList(List<Content> contentList, String key) throws CloneNotSupportedException {
        List<Content> resultList = new ArrayList<>();
        log.info("1. " + contentList.size() + " " + contentList); // initial list

        for (Content content : contentList) {
            setPartitionKey(key, content);
            setVersionKey(content);
            log.info("" + content.getVersionKey()); // added the original files

        }

        resultList.addAll(contentList);

        log.info("2. " + resultList.size() + " " + resultList); // added the original files

        List<Content> clonedContentsList = cloneContentsList(contentList);
        setLatestVersionKeyContentList(clonedContentsList);

        resultList.addAll(clonedContentsList);
        log.info("3. " + resultList.size() + " " + resultList);

        return resultList;

    }

    private void setPartitionKey(String partitionKey, Content content) {
        content.setCommitNumber(1);
        content.setVersionNumber(1);
        content.setId(partitionKey);
    }

    private void setVersionKey(Content content) {
        content.setVersionKey(
                ContentType.CONTENT + DELIMITER + content.getUuid() + DELIMITER + "V" +
                        content.getVersionNumber() + DELIMITER + "C" + content.getCommitNumber());
    }


    private void setLatestVersionKeyContentList(List<Content> contentList) {
        for (Content content : contentList) {
            content.setVersionKey(ContentType.CONTENT + DELIMITER + LATEST_KEY + DELIMITER + content.getUuid());
            log.info("" + content.getVersionKey()); // added the original files

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

    public Content cloneContent(Content content) throws CloneNotSupportedException {
        Content clonedContent = (Content) content.clone();
        return clonedContent;
    }

    public List<Content> getLatestContent(String key) {
        log.info("ContentService - getLatestContent List");
        String versionKey = ContentType.CONTENT.name() + DELIMITER + LATEST_KEY;
        return contentDbHandler.getContents(key, versionKey);
    }

    public Content getContent(String publicationId, String contentId) {
        log.info("ContentService - getLatestContent one object");
        //Skapa upp ett LATEST-versionKey med angivet contentId
        String versionKey = ContentType.CONTENT.name() + DELIMITER + LATEST_KEY + DELIMITER + contentId;
        //Anropa befintlig metod - GetContents i ContentDBHandler med parametrarna (publicationId, versionKey)
        List<Content> latestContent = contentDbHandler.getContents(publicationId, versionKey);
        log.info("ContentService - after getLatestContent one object");
        //Metoden ska returnera det första objektet i listan av Contents.
        return latestContent.get(0);
    }

    public void addContent(String key, Content content) throws CloneNotSupportedException {
        log.info("ContentService - addContent one object");

        List<Content> newContentObjects = new ArrayList<>();

        setPartitionKey(key, content);
        setVersionKey(content);

        newContentObjects.add(content);

        Content latestContent = cloneContent(content);
        setLatestVersionKey(latestContent);

        newContentObjects.add(latestContent);

        contentDbHandler.insertContent(newContentObjects);
    }

    public void deleteContent(String userName, String publicationId, List<Content> contentList) {
        log.info("ContentService - deleteContent");

        contentToDelete(publicationId, contentList);
        contentDbHandler.deleteContent(contentList);
        log.info("ContentService - after delete " + contentList);
        log.info(contentList.toString());

        markAsDeleted(contentList, userName);
        contentDbHandler.insertContent(contentList);
        log.info("ContentService - after insert " + contentList);
        log.info(contentList.toString());

    }

    private void contentToDelete(String publicationId, List<Content> contentList) {
        log.info("in the contentToDelete");
        for (Content content : contentList) {
            setPartitionKey(publicationId, content);
            setLatestVersionKey(content);
            log.info(content.getVersionKey());
        }
    }

    private void markAsDeleted(List<Content> contentList, String userName) {
        log.info("in the markAsDeleted");
        for (Content content : contentList) {
            setDeletedKey(content);
            content.setCreatedBy(userName);
            content.setCreated(ContentUtil.setCreatedTime());
            log.info(content.getVersionKey());
        }
    }

    private void setLatestVersionKey(Content content) {
        content.setVersionKey(ContentType.CONTENT + DELIMITER + LATEST_KEY + DELIMITER + content.getUuid());
    }

    public void setDeletedKey(Content content) {
        content.setVersionKey(ContentType.CONTENT + DELIMITER + DELETED_KEY + DELIMITER + content.getUuid());
    }

    public List<Content> getContentsByUuids(String publicationId, List<String> contentIdsList) {
        log.info("ContentService:  getContentsByUuids");
        log.info("Lista " + contentIdsList.size());

        List<Content> contentList = new ArrayList<>();

        for (String uuid : contentIdsList) {
            Content content = getContentById(publicationId, uuid);
            contentList.add(content);
        }
        return contentList;
    }

    public Content getContentById(String key, String uuid) {
        log.info("ContentService - getContent List");
        log.info("uuid " + uuid);

        String versionKey = ContentType.CONTENT.name() + DELIMITER + LATEST_KEY + DELIMITER + uuid;
        log.info("version key " + versionKey);
        List<Content> contents = contentDbHandler.getContents(key, versionKey);
        log.info("Lista: " + contents.size());
        log.info("container object" + contents.get(0));

        return contents.get(0);
    }
}
