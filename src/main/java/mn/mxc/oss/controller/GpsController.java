package mn.mxc.oss.controller;

import mn.mxc.oss.domain.Gps;
import mn.mxc.oss.services.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by i7 on 12/09/2016.
 */
@RestController
public class GpsController {
    @Autowired
    GpsService service;

    @RequestMapping(value = "gps/save", method = RequestMethod.POST, consumes="application/json", produces= MediaType.APPLICATION_JSON_VALUE)
    public Gps save(@RequestBody Gps entity) {
        service.save(entity);
        return entity;
    }

    @RequestMapping(value = "gps/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Hashtable findAll(@RequestParam int page, @RequestParam int size) {
        List list = service.findAll(page, size);
        Hashtable pageable = new Hashtable();
        pageable.put("total", service.total());
        pageable.put("data", list);
        return pageable;
    }
    @RequestMapping(value = "gps/delete", method = RequestMethod.DELETE)
    public Gps delete(@RequestParam int id) {
        Gps item = service.findOne(id);
        service.delete(item);
        return item;
    }

    @RequestMapping(value = "gps/update", method = RequestMethod.PUT, consumes="application/json", produces = "application/json; charset=utf-8", headers = "Accept=*/*")
    public Gps update(@RequestBody Gps entity) {
        service.update(entity);
        return entity;
    }
}
