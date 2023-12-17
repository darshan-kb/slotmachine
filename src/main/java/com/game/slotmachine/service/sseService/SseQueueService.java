package com.game.slotmachine.service.sseService;

import com.game.slotmachine.beans.ResultQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SseQueueService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    @Autowired
    private ResultQueue queue;

    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);
        sendQueueOnConnect(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
    }

    public void sendQueueOnConnect(SseEmitter emitter){
        try{
            emitter.send(queue.getQueue());
        }
        catch (IOException e){
            e.printStackTrace();
            emitter.complete();
            emitters.remove(emitter);
        }
    }

    public void sendQueue(){
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(queue.getQueue());
            }
            catch (IOException e){
                e.printStackTrace();
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }
}
