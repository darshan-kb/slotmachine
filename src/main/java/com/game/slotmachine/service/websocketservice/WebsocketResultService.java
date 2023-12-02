package com.game.slotmachine.service.websocketservice;

import com.game.slotmachine.beans.ResultBean;
import com.game.slotmachine.model.payload.ResultPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketResultService {
    @Autowired
    ResultBean resultBean;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    public void sendResult(){
        simpMessagingTemplate.convertAndSend("/result/data",new ResultPayload(resultBean.getSlot1List(),resultBean.getSlot2List(),true));
    }
}
