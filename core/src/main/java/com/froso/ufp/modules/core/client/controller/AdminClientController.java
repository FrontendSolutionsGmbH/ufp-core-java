package com.froso.ufp.modules.core.client.controller;

import com.froso.ufp.core.controller.*;
import com.froso.ufp.modules.core.client.model.*;
import com.froso.ufp.modules.core.client.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA. SimpleUser:Christian Kleinhuis (ck@froso.de) Date: 16.11.13 Time: 20:57 To change
 * this template use File | Settings | File Templates.
 */
//@Controller
@RequestMapping("/" + BaseRepositoryController.ENDPOINT + "/" + Client.TYPE_NAME)
@Api(description = BaseRepositoryController.CRUD_ADMIN_REPOSITORY, tags = Client.TYPE_NAME)
class AdminClientController extends BaseRepositoryController<Client> {

    /**
     * store a json input element in the repository
     *
     * @param id
     * @param dataIn
     * @param dataInParsed
     * @param request
     * @return
     */
   /*
    @RequestMapping(value = "/{elementId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response> saveElement(@PathVariable(
            "elementId") String elementId, @RequestBody Map<String, Object> dataIn, @RequestBody SimpleClient
            dataInParsed, HttpServletRequest request) throws Exception {
        ResponseHandler manager = new ResponseHandler(request);

        SortedMap<Integer, Integer> deliveryCosts = new TreeMap<Integer, Integer>();
        LinkedHashMap<Object, Object> map = (LinkedHashMap<Object, Object>) dataIn.get("deliveryCostCentsMap");
        Set s = map.entrySet();

        // Using iterator in SortedMap
        Iterator i = s.iterator();

        while (i.hasNext()) {
            Map.Entry m = (Map.Entry) i.next();
            Integer key = Integer.parseInt((String) m.getKey());
            Integer value = (Integer) m.getValue();
            deliveryCosts.put(key, value);
        }

        dataInParsed.setDeliveryCostCentsMap(deliveryCosts);
        dataInParsed.setKeepDeliveryCostInfo((Boolean) dataIn.get("keepDeliveryCostInfo"));
        service.save(dataInParsed);

        return manager.getResponseEntity();


    }
    */
}
