Background
Many people get their news from online news aggregators and search engines. Those sites can return very different results, as shown in the above pictures. Compare the homepages of Google News and Yahoo News on 2022 March 31 at between midnight and 1am. The discrepancies in the results stem from the following:

News outlets: News aggregator sites retrieve their content from news outlet websites, which often use different content sources.
Algorithms: News aggregators can use different algorithms to rank news articles for display. News aggregators can also automatically collect user data (such as location) to customize what content is presented to users.
Personalization: User personalization allows users to modify the content displayed on their personal homepages.
In this repo, I write a program that will reproduce the functionality of a news aggregator. A news aggregator is a type of search engine, and its implementation follows the same steps.

The first component of a web search engine is the crawler. This is a program that downloads web page content that we wish to search for.
The second component is the indexer, which will take these downloaded pages and create an inverted index.
The third component is retrieval, which answers a user’s query by talking to the user’s browser. The browser will show the search results and allow the user to interact with the web.
My news aggregator will also have a default page and will provide autocomplete capabilities.
