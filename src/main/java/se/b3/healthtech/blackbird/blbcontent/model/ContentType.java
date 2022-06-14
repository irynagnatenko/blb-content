package se.b3.healthtech.blackbird.blbcontent.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum ContentType {
    TEXT, LIST, TABLE, ATTACHMENT_REFERENCE, PICTURE_REFERENCE, REFERENCE, HEADER;
}
