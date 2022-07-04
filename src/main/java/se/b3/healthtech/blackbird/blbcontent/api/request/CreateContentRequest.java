package se.b3.healthtech.blackbird.blbcontent.api.request;

import se.b3.healthtech.blackbird.blbcontent.model.Content;

import java.util.List;

//TODO: can i delete it?
public record CreateContentRequest (String partitionKey, List<Content> contentList){
}
