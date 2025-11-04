package tata

import com.ncinga.ServiceExecutor

class Tata implements ServiceExecutor {
    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String, Object> args = list.collectEntries { [(it.name): it.value] }
        if (!args.containsKey("nic") || args.nic == null || args.nic == "") {
            throw new RuntimeException("NIC is null or missing")
        } else {

            return args.containsKey("nic")
        }
    }
}
