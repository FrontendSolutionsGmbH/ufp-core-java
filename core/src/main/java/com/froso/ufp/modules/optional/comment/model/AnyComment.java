package com.froso.ufp.modules.optional.comment.model;

import com.froso.ufp.modules.core.resourcemetadata.annotations.*;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * Created by ck on 20.09.2016.
 */
@UFPResourceMetadataAnnotation(

        keywords =
        @ResourceKeywords({
                @ResourceKeyword("COMMENT")
        })


)
@Document(collection = AnyComment.TYPE_NAME)
public class AnyComment extends CommentModel {
    public static final String TYPE_NAME = "AnyComment";
    private static final long serialVersionUID = -4840909360054155989L;

    public AnyComment() {

        super(TYPE_NAME);
    }

    public AnyComment(Comment comment) {

        super(TYPE_NAME);
        this.setComment(comment.getComment());
    }
}
