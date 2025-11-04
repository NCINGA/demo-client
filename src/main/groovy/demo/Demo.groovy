package com.ncinga.demo

import com.ncinga.HTTPClient
import com.ncinga.Method

class Demo implements com.ncinga.ServiceExecutor{

    Map fetchAuthKey() {
        HTTPClient client = new HTTPClient.Builder()
                .url("https://06f19328-1e01-482f-84b4-9d32c4458463.mock.pstmn.io/generate-token")
                .method(Method.POST)
                .mediaType("application/json")
                .build()
        def authResponse = client.exchange() as Map;
        return authResponse;

    }


    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String, Object> args = list.collectEntries { [(it.name): it.value] }
        if (!args.containsKey("nic") || args.nic == null || args.nic == "") {
            throw new RuntimeException("NIC is null or missing")
        }

        def authKey = fetchAuthKey();
        Map<String, Object> header = Map.of("x-api-key", "33bfa67f3dd0ad1b7cfca45044fb33ae", "Authorization", "Bearer $authKey")
        HTTPClient client = new HTTPClient.Builder()
                .headers(header)
                .url("https://06f19328-1e01-482f-84b4-9d32c4458463.mock.pstmn.io/generate-token")
                .method(Method.POST)
                .mediaType("application/json")
                .build()
        def authResponse = client.exchange() as Map;
        return authResponse
    }
}
