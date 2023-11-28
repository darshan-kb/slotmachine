package com.game.slotmachine.service;

import com.game.slotmachine.beans.CachedTotalBetsAmountMap;
import com.game.slotmachine.model.dto.ResultDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

            return new ResultDTO(slot1List, slot2List);
        }
        int[] winnerBet = validBetPairs.get(ThreadLocalRandom.current().nextInt(0, validBetPairs.size()));

        List<Integer> slot1List = getShuffledList(12,winnerBet[0]);
        List<Integer> slot2List = getShuffledList(3,winnerBet[1]);

        return new ResultDTO(slot1List, slot2List);
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
}