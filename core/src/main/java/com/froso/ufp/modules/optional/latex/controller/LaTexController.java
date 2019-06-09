package com.froso.ufp.modules.optional.latex.controller;

import com.froso.ufp.modules.optional.latex.service.*;
import io.swagger.annotations.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 17.11.13 Time: 16:46 To change
 * this template use File | Settings | File Templates. generates various mostly image outputs
 */
@Controller
@RequestMapping("/LaTeX")

@Api(description = "LaTeX generator endpoints", tags = "LaTeX")
class LaTexController {
    @Autowired
    private LaTeXService laTeXService;

    /**
     * Image generator qR code.
     *
     * @param latex    the latex
     * @param size     the size
     * @param response the response
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    @RequestMapping(value = "/img", method = RequestMethod.GET)

    public
    @ResponseBody
    HttpEntity<byte[]> generateLatexImage(@RequestParam(value = "latex", defaultValue = "\\frac {V_m} {K_M+S}") String latex,
                                          @RequestParam(value = "size", defaultValue = "40.0") float size,

                                          HttpServletResponse response) throws IOException {

        byte[] result;


        BufferedImage b = laTeXService.generateLatexImage(latex, size);
        // Create a byte array output stream.
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        // Write to output stream
        ImageIO.write(b, "png", bao);
        result = bao.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new HttpEntity<>(result, headers);
    }

}
