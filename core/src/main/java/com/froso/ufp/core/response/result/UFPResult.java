package com.froso.ufp.core.response.result;

import org.joda.time.*;

/**
 * Created by ck on 22.06.2016.
 */
public final class UFPResult {


    private final int code;
    private final String name;
    private final String description;
    private final LocalDate dateIntroduced;


    public UFPResult(String name, int code, String description, LocalDate dateIntroduced) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.dateIntroduced = dateIntroduced;
    }

    public final int getCode() {
        return code;
    }

    public final String getName() {
        return name;
    }

    public final String getDescription() {
        return description;
    }

    public final LocalDate getDateIntroduced() {
        return dateIntroduced;
    }
}
