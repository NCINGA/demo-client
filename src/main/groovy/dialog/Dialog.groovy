package dialog

import com.ncinga.HTTPClient

class Dialog implements com.ncinga.ServiceExecutor {

    List<Map> fetchUsers() {
        HTTPClient client = new HTTPClient.Builder()
                .url("https://jsonplaceholder.typicode.com/users")
                .method(com.ncinga.Method.GET)
                .mediaType("application/json")
                .build()
        def users = client.exchange()
        return users
    }

    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String,Object> args = list.collectEntries { [(it.name): it.value] }
        if (!args.containsKey("contact") || args.contact == null || args.contact == "") {
            throw new RuntimeException("Contact is null or missing")
        }
        def users = fetchUsers()

        def matchedUsers = users.findAll { user ->
            user.phone == args.contact
        }
        return matchedUsers
    }
}
