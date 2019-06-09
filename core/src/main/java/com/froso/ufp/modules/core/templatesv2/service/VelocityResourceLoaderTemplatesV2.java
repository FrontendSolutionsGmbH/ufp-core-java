package com.froso.ufp.modules.core.templatesv2.service;

import com.froso.ufp.modules.core.templatesv2.model.*;
import java.io.*;
import java.nio.charset.*;
import org.apache.commons.collections.*;
import org.apache.velocity.exception.*;
import org.apache.velocity.runtime.resource.*;
import org.apache.velocity.runtime.resource.loader.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 08.01.2016.
 */
public class VelocityResourceLoaderTemplatesV2 extends ResourceLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(VelocityResourceLoaderTemplatesV2.class);
    private TemplateService templateService;
    private FileTemplate templateFile;

    /**
     * Instantiates a new Velocity resource loader templates v 2.
     *
     * @param templateIn the template in
     * @param service    the service
     */
    public VelocityResourceLoaderTemplatesV2(FileTemplate templateIn, TemplateService service) {
        templateService = service;
        templateFile = templateIn;

    }

    /**
     * Init void.
     *
     * @param extendedProperties the extended properties
     */
    @Override
    public void init(ExtendedProperties extendedProperties) {

    }

    /**
     * Gets resource stream.
     *
     * @param s the s
     * @return the resource stream
     * @throws ResourceNotFoundException the resource not found exception
     */
    @Override
    public InputStream getResourceStream(String sIn) throws ResourceNotFoundException {

        LOGGER.info("Retrieving File for " + templateFile.getId() + "  " + sIn);
        InputStream result = null;
        try {

            result = new ByteArrayInputStream(templateService.getFileString(templateFile, templateFile.getTemplateSettings().getTemplatePath() + "/templates" + sIn).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {

            result = new ByteArrayInputStream(("File not found: " + sIn + "\\n").getBytes());
        }
        return result;
    }

    /**
     * Is source modified.
     *
     * @param resource the resource
     * @return the boolean
     */
    @Override
    public boolean isSourceModified(Resource resource) {

        return true;
    }

    /**
     * Gets last modified.
     *
     * @param resource the resource
     * @return the last modified
     */
    @Override
    public long getLastModified(Resource resource) {

        return 0;
    }

    /**
     * Is caching on.
     *
     * @return the boolean
     */
    @Override
    public boolean isCachingOn() {

        return false;
    }
}
