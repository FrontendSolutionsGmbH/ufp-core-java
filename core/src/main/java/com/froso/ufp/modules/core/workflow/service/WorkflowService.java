package com.froso.ufp.modules.core.workflow.service;

import com.froso.ufp.modules.core.workflow.model.*;
import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.model.workflow.domain.*;
import com.froso.ufp.modules.core.workflow.service.workflow.*;
import com.froso.ufp.modules.core.workflow.service.workflow.activities.email.*;
import com.froso.ufp.modules.core.workflow.service.workflow.activities.script.*;
import javax.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 11.01.2016.
 */
@Service
public class WorkflowService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowActivityJavaScript.class);
    /**
     * a workflow element defines possibilities to join activities, the elements define the input/outout nodes of a workflow
     *
     * @param element the element
     * @param name the name
     * @return list
     */
    /**
     * The Name.
     */

    private WorkflowActivityMap activities = new WorkflowActivityMap();


    /**
     * Gets activities.
     *
     * @return the activities
     */
    public WorkflowActivityMap getActivities() {
        return activities;
    }

    /**
     * a workflow activity is some actions actually performed, the activities depend on the workflow element, each
     * workflow element defines an interface that the activity has to implement, e.g. boolean return value of an
     * activity
     *
     * @param name     the name
     * @param activity the activity
     */
    public void registerWorkflowActivity(String name, WorkflowActivity activity) {
        WorkflowContextImpl context = new WorkflowContextImpl();

        if (activity instanceof WorkflowActivityJavaScript) {
            try {
                ((WorkflowActivityJavaScript) activity).evaluate(context);
            } catch (Exception e) {
                LOGGER.error("registerWorkflowActivity ", e);
            }
        }


        activities.put(name, activity);

    }


    /**
     * Gets activity by name.
     *
     * @param name  the name
     * @param props the props
     * @return the activity by name
     */
    public WorkflowActivity constructActivityByName(String name, PropsManager props) {
        WorkflowActivity activity = activities.get(name);
        WorkflowActivity result = null;
        if (activity != null) {
            try {
                result = activity.getClass().newInstance();

                // fixme: todo: clone incoming props object here
                result.getProps().setProps(props.getProps());

            } catch (Exception e) {

                LOGGER.error("Activity Construction failure: ", e);

            }
        }
        return result;
    }

    /**
     * fixme move initialisation to controller, for development reason a set is initiated here
     */
    @PostConstruct
    public void initActivities() {


        registerWorkflowActivity(ActivitySendEmail.NAME, new ActivitySendEmail());
        registerWorkflowActivity(WorkflowActivityJavaScript.NAME, new WorkflowActivityJavaScript());


    }


}
