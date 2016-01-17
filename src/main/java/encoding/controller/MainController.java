package encoding.controller;

import encoding.service.EncodingBase29;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    //private final static org.slf4j.Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    EncodingBase29 encodingBase29;

    @RequestMapping(value = "/encodeValue", method = {RequestMethod.POST})
    @ResponseBody
    public String encodeValue(@RequestBody String encodeValue) {
        String[] split = encodeValue.split("=");
        return encodingBase29.encode(split[1]);
    }

    @RequestMapping(value = "/decodeValue", method = {RequestMethod.POST})
    @ResponseBody
    public String decodeValue(@RequestBody String decodeValue) {
        String[] split = decodeValue.split("=");
        return encodingBase29.decode(split[1]);
    }

}