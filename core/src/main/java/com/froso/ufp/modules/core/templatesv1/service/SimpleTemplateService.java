package com.froso.ufp.modules.core.templatesv1.service;

import com.froso.ufp.core.service.*;
import com.froso.ufp.core.util.*;
import com.froso.ufp.modules.core.templatesv1.model.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * Created by alex on 05.05.14.
 */
@Service
public class SimpleTemplateService extends AbstractRepositoryService2<TemplateV1> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTemplateService.class);

    @Autowired
    private ServerService serverService;

    /**
     * Constructor Simple template service.
     */
    public SimpleTemplateService() {

        super(TemplateV1.TYPE_NAME);
    }

    private static TemplateV1 findByRessourcePath(String path) {
        TemplateV1 result = new TemplateV1();
        try {
            String contents = FileUtil.getFile(path);
            result.setContent(contents);
        } catch (Exception e) {
            LOGGER.error("findByRessourcePath ", e);
        }
        return result;
    }

    /**
     * Find by parent id.
     *
     * @param id the id
     * @return the list
     */
    public List<TemplateV1> findByParentId(String id) {

        return findByKeyValue("parentId", id);
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the simple template
     */
    public TemplateV1 findByName(String name) {

        TemplateV1 result = findOneByKeyValue("name", name);
        if (result != null) {
            prepareResultElement(result);
        }
        return result;
    }

    /**
     * Prepare result element.
     *
     * @param element the element
     */
    @Override
    protected void prepareResultElement(TemplateV1 element) {

        if (element.getCorporateId() == null) {
            element.setFullPageUrl(serverService.getApiUrl() + "/" + TemplateV1.TYPE_NAME + "/" + element.getName());
        } else {
            element.setFullPageUrl(serverService.getApiUrl() + "/" + TemplateV1.TYPE_NAME + "/" + element.getCorporateId() + "/" + element.getName());
        }
    }

    /**
     * Find by name and corporate id.
     *
     * @param name        the name
     * @param corporateId the corporate id
     * @return the simple template
     */
    public TemplateV1 findByNameAndCorporateId(String name, String corporateId) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("name", name);
        searchMap.put("corporateId", corporateId);

        TemplateV1 result = findOneByKeyValues(searchMap);
        if (result != null) {
            prepareResultElement(result);
        }
        return result;
    }

    /**
     * Find by path.
     *
     * @param path the path
     * @return the simple template
     */
    public TemplateV1 findByPath(String path) {

        TemplateV1 result = findOneByKeyValue("path", path);

        if (result == null) {
            // try to retrieve template by resource path
            result = findByRessourcePath(path);
        }

        return result;

    }

    /**
     * Find by path and corporate id.
     *
     * @param path        the path
     * @param corporateId the corporate id
     * @return the simple template
     */
    TemplateV1 findByPathAndCorporateId(String path, String corporateId) {

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("path", path);
        searchMap.put("corporateId", corporateId);
        TemplateV1 result = findOneByKeyValues(searchMap);
        if (result != null) {
            prepareResultElement(result);
        }
        return result;
    }
}
