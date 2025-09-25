package flow;

import org.json.JSONArray;
import org.json.JSONObject;

public class CourseDetailsParser {

    /**
     * JSON string ko parse karke data ko DataSaver ke zariye save karta hai.
     * @return Jitne courses mile unka count.
     */
    public int parseAndSaveData(String jsonString, DataSaver saver, String subject) {
        try {
            JSONArray rootArray = new JSONArray(jsonString);
            JSONObject firstObject = rootArray.getJSONObject(0);
            JSONArray searchArray = firstObject.getJSONObject("data").getJSONObject("SearchResult").getJSONArray("search");
            JSONObject firstSearchResult = searchArray.getJSONObject(0);
            JSONArray elements = firstSearchResult.getJSONArray("elements");

            if (elements.length() == 0) {
                return 0; // Agar koi course na mile toh 0 return karo.
            }

            for (int j = 0; j < elements.length(); j++) {
                JSONObject course = elements.getJSONObject(j);
                
                String courseName = course.optString("name", "N/A");
                String difficulty = course.optString("productDifficultyLevel", "N/A");

                JSONArray partners = course.optJSONArray("partners");
                String partnerName = "N/A";
                if (partners != null && partners.length() > 0) {
                    partnerName = partners.getString(0);
                }

                // DataSaver ko data de do save karne ke liye.
                saver.writeData(subject, courseName, partnerName, difficulty);
            }
            return elements.length(); // Jitne course mile, unka count return karo.

        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return 0; // Error aane par bhi 0 return karo.
        }
    }
}