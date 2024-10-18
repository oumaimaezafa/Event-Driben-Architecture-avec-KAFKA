package ma.oumaimaezafa.springcoudstreamskafka.web;


import ma.oumaimaezafa.springcoudstreamskafka.enteties.PageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@RestController
public class PageEventProducer {
    @Autowired
    private StreamBridge streamBridge;

    @GetMapping(path = "/publish/{topic}/{name}")
    public PageEvent publish(@PathVariable  String topic ,@PathVariable String name){
         PageEvent pageEvent=new PageEvent(name,Math.random()>0.5 ?"U1":"U2",new Date(),new Random().nextInt(9000));
          streamBridge.send(topic,pageEvent);
            return pageEvent;
    }


}
