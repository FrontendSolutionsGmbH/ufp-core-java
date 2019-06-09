package com.froso.ufp.modules.optional.media.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.optional.media.model.*;
import com.froso.ufp.modules.optional.media.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + FilePicture.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = FilePicture.TYPE_NAME)
public class AdminPictureController extends AdminFileControllerBase<FilePicture> {

    @Autowired
    public AdminPictureController(PictureService pictureService) {

        super(pictureService);
    }


}
