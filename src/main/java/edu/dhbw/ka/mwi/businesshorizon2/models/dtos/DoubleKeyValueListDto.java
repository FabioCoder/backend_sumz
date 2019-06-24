/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.dhbw.ka.mwi.businesshorizon2.models.dtos;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DHBW WWI KA
 */
public class DoubleKeyValueListDto extends ArrayList<SimpleEntry<Double, Double>> {

    public List<Double> getKeyList() {
        List<Double> keyList = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            keyList.add(this.get(i).getKey());
        }
        return keyList;
    }

    public List<Double> getValueList() {
        List<Double> valueList = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            valueList.add(this.get(i).getValue());
        }
        return valueList;
    }

}
