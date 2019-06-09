package com.froso.ufp.core.service.util.query;

import org.springframework.data.mongodb.core.query.*;

/**
 * Created by alex on 13.04.14.
 */
public interface CriteriaFactory {
    /**
     * Create criteria.
     *
     * @return the criteria
     */
    Criteria createCriteria();
}
