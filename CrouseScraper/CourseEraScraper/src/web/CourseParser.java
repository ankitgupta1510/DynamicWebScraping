package web;

import org.json.JSONArray;
import org.json.JSONObject;

public class CourseParser {

    public int parseAndSaveData(String jsonString, csvExporter saver) {
        try {
            JSONArray jsonArr = new JSONArray(jsonString);
            JSONObject firstObject = jsonArr.getJSONObject(0);
            JSONArray searchArray = firstObject.getJSONObject("data").getJSONObject("SearchResult").getJSONArray("search");
            JSONObject firstSearchResult = searchArray.getJSONObject(0);
            JSONArray elements = firstSearchResult.getJSONArray("elements");

            if (elements.length() == 0) {
                return 0;
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

                // Use the DataSaver to write the row
                saver.writeData(courseName, partnerName, difficulty);
            }
            return elements.length();

        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
            return 0;
        }
    }
}