package com.game.slotmachine.service.sseService;

import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.model.payload.ResultPayload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseResultService {
    private final List<SseEmitter> resultEmitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter){
        resultEmitters.add(emitter);
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
}
