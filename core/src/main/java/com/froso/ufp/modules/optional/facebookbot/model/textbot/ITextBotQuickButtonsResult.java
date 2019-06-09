package com.froso.ufp.modules.optional.facebookbot.model.textbot;

import java.util.*;

/**
 * Created by ck on 18.08.2016.
 */
public interface ITextBotQuickButtonsResult extends ITextBotStringResult {
    /**
     * Gets quick replies.
     *
     * @return the quick replies
     */
    List<ITextBotQuickButton> getQuick_replies();
}
