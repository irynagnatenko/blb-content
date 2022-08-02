package se.b3.healthtech.blackbird.blbcontent.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.b3.healthtech.blackbird.blbcontent.service.ContentService;

import java.util.List;

@Slf4j
@Data
@RestController
@RequestMapping(value = "/api-birdspecies/content")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @Operation(summary = "Create a list of contents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a list of text blocks", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @PostMapping(value = "/all")
    @ResponseStatus(value = HttpStatus.OK)
    public void createContentList(@RequestParam String key, @RequestBody List<se.b3.healthtech.blackbird.blbcontent.model.Content> contentList) throws CloneNotSupportedException {
        contentService.addContentList(key, contentList);
    }

    @Operation(summary = "Get the list of the latest content")
    @ApiResponses(value = {
            // Om man har en List, lägger man till @Schema
            @ApiResponse(responseCode = "200", description = "Found content",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcontent.model.Content.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/all/",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<se.b3.healthtech.blackbird.blbcontent.model.Content> getLatestContent(@RequestParam("key") String key) {
        log.info("ContentController - getLatestContent");
        return contentService.getLatestContent(key);
    }

    @Operation(summary = "Create a new content object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a text block", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @PostMapping(value = "/",
        params = "key")
    @ResponseStatus(value = HttpStatus.OK)
    public void addContent(@RequestParam("key") String key, @RequestBody se.b3.healthtech.blackbird.blbcontent.model.Content content) throws CloneNotSupportedException {
        contentService.addContent(key, content);
    }

    @Operation(summary = "Get the latest content for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found content object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcontent.model.Content.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/",
            params = {"key", "id"},
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public se.b3.healthtech.blackbird.blbcontent.model.Content getContent(@RequestParam("key") String publicationId, @RequestParam("id") String contentId) {
        log.info("in ContentController - getContent");
        return contentService.getContent(publicationId, contentId);
    }

    @Operation(summary = "Delete content object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a text block", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @PostMapping(value = "/",
            headers = "userName",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteContent(@RequestHeader("userName") String userName,
                              @RequestParam("key") String publicationId,
                              @RequestBody List<se.b3.healthtech.blackbird.blbcontent.model.Content> contentList) {
        log.info("in ContentController - deleteContent");
        contentService.deleteContent(userName, publicationId, contentList);
    }

    // for deleteContainer
    @Operation(summary = "Get list of content for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found content objects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcontent.model.Content.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/selected/",
            params = {"key", "uuids"},
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<se.b3.healthtech.blackbird.blbcontent.model.Content> getContentsByUuids(@RequestParam("key") String publicationId,
                                                                                        @RequestParam("uuids") List<String> contentIdsList) {
        log.info("in ContentController - getContentsByUuids");
        return contentService.getContentsByUuids(publicationId, contentIdsList);
    }
}
