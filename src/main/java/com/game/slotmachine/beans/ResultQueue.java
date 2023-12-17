package com.game.slotmachine.beans;

import com.game.slotmachine.entities.Game;
import com.game.slotmachine.model.payload.QueuePayload;
import com.game.slotmachine.model.projections.GameSlot1AndSlot2;
import com.game.slotmachine.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

@Component
public class ResultQueue {
    private Queue<QueuePayload> queue;

    public ResultQueue(){
        queue = new ArrayDeque<>();
    }

    public void initializeQueue(List<GameSlot1AndSlot2> top5GameList){
        //List<GameSlot1AndSlot2> top5GameList = gameRepository.fetchTop5GameOverRow();
        top5GameList.forEach((i)->queue.add(new QueuePayload(i.getSlot1(), i.getSlot2(),i.getGameTimeStamp())));

    }

    public Queue<QueuePayload> getQueue() {
        return queue;
    }

    public boolean push(QueuePayload queueElement){
        return queue.add(queueElement);
    }

    public QueuePayload pop(){
        return queue.poll();
    }

    @Override
    public String toString() {
        return "ResultQueue{" +
                "queue=" + queue.toString() +
                '}';
    }
}
