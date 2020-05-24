package at.entyties;

import at.enums.ColorsSelector;
import at.enums.NoteType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Note {

    private String title;
    private String content;
    private ColorsSelector color;
    private NoteType type;
    private String createdTime;

}

