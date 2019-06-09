package com.froso.ufp.modules.core.workflow.model.status;

import com.fasterxml.jackson.annotation.*;
import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.workflow.model.listener.*;
import java.util.*;
import org.joda.time.*;
import org.joda.time.format.*;

/**
 * Created by alex on 22.02.14.
 */
public class ServiceStatus implements IDataObject {
    private List<String> log = new ArrayList<>();
    private List<Exception> exceptions = new ArrayList<>();
    private Map<String, Object> data = new HashMap<>();
    // initialise with true, "true" shall not be set by services, only false values make sense here ;)
    private Boolean isWorking = true;
    private String name;
    private DefaultImportListenerImpl importListener = new DefaultImportListenerImpl();

    /**
     * Gets import listener.
     *
     * @return the import listener
     */
    public DefaultImportListenerImpl getImportListener() {
        return importListener;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * Gets is working.
     *
     * @return the is working
     */
    public Boolean getIsWorking() {

        return isWorking;
    }

    /**
     * Sets is working.
     *
     * @param isWorking the is working
     */
    public void setIsWorking(Boolean isWorking) {

        this.isWorking = isWorking;
    }

    /**
     * Gets exceptions.
     *
     * @return the exceptions
     */
    public List<Exception> getExceptions() {

        return exceptions;
    }

    /**
     * Add exceptions.
     *
     * @param exception the exceptions
     */
    public void addException(Exception exception) {

        this.exceptions.add(exception);
    }

    /**
     * Add exceptions.
     *
     * @param exception the exception
     */
    public void addExceptions(List<Exception> exception) {

        this.exceptions.addAll(exception);
    }

    public void setLog(List<String> log) {
        this.log = log;
    }

    public void setExceptions(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public void setWorking(Boolean working) {
        isWorking = working;
    }

    public void setImportListener(DefaultImportListenerImpl importListener) {
        this.importListener = importListener;
    }

    /**
     * Gets log as string.
     *
     * @return the log as string
     */
    @JsonIgnore
    public String getLogAsString() {

        StringBuilder result = new StringBuilder();
        for (String s : getLog()) {
            result.append(s);
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * Gets log.
     *
     * @return the log
     */
    public List<String> getLog() {

        return log;
    }

    /**
     * Add log message.
     *
     * @param msg the msg
     */
    public void addLogMessages(List<String> msg) {
        for (String row : msg) {
            addLogMessage(row);

        }
    }

    /**
     * Add log message.
     *
     * @param msg the msg
     */
    public void addLogMessage(String msg) {

        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
        log.add(dtf.print(DateTime.now()) + ":" + msg);
    }


    /**
     * Gets data.
     *
     * @return the data
     */
    public Map<String, Object> getData() {

        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(Map<String, Object> data) {

        this.data = data;
    }
}
