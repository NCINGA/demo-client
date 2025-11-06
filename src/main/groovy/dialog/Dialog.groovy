package dialog

import com.ncinga.HTTPClient
import com.ncinga.ServiceExecutor

import java.security.SecureRandom

class Dialog implements ServiceExecutor {
    List<Map> fetchUsers(Map headers) {
        HTTPClient client = new HTTPClient.Builder()
                .url("https://jsonplaceholder.typicode.com/users")
                .method(com.ncinga.Method.GET)
                .mediaType("application/json")
                .headers(headers)
                .build()
        def users = client.exchange()
        return users
    }

    String fetchAuthKey() {
        byte[] bytes = new byte[32]
        new SecureRandom().nextBytes(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String,Object> args = list.collectEntries { [(it.name): it.value] }
        if (!args.containsKey("contact") || args.contact == null || args.contact == "") {
            throw new RuntimeException("Contact is null or missing")
        }
        def token = fetchAuthKey();
        Map headers = ["x-api-key":"12","Authorization":"Bearer $token"]
        def users = fetchUsers(headers)

        def matchedUsers = users.findAll { user ->
            user.phone == args.contact
        }
        matchedUsers.add("token": token)
        return matchedUsers
    }
}
