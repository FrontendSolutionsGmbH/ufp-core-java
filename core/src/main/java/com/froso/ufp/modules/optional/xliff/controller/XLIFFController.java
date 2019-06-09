package com.froso.ufp.modules.optional.xliff.controller;

import com.froso.ufp.core.response.*;
import com.froso.ufp.core.service.*;
import com.froso.ufp.modules.optional.xliff.model.*;
import com.froso.ufp.modules.optional.xliff.xsd.xliff2.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.xml.bind.*;
import java.io.*;
import java.nio.charset.*;

@Controller
@RequestMapping(value = "/" + XLIFFModel.TYPE_NAME)
class XLIFFController {

    private final RepositoryService<XLIFFModel> service;

    @Autowired
    public XLIFFController(RepositoryService<XLIFFModel> service) {

        this.service = service;
    }

    @ApiOperation(value = "imports a default data set (if defined in service)", notes = "If the corresponding Service associated to this controller has implemented a importDefault() method, this collection will be reset to that (or empty) .")
    @RequestMapping(value = "/LoadXliff", method = RequestMethod.POST)
    public ResponseEntity<BackendResponse> importDefaults(HttpServletRequest request, @RequestBody String body) throws Exception {

        ResponseHandlerTemplate manager = new ResponseHandlerTemplate<>(request);

        JAXBContext jc = JAXBContext.newInstance(Xliff.class);
        Unmarshaller u = jc.createUnmarshaller();
        Xliff po = (Xliff) u.unmarshal(
                new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)));
        XLIFFModel model = new XLIFFModel();
        model.setXliff(po);
        manager.addResult(model);
        return manager.getResponseEntity();
    }

}
