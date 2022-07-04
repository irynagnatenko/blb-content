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
    @Operation(summary = "Create a new list of text blocks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a list of text blocks", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @PostMapping(value = "/all")
    @ResponseStatus(value = HttpStatus.OK)
    public void createContentList(@RequestParam String key, @RequestBody List<se.b3.healthtech.blackbird.blbcontent.model.Content> contentList) throws CloneNotSupportedException {
        contentService.addContentList(key, contentList);
    }

    @Operation(summary = "Get the latest content")
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

    @Operation(summary = "Create a new content block")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a text block", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void addContent(@RequestParam String key, @RequestBody se.b3.healthtech.blackbird.blbcontent.model.Content content) throws CloneNotSupportedException {
        contentService.addContent(key, content);
    }

    /*
    @Operation(summary = "Get the latest content for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found content object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcontent.model.Content.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/",
            params = {"key", "ContentId"},
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public Content getContent(@RequestParam("key") String key, @RequestParam("contentId") String contentId) {
        log.info("in ContentController - getContent");
        return contentService.getcontent(key, contentId);
    }

     */
}
