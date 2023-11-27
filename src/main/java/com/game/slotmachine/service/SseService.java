package com.game.slotmachine.service;

import com.game.slotmachine.model.payload.SsePayload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    public void sendEvents(int varcount){
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(new SsePayload("count",varcount));
            }
            catch (IOException e){
                e.printStackTrace();
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }

    public void sendResult(int result){
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(new SsePayload("result",result));
            }
            catch (IOException e){
                e.printStackTrace();
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }


}
