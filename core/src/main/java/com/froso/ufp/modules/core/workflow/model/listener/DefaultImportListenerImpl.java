package com.froso.ufp.modules.core.workflow.model.listener;

import com.froso.ufp.core.domain.interfaces.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import java.util.*;
import org.joda.time.*;
import org.joda.time.format.*;
import org.slf4j.*;

/**
 * Created with IntelliJ IDEA.
 * Entiteit: ck
 * Date: 12.03.14
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public class DefaultImportListenerImpl implements ImportListenerInterface, ImportListenerReporter, IDataObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultImportListenerImpl.class);
    private String name;
    private List<ImportListenerInterface> children = new ArrayList<>();
    private Double progress = 0.0D;
    private List<String> collectedExceptions = new ArrayList<>();
    private List<WorkflowLogMessage> collectedInfo = new ArrayList<>();
    private List<WorkflowLogMessage> collectedWarnings = new ArrayList<>();
    private List<WorkflowLogMessage> collectedErrors = new ArrayList<>();
    private boolean shouldStop = false;

    /**
     * Constructor Default import listener impl.
     */
    public DefaultImportListenerImpl() {

    }

    /**
     * Constructor Default import listener impl.
     *
     * @param nameIn the name in
     */
    public DefaultImportListenerImpl(String nameIn) {

        name = nameIn;
    }

    /**
     * Gets children.
     *
     * @return the children
     */
    public List<ImportListenerInterface> getChildren() {
        return children;
    }

    /**
     * Sets children.
     *
     * @param children the children
     */
    public void setChildren(final List<ImportListenerInterface> children) {

        this.children = children;
    }

    /**
     * Gets collected exceptions.
     *
     * @return the collected exceptions
     */
    public List<String> getCollectedExceptions() {
        return collectedExceptions;
    }

    /**
     * Sets collected exceptions.
     *
     * @param collectedExceptions the collected exceptions
     */
    public void setCollectedExceptions(List<String> collectedExceptions) {
        this.collectedExceptions = collectedExceptions;
    }

    /**
     * Gets collected info.
     *
     * @return the collected info
     */
    public List<WorkflowLogMessage> getCollectedInfo() {
        return collectedInfo;
    }

    /**
     * Sets collected info.
     *
     * @param collectedInfo the collected info
     */
    public void setCollectedInfo(List<WorkflowLogMessage> collectedInfo) {
        this.collectedInfo = collectedInfo;
    }

    /**
     * Gets collected warnings.
     *
     * @return the collected warnings
     */
    public List<WorkflowLogMessage> getCollectedWarnings() {
        return collectedWarnings;
    }

    /**
     * Sets collected warnings.
     *
     * @param collectedWarnings the collected warnings
     */
    public void setCollectedWarnings(List<WorkflowLogMessage> collectedWarnings) {
        this.collectedWarnings = collectedWarnings;
    }

    /**
     * Gets collected errors.
     *
     * @return the collected errors
     */
    public List<WorkflowLogMessage> getCollectedErrors() {
        return collectedErrors;
    }

    /**
     * Sets collected errors.
     *
     * @param collectedErrors the collected errors
     */
    public void setCollectedErrors(List<WorkflowLogMessage> collectedErrors) {
        this.collectedErrors = collectedErrors;
    }

    /**
     * Is should stop boolean.
     *
     * @return the boolean
     */
    public boolean isShouldStop() {
        return shouldStop;
    }

    /**
     * Sets should stop.
     *
     * @param shouldStop the should stop
     */
    public void setShouldStop(boolean shouldStop) {
        this.shouldStop = shouldStop;
    }

    /**
     * Gets exceptions.
     *
     * @return the exceptions
     */
    public List<String> getExceptions() {

        return collectedExceptions;

    }

    @Override
    public List<WorkflowLogMessage> getReport() {

        List<WorkflowLogMessage> result = new ArrayList<>();
        result.addAll(collectedErrors);
        result.addAll(collectedInfo);
        result.addAll(collectedWarnings);
        Collections.sort(result);

        return result;
    }

    /**
     * Should stop.
     *
     * @return the boolean
     */

    @Override
    public Double getProgress() {

        return progress;
    }

    /**
     * Sets progress in progress.
     *
     * @param t            the t
     * @param percentageIn the progress in
     */
    @Override
    public void setProgress(
            Double percentageIn) {

        progress = percentageIn;
    }

    /**
     * Should continue.
     *
     * @return the boolean
     */
    @Override
    public boolean shouldContinue() {

        return !shouldStop();
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    boolean shouldStop() {

        return shouldStop;
    }

    /**
     * Request stop.
     */
    public void requestStop() {

        shouldStop = true;
        // progpagate to children
        for (ImportListenerInterface child : children) {
            child.requestStop();
        }
    }

    /**
     * Report exception.
     *
     * @param ex the ex
     */
    @Override
    public void reportException(Exception ex) {
        // implementation due to interface definition
        LOGGER.error("EXCEPTION:", ex);
        collectedExceptions.add(toLogString("EXCEPTION", ex.getMessage()));
    }

    /**
     * Report info.
     *
     * @param info the info
     */
    @Override
    public void reportInfo(String info) {

        collectedInfo.add(WorkflowLogMessage.createInfo(info));
        LOGGER.info("INFO:");
    }

    /**
     * Report warning.
     *
     * @param warning the warning
     */
    @Override
    public void reportWarning(String warning) {
        // implementation due to interface definition
        collectedWarnings.add(WorkflowLogMessage.createWarning(warning));
        LOGGER.warn(warning);
    }

    /**
     * Report error.
     *
     * @param error the error
     */
    @Override
    public void reportError(String error) {

        collectedErrors.add(WorkflowLogMessage.createError(error));
        LOGGER.error(error);
    }

    private String toLogString(String type,
                               String input) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd.MM HH:mm:ss:SSSS");
        return "[" + fmt.print(DateTime.now()) + "] " + input;
    }

    /**
     * Create child listener.
     *
     * @param name the name
     * @return the import listener interface
     */
    public ImportListenerInterface createChildListener(String name) {

        DefaultImportListenerImpl result = new DefaultImportListenerImpl();
        result.setName(name);
        this.children.add(result);
        return result;
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
    public void setName(final String name) {

        this.name = name;
    }
}
