package com.game.slotmachine.service.sseService;

import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.model.payload.ResultPayload;
import com.game.slotmachine.model.payload.SseCountdownPayload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseCountdownService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    public void sendEvents(int varcount){
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(new SseCountdownPayload(varcount));
            }
            catch (IOException e){
                e.printStackTrace();
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }
}
