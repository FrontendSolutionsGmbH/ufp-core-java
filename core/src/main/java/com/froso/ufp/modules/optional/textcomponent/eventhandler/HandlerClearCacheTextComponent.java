package com.froso.ufp.modules.optional.textcomponent.eventhandler;

import com.froso.ufp.core.domain.events.*;
import com.froso.ufp.modules.optional.textcomponent.model.textcomponent.*;
import org.springframework.stereotype.*;

/**
 * Created by ckleinhuix on 15.05.2015.
 *
 * @param <T> the type parameter
 */
@Component
public class HandlerClearCacheTextComponent {


    /**
     * The type Handler clear value.
     */
    @Component
    public static class HandlerClearOnTextValue extends HandlerClearCache<TextComponentValue, DataDocumentSaveEvent> {

    }

    /**
     * The type Handler clear value 2.
     */
    @Component
    public static class HandlerClearOnTextLanguage extends HandlerClearCache<TextComponentLanguage, DataDocumentSaveEvent> {
    }

    /**
     * The type Handler clear on text component.
     */
    @Component
    public static class HandlerClearOnTextComponent extends HandlerClearCache<TextComponent, DataDocumentSaveEvent> {

    }

    /**
     * The type Handler clear on text value delete.
     */
    @Component
    public static class HandlerClearOnTextValueDelete extends HandlerClearCache<TextComponentValue, DataDocumentDeleteEvent> {

    }

    /**
     * The type Handler clear value 2.
     */
    @Component
    public static class HandlerClearOnTextLanguageDelete extends HandlerClearCache<TextComponentLanguage, DataDocumentDeleteEvent> {
    }

    /**
     * The type Handler clear on text component.
     */
    @Component
    public static class HandlerClearOnTextComponentDelete extends HandlerClearCache<TextComponent, DataDocumentDeleteEvent> {
    }

    /**
     * The type Handler clear on text value create.
     */
    @Component
    public static class HandlerClearOnTextValueCreate extends HandlerClearCache<TextComponentValue, DataDocumentCreateEvent> {

    }

    /**
     * The type Handler clear value 2.
     */
    @Component
    public static class HandlerClearOnTextLanguageCreate extends HandlerClearCache<TextComponentLanguage, DataDocumentCreateEvent> {
    }

    /**
     * The type Handler clear on text component.
     */
    @Component
    public static class HandlerClearOnTextComponentCreate extends HandlerClearCache<TextComponent, DataDocumentCreateEvent> {

    }


}


