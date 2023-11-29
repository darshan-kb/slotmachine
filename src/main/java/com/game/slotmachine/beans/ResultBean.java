package com.game.slotmachine.beans;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Setter
@Getter
@ToString
public class ResultBean {
    private int slot1;
    private int slot2;
    private double totalGameAmount;
    private double totalAmountDisbursedToUsers;
    private List<Integer> slot1List;
    private List<Integer> slot2List;
    public ResultBean(){
        slot1=0;
        slot2=0;
        totalAmountDisbursedToUsers=0;
        totalGameAmount=0;
        slot1List = getShuffledAllElementsList(12);
        slot2List = getShuffledAllElementsList(3);
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
