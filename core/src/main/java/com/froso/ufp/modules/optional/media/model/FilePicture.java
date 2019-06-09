package com.froso.ufp.modules.optional.media.model;

import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = FilePicture.TYPE_NAME)
@UFPResourceMetadataAnnotation(
        keywords = @ResourceKeywords({
                @ResourceKeyword("MENU_MONITORING"),
                @ResourceKeyword("MONITORING"),
                @ResourceKeyword("FTP"),
                @ResourceKeyword("MENU_FTP")
        })

)
public class FilePicture extends UfpFile {
    public static final String TYPE_NAME = "FilePicture";

    public FilePicture() {
        super(TYPE_NAME);
    }
}
