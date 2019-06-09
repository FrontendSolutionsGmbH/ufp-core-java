package com.froso.ufp.modules.core.workflow.service.workflow.activities.email;

import com.froso.ufp.modules.core.workflow.model.workflow.*;
import com.froso.ufp.modules.core.workflow.service.*;
import com.froso.ufp.modules.core.workflow.service.workflow.*;
import com.froso.ufp.modules.core.workflow.service.workflow.activities.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Created by ckleinhuix on 14.12.13.
 * <p>
 * the sendactivation email activity just sends out a velocity template email to the user containing a link for
 * continuing the workflowq
 */
public class ActivitySendEmail extends AbstractActivityVoid<WorkflowContextImpl> {
    /**
     * The constant PROP_EMAIL_SUBJECT.
     */
    public static final String PROP_EMAIL_SUBJECT = "emailSubject";

    /**
     * The constant PROP_FROM.
     */
    public static final String PROP_FROM = "from";
    /**
     * The constant PROP_TO.
     */
    public static final String PROP_TO = "to";
    /**
     * The constant TYPE_NAME.
     */
    public static final String NAME = "SendEmail";
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivitySendEmail.class);
    @Autowired
    private WorkflowService workflowService;

    /**
     * Constructor Entiteit workflow activity send email.
     */
    public ActivitySendEmail() {

        super();


    }

    @Override
    protected void initialise() {

        getProps().getProp(PROP_EMAIL_SUBJECT, "UFP Backend Email");
        getProps().getProp(PROP_FROM, "defaultFrom@email.com");
        getProps().getProp(PROP_TO, "defaultTo@email.com");
        getProps().getProp(DefaultProps.Template.PROP_TEMPLATE_LINK_ID, DefaultProps.Template.DEFAULT_TEMPLATE_LINK_ID);
        getProps().getProp(DefaultProps.Template.PROP_TEMPLATE_ENTRY, DefaultProps.Template.DEFAULT_TEMPLATE_ENTRY);
        getProps().getProp(DefaultProps.Template.PROP_TEMPLATE_ENTRY_CONTAINER, DefaultProps.Template.DEFAULT_TEMPLATE_ENTRY_CONTAINER);

    }


    /**
     * Evaluate void.
     *
     * @param workflowContext the workflow context
     */
    @Override
    public void evaluate(WorkflowContextImpl workflowContext) {
        LOGGER.info("ActivitySendEmail");
        // FIXME: GENERALIZE SENDEMAIL WORKFLOW STEP!    used more than once!
        // String activationurl = workflowContext.getServerService().getApiUrl() + "/Workflow/" + workflowContext.getContextData().get(
        //            AbstractWorkflowElement.WORKFLOW_ID) + "?nonce=" + workflowContext.getContextData().get(ActivityStoreNonce.NONCE_NAME);
        Map<String, Object> data = new HashMap<>();
        data.put("context", workflowContext.getContextData());
        LOGGER.info("Autowiring check: " + workflowService);
    /*      try {

        } catch (PropertyException e) {
            throw new WorkflowException("MailheaderProblem ", e);
        }
        workflowContext.getEmailSenderService().sendHTMLVelocityTemplateEmail(workflowContext.getCoreUser().getEmail(), getProps().getProp(PROP_EMAIL_SUBJECT),
                "corporateid/" + getProps().getProp(PROP_TEMPLATE_LINK_ID), data);
                 */
    }
}
