package cargills

import com.ncinga.ServiceExecutor
import com.ncinga.HTTPClient
import com.ncinga.Method

class Cargills implements ServiceExecutor {

    Map fetch() {
        HTTPClient client = new HTTPClient.Builder()
                .url("https://reqres.in/api/users?page=2")
                .method(Method.GET)
                .mediaType("application/json")
                .build()

        def response = client.exchange() as Map
        println("Request Response: " + response)
        return response
    }

    @Override
    Object execute(List<Map<String, Object>> list) {
        def data = fetch()
        return data.data.first_name
    }
}