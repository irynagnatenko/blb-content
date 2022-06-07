package se.b3.healthtech.blackbird.blbcontent.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.b3.healthtech.blackbird.blbcontent.api.request.CreateContentRequest;
import se.b3.healthtech.blackbird.blbcontent.service.ContentService;

@Slf4j
@Data
@RestController
@RequestMapping(value = "/api-birdspecies/content")
public class ContentController {

    private ContentService contentService;

    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @Operation(summary = "Create a new text block")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a text block", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void createContent(@RequestBody CreateContentRequest request) {
        contentService.createContent(request);
    }

}
