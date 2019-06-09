package com.froso.ufp.modules.optional.xliff.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.xliff.model.*;
import com.froso.ufp.modules.optional.xliff.xsd.xliff2.*;
import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.*;
import javax.xml.bind.*;
import java.io.*;

@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + XLIFFModel.TYPE_NAME)
class AdminXLIFFController extends BaseRepositoryController<XLIFFModel> {

    @ApiOperation(value = "imports a default data set (if defined in service)", notes = "If the corresponding Service associated to this controller has implemented a importDefault() method, this collection will be reset to that (or empty) .")
    @RequestMapping(value = "/{id}/ImportXliff", method = RequestMethod.POST)
    public ResponseEntity<BackendResponse> importDefaults(@PathVariable final String userId,
                                                          @PathVariable final String id,
                                                          @RequestParam final MultipartFile file,
                                                          HttpServletRequest request
    ) throws Exception {

        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);
        XLIFFModel xliffModel = this.service.findOne(id);
        JAXBContext jc = JAXBContext.newInstance(Xliff.class);
        Unmarshaller u = jc.createUnmarshaller();
        Xliff po = (Xliff) u.unmarshal(
                file.getInputStream());

        xliffModel.setXliff(po);
        service.save(xliffModel);
        manager.addResult(xliffModel);
        return manager.getResponseEntity();
    }

    @ApiOperation(value = "exports a default data set (if defined in service)", notes = "If the corresponding Service associated to this controller has implemented a importDefault() method, this collection will be reset to that (or empty) .")
    @RequestMapping(value = "/{id}/GetXliff",
            method = RequestMethod.GET
    )

    public ResponseEntity<String> exportXliff(@PathVariable final String userId,
                                              @PathVariable final String id,
                                              HttpServletResponse response
    ) throws Exception {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/xml; charset=utf-8");

        XLIFFModel xliffModel = this.service.findOne(id);
        JAXBContext jc = JAXBContext.newInstance(Xliff.class);
        Marshaller u = jc.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        u.marshal(xliffModel.getXliff(), stringWriter);
        return new ResponseEntity<>(stringWriter.toString(), responseHeaders, HttpStatus.OK);
    }

}
