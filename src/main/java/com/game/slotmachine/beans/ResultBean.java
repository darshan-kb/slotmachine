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
    private List<Integer> slot1;
    private List<Integer> slot2;
    public ResultBean(){
        slot1 = getShuffledAllElementsList(12);
        slot2 = getShuffledAllElementsList(3);
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
