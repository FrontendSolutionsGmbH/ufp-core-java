package com.froso.ufp.modules.optional.media.service.util;

import com.froso.ufp.modules.optional.media.model.media.*;

/**
 * Created by ckleinhuix on 12.12.2015.
 */
public interface FileTypeDeterminer {
    boolean isOfTypeAndSet(MediaElement mediaElement, byte[] bytes);
}
