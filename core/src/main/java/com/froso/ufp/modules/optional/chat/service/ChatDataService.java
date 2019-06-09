package com.froso.ufp.modules.optional.chat.service;

import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.optional.chat.model.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 *
 * @param <T> the type parameter
 */
abstract public class ChatDataService<T extends ChatData> extends AbstractClientRefService<T> {


}
