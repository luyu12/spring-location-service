package demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



/*
 1.runnign-location-distribution service
 2.REST API receive request from running-location-simulator
 */
@RestController
@EnableBinding(Source.class)
//要发出的消息必须通过chanel发送出去
public class RunnerPositionSource {

    @Autowired
    private MessageChannel output;

    @RequestMapping(value = "/api/locations", method = RequestMethod.POST)
    public void locations(@RequestBody String positionInfo) {
        this.output.send(MessageBuilder.withPayload(positionInfo).build());
    }
}
