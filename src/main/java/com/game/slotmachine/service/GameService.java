package com.game.slotmachine.service;

import com.game.slotmachine.beans.CachedTotalBetsAmountMap;
import com.game.slotmachine.beans.ResultBean;
import com.game.slotmachine.beans.ResultQueue;
import com.game.slotmachine.entities.Game;
import com.game.slotmachine.model.dto.ResultDTO;
import com.game.slotmachine.model.mapper.Mapper;
import com.game.slotmachine.model.projections.GameSlot1AndSlot2;
import com.game.slotmachine.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class GameService {
    private CachedTotalBetsAmountMap betsAmountMap;
    private ResultBean resultBean;
    private Mapper mapper;
    private GameRepository gameRepository;
    private ResultQueue resultQueue;

    public ResultDTO calculateGameResult(){
        List<Double> doubleBetAmountList  = betsAmountMap.getBetsMap().values().stream().map(i->i*20.0).collect(Collectors.toList());
        List<Double> tripleBetAmountList  = betsAmountMap.getBetsMap().values().stream().map(i->i*30.0).collect(Collectors.toList());
        List<Double> singleBetAmountList  = betsAmountMap.getBetsMap().values().stream().map(i->i*10.0).collect(Collectors.toList());

        double totalBetAmount = totalGameAmount();

        List<int[]> validBetPairs = new ArrayList<>();

        calculateValidBets(singleBetAmountList,validBetPairs,10,totalBetAmount);
        calculateValidBets(doubleBetAmountList,validBetPairs,20,totalBetAmount);
        calculateValidBets(tripleBetAmountList,validBetPairs,30,totalBetAmount);
        if(validBetPairs.isEmpty()){
            List<Integer> slot1List = getShuffledAllElementsList(12);
            List<Integer> slot2List = getShuffledAllElementsList(3);
            updateResultBean(slot1List,slot2List,new int[]{slot1List.get(11), slot2List.get(2)});
            //updateQueue(new int[]{slot1List.get(11), slot2List.get(2)});
            return new ResultDTO(slot1List, slot2List);
        }
        int[] winnerBet = validBetPairs.get(ThreadLocalRandom.current().nextInt(0, validBetPairs.size()));

        List<Integer> slot1List = getShuffledList(12,winnerBet[0]);
        List<Integer> slot2List = getShuffledList(3,winnerBet[1]);
        updateResultBean(slot1List,slot2List,winnerBet);
        //updateQueue(winnerBet);
        System.out.println(resultBean);
        return mapper.ResultBeanToResultDTO(resultBean);
    }

    public double totalGameAmount(){
        return betsAmountMap.getBetsMap().values().stream().reduce(0.0,(a,b)->a+b);
    }

    public void calculateValidBets(List<Double> betList, List<int[]> resultList, int multiple,double totalBetAmount){
        for(int i=0;i<betList.size();i++){
            if(totalBetAmount>betList.get(i)){
                resultList.add(new int[]{i+1, 1});
            }
        }
    }

    public List<Integer> getShuffledList(int capacity, int avoidInteger){
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<capacity;i++){
            if(avoidInteger != i+1)
                list.add(i+1);
        }
        Collections.shuffle(list);
        list.add(avoidInteger);
        return list;
    }

    public List<Integer> getShuffledAllElementsList(int capacity){
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<capacity;i++){
                list.add(i+1);
        }
        Collections.shuffle(list);
        return list;
    }
    @Transactional
    public void markGameAsOver(Game game){
        game.setGameOver(true);
        System.out.println(resultBean);
        game.setTotalAmount(resultBean.getTotalGameAmount());
        game.setSlot1(resultBean.getSlot1());
        game.setSlot2(resultBean.getSlot2());
        game.setTotalRewardDisbursed(resultBean.getTotalAmountDisbursedToUsers());
        gameRepository.save(game);
    }

    public void updateResultBean(List<Integer> slot1List, List<Integer> slot2List, int[] winnerBet){
        resultBean.setSlot1(winnerBet[0]);
        resultBean.setSlot2(winnerBet[1]);
        resultBean.setSlot1List(slot1List);
        resultBean.setSlot2List(slot2List);
        resultBean.setTotalGameAmount(totalGameAmount());
        resultBean.setTotalAmountDisbursedToUsers(betsAmountMap.getTotalAmountByBetNumber(winnerBet[0])*winnerBet[1]*10);
    }

    public void updateQueue(int[] queueElement){
        System.out.println(resultQueue.toString());
        resultQueue.pop();
        resultQueue.push(queueElement);
    }

    public void updateQueue(){
        System.out.println(resultQueue.toString());
        resultQueue.pop();
        resultQueue.push(new int[]{resultBean.getSlot1(),resultBean.getSlot2()});
    }

    public GameSlot1AndSlot2 getLastFinisedGame(){
        return gameRepository.fetchLastFinisedGame().orElseThrow();
    }
}
