/**
 *  Copyright 2022 Carbon_Developers
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
