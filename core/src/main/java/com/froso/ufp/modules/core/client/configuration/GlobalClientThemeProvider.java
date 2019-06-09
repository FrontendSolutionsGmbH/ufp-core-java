package com.froso.ufp.modules.core.client.configuration;

import com.froso.ufp.core.domain.documents.simple.plain.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.client.service.*;
import com.froso.ufp.modules.core.globals.interfaces.*;
import com.froso.ufp.modules.optional.media.model.*;
import com.froso.ufp.modules.optional.media.service.*;
import com.froso.ufp.modules.optional.theme.model.*;
import com.froso.ufp.modules.optional.theme.service.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;

/**
 * Created by ckleinhuis on 01.11.2017.
 */
public class GlobalClientThemeProvider implements IGlobalsPropertyProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalClientThemeProvider.class);
    @Autowired
    private IClientService clientService;
    @Autowired(required = false)
    private PictureService pictureService;
    @Autowired(required = false)
    private ThemeService themeService;
    @Autowired
    private ClientManagerService clientManagerService;


    public Object getPropertyValue() {

        // return aggregated map of key values from the various theme settings
        Map<String, String> result = new HashMap<>();

        IClient client = (IClient) clientService.findOneBrute(clientManagerService.getCurrentClientId());

        ThemeModel resultModel = new ThemeModel();
        if (client instanceof ThemedClient) {

            ThemedClient clientThemed = (ThemedClient) client;

            if (themeService != null) {
                if (clientThemed.getTheme() != null) {
                    try {
                        resultModel = themeService.findOneBrute(clientThemed.getTheme().getId());

                    } catch (Exception e) {
                        LOGGER.error("ClientTheme findtheme", e);

                    }
                }
            }
        }
        if (resultModel != null) {
            result.put("themeName", resultModel.getName());

            if (pictureService != null) {
                if (resultModel.getTeaser() != null) {

                    FilePicture pic = pictureService.findOneBrute(resultModel.getTeaser().getId());
                    if (null != pic) {
                        result.put("teaserImageUrl", pic.getMediaElement().getLivePath());
                    }
                }

                if (resultModel.getLogo() != null) {

                    FilePicture pic = pictureService.findOneBrute(resultModel.getLogo().getId());
                    if (null != pic) {
                        result.put("logoImageUrl", pic.getMediaElement().getLivePath());
                    }
                }

                if (resultModel.getProductLogo() != null) {

                    FilePicture pic = pictureService.findOneBrute(resultModel.getProductLogo().getId());
                    if (null != pic) {
                        result.put("productLogoImageUrl", pic.getMediaElement().getLivePath());
                    }
                }
            }

            if (resultModel.getProperties() != null) {
                for (KeyValueItem item : resultModel.getProperties()) {
                    if ("".equals(item.getKey()) || item.getKey() == null) {
                        // do nothing if key isnull or empty
                    } else {
                        result.put(item.getKey(), item.getValue());
                    }
                }
            }


            if (resultModel.getColors() != null) {
                for (KeyValueItemTyped<UfpColor> item : resultModel.getColors()) {
                    if ("".equals(item.getKey()) || item.getKey() == null) {
                        // do nothing if key isnull or empty
                    } else {
                        if (item.getValue() != null) {
                            result.put(item.getKey(), item.getValue().getHex());
                        }
                    }
                }
            }


        }

        return result;
    }

}

