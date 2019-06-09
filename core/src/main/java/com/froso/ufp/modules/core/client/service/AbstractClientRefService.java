package com.froso.ufp.modules.core.client.service;

import com.fasterxml.jackson.databind.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.session.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;

import java.util.*;

public class AbstractClientRefService<T extends IDataDocumentWithClientLink> extends AbstractRepositoryService2<T> {

    @Autowired
    private ClientManagerService clientService;

    @Deprecated
    public AbstractClientRefService() {
        super();
    }

    public AbstractClientRefService(String thetype,
                                    ClientManagerService clientService,
                                    MongoOperations mongoOperations,
                                    MongoTemplate mongoTemplate,
                                    ObjectMapper objectMapper,
                                    SessionService sessionService,
                                    ResourcesService resourcesService) {
        super(thetype, mongoOperations, mongoTemplate, objectMapper, sessionService, resourcesService);
        this.clientService = clientService;
    }

    @Override
    public T createNewDefault() {

        T result = super.createNewDefault();
        result.setClient(new DataDocumentLink<>(clientService.getCurrentClientId()));
        //   save(result);
        return result;
    }

    @Override
    protected void prepareSave(T data) {

        if (data.getClient() == null) {
            // set default client if not already set
            data.setClient(new DataDocumentLink<>(clientService.getCurrentClientId()));
        } else if (data.getClient().getId() == null) {
            data.setClient(new DataDocumentLink<>(clientService.getCurrentClientId()));

        }
    }

    @Override
    public DataObjectList<T> searchPaged(Map<String, String> searchkeyvalues) {

        Map<String, String> transform = new HashMap<>(searchkeyvalues);
        transform.putAll(getAdditionalSearch());

        return super.searchPaged(transform);
    }

    @Override
    public List<T> findByKeyValues(Map<String, String> keyvalues) {

        Map<String, String> transform = new HashMap<>(keyvalues);
        transform.putAll(getAdditionalSearch());

        return super.findByKeyValues(transform);
    }

    private Map<String, String> getAdditionalSearch() {
        Map<String, String> transform = new HashMap<>();
        // hm, here all findBy methods get the additional client filter

        String requestClientId = clientService.getCurrentClientId();
        if (null != requestClientId) {
            if (!requestClientId.equals(clientService.getDefaultClientId())) {
                // default client is untouched

                if (transform.get("client.id") == null) {
                    // hmm, only set if not present,
                    transform.put("client.id", requestClientId);
                }
            }
        }

        return transform;

    }

}
