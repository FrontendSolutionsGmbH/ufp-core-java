package com.froso.ufp.modules.optional.media.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.core.domain.documents.*;
import com.froso.ufp.core.response.*;
import com.froso.ufp.modules.optional.media.model.*;
import com.froso.ufp.modules.optional.media.service.*;
import io.swagger.annotations.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;

public class AdminFileControllerBase<T extends UfpFile> extends BaseRepositoryController<T> {

    private final FileService<T> fileService;

    public AdminFileControllerBase(FileService<T> serviceIn) {
        super();
        fileService = serviceIn;
    }

    @ApiOperation(value = "reupload for existing image")
    @RequestMapping(value = "/{elementid}/upload", method = RequestMethod.POST)
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> reUploadByForm(
            @PathVariable("token") String userId,
            @RequestParam("file") final MultipartFile file,
            @PathVariable("elementid") final String elementid) throws IOException {
        //  handle file here
        String filenameID = AbstractFileServiceFTP.createIDFromString(file.getOriginalFilename());
        T element = fileService.updateElemtFromInputStreamThreaded(
                elementid,
                "FILE-UPLOAD:" + file.getOriginalFilename(),
                file.getInputStream(),
                filenameID);
        ResponseHandlerTemplateSingleObject<T> manager = new ResponseHandlerTemplateSingleObject<>();
        manager.addResult(element);
        // manager.addMessage("File Received, uploading and processing");
        return manager.getResponseEntity();
    }

    @ApiOperation(value = "create new image")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> uploadByForm(
            @PathVariable("token") String userId,
            @RequestParam("file") final MultipartFile file
    ) throws IOException {
        //  handle file here
        String filenameID = AbstractFileServiceFTP.createIDFromString(file.getOriginalFilename());

        T filedata = fileService.createNewDefault();
        filedata.setCoreUser(new DataDocumentLink<>(userId));
        filedata.getMetaData().setCreatorUserLink(new DataDocumentLink<>(userId));
        filedata.getMetaData().setLastEditorUserLink(new DataDocumentLink<>(userId));
        filedata.setName(filenameID);
        fileService.save(filedata);

        T element = fileService.updateElemtFromInputStreamThreaded(filedata.getId(), "FILE-UPLOAD:" + file.getOriginalFilename(), file.getInputStream(), filenameID);
        ResponseHandlerTemplateSingleObject<T> manager = new ResponseHandlerTemplateSingleObject<T>();
        manager.addResult(element);
        // manager.addMessage("File Received, uploading and processing");
        return manager.getResponseEntity();
    }

    @ApiOperation(value = "base64 reupload for existing image")
    @RequestMapping(value = "/{elementid}/base64/upload", method = RequestMethod.POST)
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> reUploadByBase64(
            @PathVariable("token") String userId,
            @RequestBody final MediaBase64 data,
            @PathVariable("elementid") final String elementid) throws IOException {
        //  handle file here
        String filenameID = AbstractFileServiceFTP.createIDFromString(data.getFileName());

        byte[] decoded = Base64.getDecoder().decode(data.getMediaBase64());

        InputStream is = new ByteArrayInputStream(decoded);
        T element = fileService.updateElemtFromInputStreamThreaded(
                elementid,
                "FILE-UPLOAD:" + data.getFileName(),
                is,
                filenameID);
        ResponseHandlerTemplateSingleObject<T> manager = new ResponseHandlerTemplateSingleObject<>();
        manager.addResult(element);
        // manager.addMessage("File Received, uploading and processing");
        return manager.getResponseEntity();
    }

    @ApiOperation(value = "base 64 create new image")
    @RequestMapping(value = "/base64/upload", method = RequestMethod.POST)
    public ResponseEntity<BackendResponseTemplateSingleObject<T>> uploadByBase64(
            @PathVariable("token") String userId,
            @RequestBody final MediaBase64 data
    ) throws IOException {
        //  handle file here
        String filenameID = AbstractFileServiceFTP.createIDFromString(data.getFileName());

        byte[] decoded = Base64.getDecoder().decode(data.getMediaBase64());

        InputStream is = new ByteArrayInputStream(decoded);

        T filedata = fileService.createNewDefault();
        filedata.setCoreUser(new DataDocumentLink<>(userId));
        filedata.getMetaData().setCreatorUserLink(new DataDocumentLink<>(userId));
        filedata.getMetaData().setLastEditorUserLink(new DataDocumentLink<>(userId));
        filedata.setName(filenameID);
        fileService.save(filedata);

        T element = fileService.updateElemtFromInputStreamThreaded(filedata.getId(), "FILE-UPLOAD:" + data.getFileName(), is, filenameID);
        ResponseHandlerTemplateSingleObject<T> manager = new ResponseHandlerTemplateSingleObject<T>();
        manager.addResult(element);
        // manager.addMessage("File Received, uploading and processing");
        return manager.getResponseEntity();
    }
}
