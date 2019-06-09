package com.froso.ufp.modules.core.workflow.service.workflow.activities.script;

import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.service.workflow.*;
import javax.script.*;
import org.slf4j.*;

/**
 * Created by ckleinhuix on 14.12.13.
 * <p>
 * the sendactivation email activity just sends out a velocity template email to the user containing a link for
 * continuing the workflowq
 */
public class WorkflowActivityJavaScript extends AbstractActivityBoolean<WorkflowContextImpl> {


    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "JavaScript";
    /**
     * The constant PROP_SCRIPT_CODE.
     */
    public static final String PROP_SCRIPT_CODE = "scriptCode";
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowActivityJavaScript.class);

    @Override
    protected void initialise() {

        getProps().getProp(PROP_SCRIPT_CODE, "function main(){" +
                "context.clickatellsmsprovider='JAÖÖP';" +
                "return true;}");
    }


    /**
     * Evaluate void.
     *
     * @param workflowContext the workflow context
     * @throws WorkflowException the workflow exception
     */
    @Override
    public boolean evaluate(WorkflowContextImpl workflowContext) throws WorkflowException {
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");

        // javax.script.Invocable is an controller interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        Invocable inv = (Invocable) engine;

        engine.put("context", workflowContext.getContextData());
        try {

            // evaluate script
            engine.eval(getProps().getProp("scriptCode"));

            // invoke the global function named "hello"
            Object resultObj = inv.invokeFunction("main");
            Boolean result = Boolean.parseBoolean(resultObj.toString());
            return result.equals(Boolean.TRUE);
        } catch (ScriptException exception) {
            LOGGER.error("Script Execution Failure ", exception);
            return false;

        } catch (NoSuchMethodException exception) {
            LOGGER.error("No Such Method on Javascript Code found ", exception);
            return false;
        }
    }
}
