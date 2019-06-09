package com.froso.ufp.modules.optional.media.configuration;

import com.froso.ufp.modules.optional.media.service.*;
import com.froso.ufp.modules.optional.media.service.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Created by ckleinhuix on 12.11.2015.
 */
@Configuration
@ComponentScan(basePackages = {
        "com.froso.ufp.modules.optional.media.controller",
        "com.froso.ufp.modules.optional.media.eventhandler",
        "com.froso.ufp.modules.optional.media.service"
})
public class MediaConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaConfiguration.class);

    /**
     * Instantiates a new Media configuration.
     */
    MediaConfiguration() {

        LOGGER.info("MEDIA CONFIGURATION");

    }

    /**
     * Init file types.
     *
     * @param mediaTypeService the media type service
     */
    @Autowired
    public void initFileTypes(MediaTypeService mediaTypeService) {
        mediaTypeService.registerMediaTypeDeterminer(new FileTypeExcel());
        mediaTypeService.registerMediaTypeDeterminer(new FileTypePDF());
        mediaTypeService.registerMediaTypeDeterminer(new FileTypePicture());


    }

}
