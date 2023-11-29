package com.game.slotmachine.service.sseService;

import com.game.slotmachine.beans.Countdown;
import com.game.slotmachine.beans.ResultBean;
import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.model.payload.ResultPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseResultService {
    private final List<SseEmitter> resultEmitters = new CopyOnWriteArrayList<>();
    @Value("${resultRequestTimeout}")
    private int resultRequestTimeout;
    @Autowired
    private Countdown countdown;
    @Autowired
    private ResultBean resultBean;


    public void addEmitter(SseEmitter emitter){
        resultEmitters.add(emitter);
        int currentCount = countdown.getCountdown();
        if(currentCount<=0 && currentCount>=resultRequestTimeout){
            sentResultToLateConnection(resultBean, emitter);
        }
        emitter.onCompletion(() -> resultEmitters.remove(emitter));
        emitter.onTimeout(() -> resultEmitters.remove(emitter));
    }

    public void sendResult(ResultDTO resultDTO){
        for(SseEmitter emitter : resultEmitters){
            try{
                emitter.send(new ResultPayload(resultDTO.slot1(), resultDTO.slot2()));
            }
            catch (IOException e){
                e.printStackTrace();
                emitter.complete();
                resultEmitters.remove(emitter);
            }
        }
    }

    public void sentResultToLateConnection(ResultBean resultBean, SseEmitter sseEmitter){
        try{
            System.out.println(resultBean.toString());
            sseEmitter.send(new ResultPayload(resultBean.getSlot1(), resultBean.getSlot2()));
        }
        catch (IOException e){
            e.printStackTrace();
            sseEmitter.complete();
            resultEmitters.remove(sseEmitter);
        }
    }
}
