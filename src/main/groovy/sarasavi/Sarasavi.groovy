package sarasavi

import com.ncinga.HTTPClient
import com.ncinga.Method
import com.ncinga.ServiceExecutor

class Sarasavi implements ServiceExecutor {

    Map fetchBooks(String query, String fields) {

        String searchQuery = query ?: "harry potter"
        String searchFields = fields ?: "title,author_name,isbn"

        String url = "https://openlibrary.org/search.json?q=${URLEncoder.encode(searchQuery, 'UTF-8')}"
        url += "&fields=${searchFields}"
        url += "&limit=5"

        HTTPClient client = new HTTPClient.Builder()
                .url(url)
                .method(Method.GET)
                .mediaType("application/json")
                .build()

        def booksResponse = client.exchange() as Map
        return [books: booksResponse]
    }

    @Override
    Object execute(List<Map<String, Object>> list) {
        Map<String, Object> args = list.collectEntries { [(it.name): it.value] }

        String query = args.containsKey("query") ? args.query as String : null
        String fields = args.containsKey("fields") ? args.fields as String : null

        def response = fetchBooks(query, fields)

        def books = response.books.docs?.collect { book ->
            [
                title: book.title,
                authors: book.author_name,
                isbn: book.isbn ? book.isbn[0] : null
            ]
        } ?: []

        return books
    }
}

