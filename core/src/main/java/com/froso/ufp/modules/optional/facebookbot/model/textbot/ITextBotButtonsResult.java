package com.froso.ufp.modules.optional.facebookbot.model.textbot;

import java.util.*;

/**
 * Created by ck on 18.08.2016.
 */
public interface ITextBotButtonsResult extends ITextBotStringResult {
    List<ITextBotButton> getButtons();
}
