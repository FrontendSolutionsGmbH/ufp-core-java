package com.froso.ufp.modules.optional.comment.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.comment.model.*;
import com.froso.ufp.modules.optional.comment.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this
 * template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + AnyComment.TYPE_NAME)
class AdminCommentModuleController extends BaseRepositoryController<CommentModel> {


}
