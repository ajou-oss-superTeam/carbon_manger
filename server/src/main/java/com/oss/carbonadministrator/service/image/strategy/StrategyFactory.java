package com.oss.carbonadministrator.service.image.strategy;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class StrategyFactory {

    private final Map<BillType, BillStrategy> strategyMap = new HashMap<>();


    public StrategyFactory() {
        strategyMap.put(BillType.ELECTRICITY, new Electricity());
        strategyMap.put(BillType.WATER, new Water());
        strategyMap.put(BillType.GAS, new Gas());
    }

    public BillStrategy findBillStrategy(BillType billType) {
        return strategyMap.get(billType);
    }

}
