package keels

import com.ncinga.HTTPClient
import com.ncinga.Method
import com.ncinga.ServiceExecutor

class Keels implements ServiceExecutor {
    Map fetchCoffeeList() {
        HTTPClient client = new HTTPClient.Builder()
                .url("https://api.sampleapis.com/coffee/hot")
                .method(Method.GET)
                .mediaType("application/json")
                .build()

        def coffeeResponse = client.exchange() as List
        return [coffees: coffeeResponse]
    }

    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String, Object> args = list.collectEntries { [(it.name): it.value] }

        def response = fetchCoffeeList()

        return response.coffees
    }
}
