package ru.qmbo.krisha.model.document;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Accessors(chain = true)
@Data
@Document
public class Room {
    @Id
    private String link;
}
