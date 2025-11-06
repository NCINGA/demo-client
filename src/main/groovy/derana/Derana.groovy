package derana

import com.ncinga.HTTPClient
import com.ncinga.Method
import com.ncinga.ServiceExecutor

class Derana implements ServiceExecutor{

    Map fetchNewsData() {
        Map<String, Object> headers = Map.of(
            "User-Agent", "Mozilla/5.0",
            "Accept", "application/json"
        )

        HTTPClient client = new HTTPClient.Builder()
                .url("https://saurav.tech/NewsAPI/top-headlines/category/general/us.json")
                .method(Method.GET)
                .headers(headers)
                .mediaType("application/json")
                .build()

        def newsResponse = client.exchange() as Map
        return [news: newsResponse]
    }

    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String, Object> args = list.collectEntries { [(it.name): it.value] }

        def response = fetchNewsData()

        def limitedNews = response.news.articles?.take(3)?.collect { article ->
            [
                title: article.title,
                description: article.description,
                url: article.url,
                source: article.source?.name,
                publishedAt: article.publishedAt
            ]
        } ?: []

        return limitedNews
    }
}
