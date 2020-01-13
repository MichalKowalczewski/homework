package com.homework.controller;

import com.homework.model.Customer;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
@Api(description = "Api related to volume.json", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    //@Autowired
    //JsonParser jsonParser;

    @PostMapping("create")
    public String postTest(@RequestBody Customer test){
        //jsonParser.processNewJson(test);
        return  "Test value : "+ test.getName() +" with address: "+ test.getAddress() + " and id: "+ test.getId() +" has been saved";
    }

    @PutMapping("modify/{name}")
    public String putTest(@PathVariable("name") String name, @RequestBody Customer test){
       // jsonParser.modifyJson(test, name);
        return "File :" +name+"file.json has been modified";
    }
}
