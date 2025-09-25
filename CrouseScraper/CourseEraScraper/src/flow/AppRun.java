package flow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;


public class AppRun {

    private static final String URL = "https://www.coursera.org/graphql-gateway?opname=Search";
    
    // IMPORTANT: Niche wali string mein ... ki jagah browser se copy kiya hua poora payload daalein.
    // Maine readability ke liye isse chhota rakha hai.
    private static final String PAYLOAD_TEMPLATE = "[{\"operationName\":\"Search\",\"variables\":{\"requests\":[{\"entityType\":\"PRODUCTS\",\"limit\":12,\"facets\":[\"topic\"],\"facetFilters\":[%s],\"cursor\":\"%d\",\"query\":\"\"},{\"entityType\":\"SUGGESTIONS\",\"limit\":1,\"facetFilters\":[],\"cursor\":\"0\",\"query\":\"\"}]},\"query\":\"query Search($requests: [Search_Request!]!) { SearchResult { search(requests: $requests) { ...SearchResult __typename } __typename } } fragment SearchResult on Search_Result { elements { ...SearchHit __typename } facets { ...SearchFacets __typename } pagination { cursor totalElements __typename } totalPages source { indexName recommender { context hash __typename } __typename } __typename } fragment SearchHit on Search_Hit { ...SearchArticleHit ...SearchProductHit ...SearchSuggestionHit __typename } fragment SearchArticleHit on Search_ArticleHit { aeName careerField category createdByName firstPublishedAt id internalContentEpic internalProductLine internalTargetKw introduction islocalized lastPublishedAt localizedCountryCd localizedLanguageCd name subcategory topics url skill: skills __typename } fragment SearchProductHit on Search_ProductHit { avgProductRating cobrandingEnabled completions duration id imageUrl isCourseFree isCreditEligible isNewContent isPartOfCourseraPlus name numProductRatings parentCourseName parentLessonName partnerLogos partners productCard { ...SearchProductCard __typename } productDifficultyLevel productDuration productType skills url videosInLesson translatedName translatedSkills translatedParentCourseName translatedParentLessonName tagline fullyTranslatedLanguages subtitlesOnlyLanguages __typename } fragment SearchSuggestionHit on Search_SuggestionHit { id name score __typename } fragment SearchProductCard on ProductCard_ProductCard { id canonicalType marketingProductType badges productTypeAttributes { ... on ProductCard_Specialization { ...SearchProductCardSpecialization __typename } ... on ProductCard_Course { ...SearchProductCardCourse __typename } ... on ProductCard_Clip { ...SearchProductCardClip __typename } ... on ProductCard_Degree { ...SearchProductCardDegree __typename } __typename } __typename } fragment SearchProductCardSpecialization on ProductCard_Specialization { isPathwayContent __typename } fragment SearchProductCardCourse on ProductCard_Course { isPathwayContent rating reviewCount __typename } fragment SearchProductCardClip on ProductCard_Clip { canonical { id __typename } __typename } fragment SearchProductCardDegree on ProductCard_Degree { canonical { id __typename } __typename } fragment SearchFacets on Search_Facet { name nameDisplay valuesAndCounts { ...ValuesAndCounts __typename } __typename } fragment ValuesAndCounts on Search_FacetValueAndCount { count value valueDisplay __typename }\"}]";

    public static void main(String[] args) throws IOException, InterruptedException {
        
        System.out.println("Phase 1: Fetching all available subjects...");
        List<String> subjects = getSubjects();
        System.out.println("Found " + subjects.size() + " subjects to scrape: " + subjects);

        System.out.println("\nPhase 2: Starting to scrape courses for each subject...");
        CourseDetailsParser parser = new CourseDetailsParser();
        DataSaver saver = new DataSaver();
        saver.openFile("coursera_courses_fully_automated.csv");

        for (String subject : subjects) {
            System.out.println("\n========================================");
            System.out.println("SCRAPING SUBJECT: " + subject);
            System.out.println("========================================");
            
            int cursor = 2;
            while (true) {
                String facetFilter = String.format("\"topic~%s\"", subject);
                String finalPayload = String.format(PAYLOAD_TEMPLATE, facetFilter, cursor);
                
                System.out.println("Fetching page with cursor: " + cursor);

                Response res = Jsoup.connect(URL)
                    .ignoreContentType(true)
                    .method(Method.POST)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
                    .header("Content-Type", "application/json")
                    .requestBody(finalPayload)
                    .timeout(60000)
                    .execute();
                
                int coursesFound = parser.parseAndSaveData(res.body(), saver, subject);

                if (coursesFound == 0) {
                    System.out.println("No more courses found for " + subject);
                    break;
                }
                
                cursor++;
                Thread.sleep(1000);
            }
        }

        saver.closeFile();
        System.out.println("\nScraping complete! Data saved to coursera_courses_fully_automated.csv");
    }

    private static List<String> getSubjects() throws IOException {
        List<String> subjectList = new ArrayList<>();
        String initialPayload = String.format(PAYLOAD_TEMPLATE, "", 1);

        Response res = Jsoup.connect(URL)
            .ignoreContentType(true)
            .method(Method.POST)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36")
            .header("Content-Type", "application/json")
            .requestBody(initialPayload)
            .timeout(60000)
            .execute();

        try {
            JSONArray rootArray = new JSONArray(res.body());
            JSONObject firstObject = rootArray.getJSONObject(0);
            JSONArray searchArray = firstObject.getJSONObject("data").getJSONObject("SearchResult").getJSONArray("search");
            JSONObject firstSearchResult = searchArray.getJSONObject(0);
            JSONArray facets = firstSearchResult.getJSONArray("facets");

            for (int i = 0; i < facets.length(); i++) {
                JSONObject facet = facets.getJSONObject(i);
                if ("topic".equals(facet.optString("name"))) {
                    JSONArray valuesAndCounts = facet.getJSONArray("valuesAndCounts");
                    for (int j = 0; j < valuesAndCounts.length(); j++) {
                        JSONObject item = valuesAndCounts.getJSONObject(j);
                        subjectList.add(item.getString("value"));
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not parse subjects: " + e.getMessage());
        }
        
        return subjectList;
    }
}