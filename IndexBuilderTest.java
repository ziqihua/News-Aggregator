import static org.junit.Assert.*;

import java.util.*;
import java.util.Map.Entry;
import org.junit.Test;

/**
 * @author ericfouh
 */
public class IndexBuilderTest {
    // ToDo
    String page1 = "https://www.seas.upenn.edu/~cit5940/page1.html";
    String page2 = "https://www.seas.upenn.edu/~cit5940/page2.html";
    String page3 = "https://www.seas.upenn.edu/~cit5940/page3.html";
    String page4 = "https://www.seas.upenn.edu/~cit5940/page4.html";
    String page5 = "https://www.seas.upenn.edu/~cit5940/page5.html";

    @Test
    public void testParseFeed() {
        IndexBuilder indexBuilder = new IndexBuilder();
        Map<String, List<String>>wordsMap;
        String testString = "https://www.cis.upenn.edu/~cit5940/sample_rss_feed.xml";
        List<String> testFeed = new ArrayList<>();
        testFeed.add(testString);
        wordsMap = indexBuilder.parseFeed(testFeed);
        assertEquals(5, wordsMap.size());
        assertEquals(22, wordsMap.get(page4).size());
    }

    @Test
    public void testBuildIndex() {
        IndexBuilder indexBuilder = new IndexBuilder();
        Map<String, List<String>> result;
        String testString = "https://www.cis.upenn.edu/~cit5940/sample_rss_feed.xml";
        List<String> testFeed = new ArrayList<String>();
        testFeed.add(testString);
        result = indexBuilder.parseFeed(testFeed);
        Map<String, Map<String, Double>> index = indexBuilder.buildIndex(result);
        Map<String, Double> testing = index.get(page1);
        assertEquals(8, testing.size());
        assertEquals(0.1021, testing.get("data"),0.0001);
        assertEquals(0.0916, testing.get("lists"),0.0001);
    }

    @Test
    public void testBuildInvertedIndex() {
        IndexBuilder indexBuilder = new IndexBuilder();
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        String testString = "https://www.cis.upenn.edu/~cit5940/sample_rss_feed.xml";
        List<String> testFeed = new ArrayList<String>();
        testFeed.add(testString);
        result = indexBuilder.parseFeed(testFeed);
        Map<String, Map<String, Double>> index = indexBuilder.buildIndex(result);
        Map<?, ?> invertedIndex = indexBuilder.buildInvertedIndex(index);
        ArrayList<Entry<String, Double>> check =
                (ArrayList<Entry<String, Double>>) invertedIndex.get("linkedlist");
        assertEquals(2, check.size());
    }

    @Test
    public void testBuildHomePage() {
        IndexBuilder indexBuilder = new IndexBuilder();
        Map<String, List<String>> result;
        String testString = "https://www.cis.upenn.edu/~cit5940/sample_rss_feed.xml";
        List<String> testFeed = new ArrayList<String>();
        testFeed.add(testString);
        result = indexBuilder.parseFeed(testFeed);
        Map<String, Map<String, Double>> index = indexBuilder.buildIndex(result);
        Map<?, ?> invertedIndex = indexBuilder.buildInvertedIndex(index);
        ArrayList<Entry<String, Double>> check =
                (ArrayList<Entry<String, Double>>) invertedIndex.get("linkedlist");
        assertEquals(2, check.size());
        Collection<Entry<String, List<String>>> homePage
                = indexBuilder.buildHomePage(invertedIndex);
        List<Entry<String, List<String>>> homePageList =
                new ArrayList<>(homePage);
        assertEquals(57, homePageList.size());
        assertEquals("data", homePageList.get(0).getKey());
    }

    @Test
    public void testSearchArticles() {
        IndexBuilder indexBuilder = new IndexBuilder();
        Map<String, List<String>> result;
        String testString = "https://www.cis.upenn.edu/~cit5940/sample_rss_feed.xml";
        List<String> testFeed = new ArrayList<String>();
        testFeed.add(testString);
        result = indexBuilder.parseFeed(testFeed);
        Map<String, Map<String, Double>> index = indexBuilder.buildIndex(result);
        Map<?, ?> invertedIndex = indexBuilder.buildInvertedIndex(index);
        ArrayList<Entry<String, Double>> check =
                (ArrayList<Entry<String, Double>>) invertedIndex.get("linkedlist");
        assertEquals(2, check.size());
        Collection<Entry<String, List<String>>> homePage
                = indexBuilder.buildHomePage(invertedIndex);
        List<Entry<String, List<String>>> homePageList =
                new ArrayList<>(homePage);
        assertEquals(57, homePageList.size());
        assertEquals("data", homePageList.get(0).getKey());
        List<String> articles = indexBuilder.searchArticles("data", invertedIndex);
        assertEquals(3, articles.size());
        assertEquals(page1, articles.get(0));
        assertEquals(page2, articles.get(1));
    }

    @Test
    public void testCreateAutocompleteFile() {
        IndexBuilder indexBuilder = new IndexBuilder();
        Map<String, List<String>> result;
        String testString = "https://www.cis.upenn.edu/~cit5940/sample_rss_feed.xml";
        List<String> testFeed = new ArrayList<String>();
        testFeed.add(testString);
        result = indexBuilder.parseFeed(testFeed);
        Map<String, Map<String, Double>> index = indexBuilder.buildIndex(result);
        Map<?, ?> invertedIndex = indexBuilder.buildInvertedIndex(index);
        ArrayList<Entry<String, Double>> check =
                (ArrayList<Entry<String, Double>>) invertedIndex.get("linkedlist");
        assertEquals(2, check.size());
        Collection<Entry<String, List<String>>> homePage
                = indexBuilder.buildHomePage(invertedIndex);
        List<Entry<String, List<String>>> homePageList =
                new ArrayList<>(homePage);
        assertEquals(57, homePageList.size());
        assertEquals("data", homePageList.get(0).getKey());
        List<String> articles = indexBuilder.searchArticles("data", invertedIndex);
        assertEquals(3, articles.size());
        assertEquals(page1, articles.get(0));
        assertEquals(page2, articles.get(1));
        Collection<?> auto;
        auto = indexBuilder.createAutocompleteFile(homePageList);
        assertEquals(57, auto.size());
    }

}
