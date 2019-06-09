package com.froso.ufp.modules.optional.barcodegenerator.controller;

import com.froso.ufp.core.*;
import com.froso.ufp.core.exceptions.*;
import com.google.zxing.*;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.*;
import com.google.zxing.oned.*;
import com.google.zxing.qrcode.*;
import io.swagger.annotations.*;
import java.io.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/BarCode")
@Api(description = "Barcode generator endpoints", tags = "BarCode")
class BarCodeGeneratorController {
    /**
     * The constant QRCODES.
     */
    private static final String IMAGE_OUTPUT_ERROR_OCCURED = "Image Output Error Occured";
    private static final int DEFAULT_SIZE = 256;
    //get log4j handler
    private static final Logger LOGGER = LoggerFactory.getLogger(BarCodeGeneratorController.class);

    /**
     * Image generator qR code.
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     * @throws WriterException the writer exception
     */
    @RequestMapping(value = "/QR_CODE", method = RequestMethod.GET)
    @ApiOperation(
            produces = UFPConstants.APPLICATION_DEFAULT_CONTENT_TYPE_UTF8,
            consumes = UFPConstants.APPLICATION_DEFAULT_CONTENT_TYPE_UTF8,
            value = "image generator for the QR Code",
            notes = "Generates and shows the image of the QR Code for a set of parameters."
    )
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorQRCode(@RequestParam(value = "value", defaultValue = "default") String value,
                                                   @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
                                                   @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,
                                                   HttpServletResponse response
    ) {
        // Initialise with empty array...

        return generateResponse(new QRCodeWriter(), BarcodeFormat.QR_CODE, value, width, height);
    }

    /**
     * Image generator eAN.
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     * @throws Exception the exception
     */
    @RequestMapping(value = "/EAN13", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorEAN(
            @RequestParam(value = "value", defaultValue = "0123456789012") String value,
            @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
            @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,
            HttpServletResponse response
    ) {
        return generateResponse(new EAN13Writer(), BarcodeFormat.EAN_13, value, width, height);
    }

    /**
     * Image generator coda bar byte [ ].
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     */
    @RequestMapping(value = "/CODABAR", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorCodaBar(
            @RequestParam(value = "value", defaultValue = "A1234567890T") String value,
            @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
            @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,

            HttpServletResponse response) {
        return generateResponse(new CodaBarWriter(), BarcodeFormat.CODABAR, value, width, height);
    }


    /**
     * Generate response http entity.
     *
     * @param writer the writer
     * @param format the format
     * @param value  the value
     * @param width  the width
     * @param height the height
     * @return the http entity
     * @throws Exception the exception
     */
    public HttpEntity<byte[]> generateResponse(Writer writer, BarcodeFormat format,
                                               String value,
                                               Integer width,
                                               Integer height) {
        // Initialise with empty array...
        byte[] bytes;
        try {
            BitMatrix bitMatrix = writer.encode(value, format, width, height);

            bytes = getBitmatryBytes(bitMatrix);

            // response.setContentLength(bytes.length);
            // response.setContentType(IMAGE_PNG);
        } catch (Exception e) {
            LOGGER.error("Error " + e.getMessage(), e);
            throw new ImageGenerationException(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new HttpEntity<>(bytes, headers);
    }


    /**
     * Image generator code 39 byte [ ].
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     * @throws Exception the exception
     */
    @RequestMapping(value = "/CODE39", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorCode39(
            @RequestParam(value = "value", defaultValue = "*DEFAULT*") String value,
            @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
            @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,

            HttpServletResponse response) {
        // Initialise with empty array...
        return generateResponse(new Code39Writer(), BarcodeFormat.CODE_39, value, width, height);
    }

    /**
     * Image generator code 39 byte [ ].
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     */
    @RequestMapping(value = "/CODE128", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorCode128(
            @RequestParam(value = "value", defaultValue = "default") String value,
            @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
            @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,

            HttpServletResponse response) {

        return generateResponse(new Code128Writer(), BarcodeFormat.CODE_128, value, width, height);
    }

    /**
     * Image generator ean 8 byte [ ].
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     * @throws Exception the exception
     */
    @RequestMapping(value = "/EAN8", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorEAN8(
            @RequestParam(value = "value", defaultValue = "01234567") String value,
            @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
            @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,

            HttpServletResponse response) {

        return generateResponse(new EAN8Writer(), BarcodeFormat.EAN_8, value, width, height);
    }

    /**
     * Image generator itf byte [ ].
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     * @throws Exception the exception
     */
    @RequestMapping(value = "/ITF", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorITF(
            @RequestParam(value = "value", defaultValue = "01234567") String value,
            @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
            @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,

            HttpServletResponse response) {

        return generateResponse(new ITFWriter(), BarcodeFormat.ITF, value, width, height);
    }

    /**
     * Image generator upca byte [ ].
     *
     * @param value    the value
     * @param width    the width
     * @param height   the height
     * @param response the response
     * @return the byte [ ]
     * @throws Exception the exception
     */
    @RequestMapping(value = "/UPCA", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> imageGeneratorUPCA(
            @RequestParam(value = "value", defaultValue = "01234567890") String value,
            @RequestParam(value = "width", defaultValue = DEFAULT_SIZE + "") Integer width,
            @RequestParam(value = "height", defaultValue = DEFAULT_SIZE + "") Integer height,

            HttpServletResponse response) {

        return generateResponse(new UPCAWriter(), BarcodeFormat.UPC_A, value, width, height);
    }


    private byte[] getBitmatryBytes(BitMatrix bitMatrix) {
        byte[] bytes = new byte[0];
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);
            bytes = outputStream.toByteArray();
        } catch (Exception e) {
            LOGGER.error(IMAGE_OUTPUT_ERROR_OCCURED + e.getMessage(), e);
        }
        return bytes;
    }

}
