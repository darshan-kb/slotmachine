package com.game.slotmachine.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CachedTotalBetsAmountMap {
    private ConcurrentHashMap<Integer,Double> betAmountMap;
    private static Logger logger = LoggerFactory.getLogger(CachedTotalBetsAmountMap.class);
    public CachedTotalBetsAmountMap(){
        betAmountMap = new ConcurrentHashMap<>();
        initializeValueMap();
    }

    public void initializeValueMap() {
        for(int i=0;i<12;i++){
            betAmountMap.put(i+1,0.0);
        }
    }

    public void addAmountToBet(int betNumber, double betAmount){
        betAmountMap.put(betNumber,betAmountMap.get(betNumber)+betAmount);
        logger.info(betAmountMap.toString());
    }

    public Map<Integer,Double> getBetsMap(){
        return betAmountMap;
    }

    public double getTotalAmountByBetNumber(int betNumber){
        logger.info("winning bet number {}",betNumber);
        return betAmountMap.get(betNumber);
    }

    public double getTotalAmountBetOnCurrentGame(){
        return betAmountMap.values().stream().reduce(0.0, (a,b)->a+b);
    }

    @Override
    public String toString() {

        return "CachedTotalBetsAmountMap{" +
                "betAmountMap=" + betAmountMap.values().toString() +
                '}';
    }
}
