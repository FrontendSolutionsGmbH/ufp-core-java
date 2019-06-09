package com.froso.ufp.modules.optional.textbot.yesno2.actions;

import com.froso.ufp.modules.optional.textbot.yesno2.model.*;
import com.froso.ufp.modules.optional.textbot.yesno2.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 15/02/2017.
 */
@Component
public class CountFor {

    @Autowired
    private YesNoTextBotService yesNoTextBotService;

    /**
     * Instantiates a new Execute.
     *
     * @param voteid the voteid
     */
    public void execute(String voteid) {
        YesNoTextBotModel model = yesNoTextBotService.findOne(voteid);
        model.setYesCount(model.getYesCount() + 1);
        yesNoTextBotService.save(model);
    }
}
