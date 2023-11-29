package com.game.slotmachine.controller;


import com.game.slotmachine.service.sseService.SseCountdownService;
import com.game.slotmachine.service.sseService.SseQueueService;
import com.game.slotmachine.service.sseService.SseResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SseEventController {
    @Autowired
    SseCountdownService sseCountdownService;
    @Autowired
    SseResultService sseResultService;
    @Autowired
    SseQueueService sseQueueService;

    @GetMapping(path = "/countdown", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter countEventStream(){
        SseEmitter emitter = new SseEmitter();
        sseCountdownService.addEmitter(emitter);
        return emitter;
    }

    @GetMapping(path = "/result", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter resultEventStream(){
        SseEmitter emitter = new SseEmitter();
        sseResultService.addEmitter(emitter);
        return emitter;
    }

    @GetMapping(path = "/queue", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter queueEventStream(){
        SseEmitter emitter = new SseEmitter();
        sseQueueService.addEmitter(emitter);
        return emitter;
    }
}
