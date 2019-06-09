package com.froso.ufp.modules.optional.latex.service;

import com.froso.ufp.core.exceptions.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import org.scilab.forge.jlatexmath.*;
import org.springframework.stereotype.*;

/**
 * Created with IntelliJ IDEA. Christian Kleinhuis (ck@froso.de) Date: 28.11.13 Time: 11:46 Services act as
 * Proxy/Model Classes, any Model relevant actions that the application performs shall be routed through the service
 * instances
 */
@Service
public class LaTeXService {


    /**
     * The constant MAX_SIZE.
     */
    public static final int MAX_SIZE = 250;

    /**
     * Generate latex image buffered image.
     *
     * @param latex the latex
     * @param size  the size
     * @return the buffered image
     */
    public BufferedImage generateLatexImage(String latex, float size) {

        BufferedImage b;
        try {
            if (size > MAX_SIZE) {
                size = MAX_SIZE;
            }
            if (size < 0) {
                size = 0;
            }

            String math = latex;// "\\frac {V_m} {K_M+S}";

            TeXFormula teXFormula = new TeXFormula(math);
            TeXIcon ti = teXFormula.createTeXIcon(
                    TeXConstants.STYLE_DISPLAY, size);
            ti.setForeground(Color.black);

            b = new BufferedImage(ti.getIconWidth(), ti
                    .getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            ti.paintIcon(new JLabel(), b.getGraphics(), 0, 0);
            // Create a byte array output stream.

        } catch (Exception e) {

            throw new ImageGenerationException(e.getMessage(), e);

        }

        return b;
    }
}
