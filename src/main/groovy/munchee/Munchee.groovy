package munchee

import com.ncinga.ServiceExecutor

class Munchee implements ServiceExecutor{

    Map fetchList() {
        return ["name": "munchee", "type": "food-city","initialAmount":1000.0,"newAmount":1000.0]
    }
    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String,Object> args = list.collectEntries { [(it.name): it.value] }
        if (!args.containsKey("amount") || args.amount == null || args.amount == "") {
            throw new RuntimeException("Amount is null or missing")
        }

        def listData = fetchList()
        if(listData.containsKey("newAmount")){
            listData.newAmount = listData.initialAmount + args.amount
        }

        return listData
    }
}
