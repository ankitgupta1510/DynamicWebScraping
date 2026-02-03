package com.scrape.dmart;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class BrowserSearch {

    private static final String INPUT_FILE = "/home/ankit/eclipse-workspace/WebCrawler/Dmart_Unique_Brands.xlsx";

    private static final String OUTPUT_FILE = "/home/ankit/eclipse-workspace/WebCrawler/Final_Brand_Data.xlsx";

    public static void main(String[] args) {
        processBrands();
    }

    private static void processBrands() {

        try (FileInputStream fis = new FileInputStream(INPUT_FILE); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Header
            Row header = sheet.getRow(0);
            if (header == null)
                header = sheet.createRow(0);

            if (header.getCell(1) == null)
                header.createCell(1).setCellValue("Website URL");

            if (header.getCell(2) == null)
                header.createCell(2).setCellValue("Logo URL");

            Iterator<Row> iterator = sheet.iterator();
            iterator.next(); // skip header

            int count = 1;

            while (iterator.hasNext()) {
                Row row = iterator.next();
                Cell brandCell = row.getCell(0);

                if (brandCell == null)
                    continue;

                String brand = brandCell.getStringCellValue().trim();
                if (brand.length() < 3)
                    continue;

                System.out.println("[" + count++ + "] Searching: " + brand);

                String website = fetchOfficialWebsite(brand);
                String logo = "N/A";

                if (!website.equals("N/A")) {
                    logo = fetchLogoFromWebsite(website);

                    // fallback
                    if (logo.equals("N/A")) {
                        logo = "";
                    }
                }

                row.createCell(1).setCellValue(website);
                row.createCell(2).setCellValue(logo);

                System.out.println("    Website: " + website);
                System.out.println("    Logo   : " + logo);

                Thread.sleep(2000); // rate limit
            }

            try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE)) {
                workbook.write(fos);
            }

            System.out.println("DONE Saved at: " + OUTPUT_FILE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fetchOfficialWebsite(String brand) {
        try {
            String query = brand + "brand official website";
            String url = "https://duckduckgo.com/html/?q=" + URLEncoder.encode(query, StandardCharsets.UTF_8);

            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(15000).get();

            Element result = doc.selectFirst("a.result__a");
            if (result == null)
                return "N/A";

            String href = result.attr("href");

            // Decode DuckDuckGo redirect
            if (href.contains("uddg=")) {
                String encoded = href.substring(href.indexOf("uddg=") + 5).split("&")[0];
                return URLDecoder.decode(encoded, "UTF-8");
            }

        } catch (Exception e) {
            System.err.println("Search error: " + e.getMessage());
        }
        return "N/A";
    }

    private static String fetchLogoFromWebsite(String websiteUrl) {
        try {
            Document doc = Jsoup.connect(websiteUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .header("Accept-Language", "en-US,en;q=0.9").header("Referer", "https://www.google.com/")
                    .timeout(20000).get();

            // logo:image
            Element logo = doc.selectFirst("meta[property=og:image]");
            if (logo != null) {
                return normalizeUrl(websiteUrl, logo.attr("content"));
            }

            // Favicon
            Element icon = doc.selectFirst("link[rel~=icon]");
            if (icon != null)
                return normalizeUrl(websiteUrl, icon.attr("href"));

        } catch (Exception e) {
            System.err.println("Logo error: " + e.getMessage());
        }
        return "N/A";
    }

    private static String normalizeUrl(String baseUrl, String url) {

        if (url == null || url.isEmpty())
            return "N/A";

        if (url.startsWith("http://") || url.startsWith("https://"))
            return url;

        if (url.startsWith("//"))
            return "https:" + url;

        if (url.startsWith("/"))
            return baseUrl.replaceAll("/$", "") + url;

        return baseUrl.replaceAll("/$", "") + "/" + url;
    }
}


