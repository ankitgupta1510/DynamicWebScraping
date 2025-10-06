package flow;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

        String url_new = "https://mapi.makemytrip.com/clientbackend/cg/search-hotels/DESKTOP/2?cityCode=CTGOI&language=eng&region=in&currency=INR&idContext=B2C&countryCode=IN&requestId=7ccff040-6afa-4441-84db-7d58a3313e25";
        String payload = "{\"deviceDetails\":{\"appVersion\":\"70.0.3538.77\",\"deviceId\":\"de06aa94-0fae-4d99-9e83-329306baa840\",\"bookingDevice\":\"DESKTOP\",\"networkType\":\"WiFi\",\"deviceType\":\"DESKTOP\",\"deviceName\":null},\"filterRemovedCriteria\":null,\"searchCriteria\":{\"checkIn\":\"2025-11-09\",\"checkOut\":\"2025-11-10\",\"limit\":10,\"roomStayCandidates\":[{\"adultCount\":2,\"rooms\":1,\"childAges\":[]}],\"countryCode\":\"IN\",\"cityCode\":\"CTGOI\",\"locationId\":\"CTGOI\",\"locationType\":\"city\",\"currency\":\"INR\",\"preAppliedFilter\":false,\"userSearchType\":\"city\",\"lastHotelId\":\"202410121919144277\",\"lastHotelCategory\":\"\",\"personalizedSearch\":true,\"nearBySearch\":false,\"totalHotelsShown\":20,\"personalCorpBooking\":false,\"rmDHS\":false,\"lastFetchedWindowInfo\":\"001000000000000000000000001000000000000000000000000000001000#0#60#false\"},\"requestDetails\":{\"visitorId\":\"cda42c42-1586-4512-abf9-143b0cc90e71\",\"visitNumber\":12,\"trafficSource\":{\"type\":\"CMP\",\"source\":\"SEO\"},\"funnelSource\":\"HOTELS\",\"idContext\":\"B2C\",\"pageContext\":\"LISTING\",\"channel\":\"B2Cweb\",\"journeyId\":\"153557540017683684-17eb-4cfc-bcbc-f3da699d4dc0\",\"requestId\":\"7ccff040-6afa-4441-84db-7d58a3313e25\",\"sessionId\":\"f961ab8e-2d78-4d45-92ed-a6a621ecb078\",\"subPageContext\":\"\",\"couponCount\":2,\"seoCorp\":false,\"loggedIn\":false,\"forwardBookingFlow\":false},\"featureFlags\":{\"soldOut\":true,\"staticData\":true,\"extraAltAccoRequired\":false,\"freeCancellation\":true,\"coupon\":true,\"walletRequired\":true,\"poisRequiredOnMap\":true,\"mmtPrime\":false,\"checkAvailability\":true,\"reviewSummaryRequired\":true,\"persuasionSeg\":\"P1000\",\"persuasionsRequired\":true,\"persuasionsEngineHit\":true,\"shortlistingRequired\":false,\"similarHotel\":false,\"personalizedSearch\":true,\"originListingMap\":false,\"selectiveHotels\":false,\"seoDS\":true,\"flashDealClaimed\":false,\"showRushDealsBottomSheet\":true,\"persuasionSuppression\":false,\"seoUrlRequired\":false},\"imageDetails\":{\"types\":[\"professional\"],\"categories\":[{\"type\":\"H\",\"count\":1,\"height\":162,\"width\":243,\"imageFormat\":\"webp\"}]},\"reviewDetails\":{\"otas\":[\"MMT\",\"TA\",\"MANUAL\"],\"tagTypes\":[\"BASE\",\"WHAT_GUEST_SAY\"]},\"filterCriteria\":[{\"filterGroup\":\"STAR_RATING\",\"filterValue\":\"5\",\"filterRange\":null,\"isRangeFilter\":false}],\"matchMakerDetails\":{},\"sortCriteria\":null,\"expData\":\"{APE:10,PAH:5,PAH5:T,WPAH:F,BNPL:T,MRS:T,PDO:PN,MCUR:T,ADDON:T,CHPC:T,AARI:T,NLP:Y,RCPN:T,PLRS:T,MMRVER:V3,BLACK:T,IAO:F,BNPL0:T,EMIDT:1,HAFC:T,CRI:T,ALC:T,LSTNRBY:T,PLV2:T,HIS:DEFAULT,HFC:T,VIDEO:0,MLOS:T,CV2:T,SOU:T,APT:T,AIP:T,PERNEW:T,RTBC:T,PCCE:T,FLTRPRCBKT:T,UGCV2:T,CRF:T,GALLERYV2:T}\",\"appliedBatchKeys\":[],\"userLocation\":{\"city\":\"CTMAA\",\"state\":\"STTN\",\"country\":\"IN\"},\"hotelHighlightCities\":[\"DUBW\",\"NYC\"]}";
        String cookie_val = "lang=eng; ccde=IN; GL=true; isWW=0; userCurrency=INR; s_ecid=MCMID%7C68630175928077928637636001852950524706; mcid=cda42c42-1586-4512-abf9-143b0cc90e71; AMCVS_1E0D22CE527845790A490D4D%40AdobeOrg=1; cmp_attr=SEO; trafficSrc=%7B%22type%22%3A%22CMP%22%2C%22source%22%3A%22SEO%22%7D; _adck_id=dc30eeab58e541348bbab95d632774d68a26332e6ad3735a515df546bfa36ae7; isGdprRegion=0; AllocationTracking=agw%2Cagx%2Cbv%2C8zs%2C9ak%2Caac%2Caae%2C68%2Cage%2C6e%2C6h%2C6j%2C6n%2Carr%2Caku; bm_sz=466E93D09DFFAF8735A2CAC861EB4262~YAAQZC3fF9x7fKCZAQAA70u4uB3WAT4BmB9P35x5VczGSO6Wo1mHfXgwjypV8O5yLn4gU6gpbWENFVK7pWIaBYdxoM+lbAYry0aw3aeqTwdOxMyev48sD3909tvs1BZG3PiFtA1lwoX+Lt3RSpJp6ZpC8PRlcvAHWkEOHPwNEKC78+6OGIZhSXtgpUkhR4fiUKaWyNKJu2OGYtyEAMP9faYsMF2JfsYteJojvd1W6MyhzziVPy2z5sQCRMdg3S+NPB3LPj3kXBv7WJDT83rEVB63l3aBmkZ/3xr1bR5Sp4bNfKvRHgAFI3+1QTkaPC3Tpt6ab0k+OiVCI9Agbfdvz8TYbLs8LRsGdXQFpyiCXjcxUPNLRBZ8Jz+89byK~4272450~3294519; bm_ss=ab8e18ef4e; bm_mi=C168FE7459A7DE5F051C962E264C1E02~YAAQncMzuAl3s5eZAQAA6+5fuR1Uiw0rBZWNWprqdPebKM+kHUi1RJoDbh9QJJufuNzBSw8TCjOvvGuBMQdAoS3CVJSsK+1CXpUG86uKxwWr6DUXuTVrvxA4gePDgjb+zCk; bm_so=CB2D49DC07022E536CACECB4504A3C80233C4538C7002B3D1645D99F76C326FA~YAAQncMzuAt3s5eZAQAA6+5fuQUkgSK/Jd9EJE5/DWMIHftDMaJl888B6aRuyKCFIDduytaof54dhy8+KbbsJe4TPj8BDsUrAfKQc7PWUSyV6ITUjXR4lZtboGzibWLKnwUhCAgsfk+Wpj9/qEdu2X+W4eNM9A3p4YPu1cwJY6UUV40OrTr3deUQF/26jdUBvtqjuUbTltNEkpoFkNLAUr8aGsHcv6Q79I944fD1sefPZNN8X5/NtueCVf1o7ikpA7LmqU0d6E1eXlOd106aLMjOqu+DbJ+/tqRp92uUggf33i28djcQhoOsdPfC5W9LwjCwBdft7pqOd/OlDf4juswUCbbK7Mz4CxD0yz8el5S3BwQ9cksURENtusY5ZTe/EBxG92F71ePeYRv14RbS9M0jCGGjXjvwTz3Vl2LlPTh0bthzze2ZsrHvKEsQL4CPxzXDuhPxO9VrbUfH9tD2tNV4Jyc=; AMCV_1E0D22CE527845790A490D4D%40AdobeOrg=-1712354808%7CMCIDTS%2C20368%7CMCMID%7C68630175928077928637636001852950524706%7CMCAID%7CNONE%7CMCOPTOUT-1759758896s%7CNONE%7CvVersion%7C4.3.0; _abck=8B163A8662984E1C85629B7DBB6E2337~0~YAAQncMzuFR3s5eZAQAAy/NfuQ6UE4djFRu1Yr5SNs0IX3+VkPHwBiCmwzbxDGH/Vz4AFL8Woo3EfAXqOGmM4tkjV9ZBjWMlyKAQ7zZ88L75dXp2RFcbY0WBxPgtMv4Hqb4oU602QbcmjBFlhada748wbG5q3BpBPBiVOZfj0K084hhanCkfBVDLHxpjgI91LxpNo2gyRi+1+ZUBCcNaQv84DH+c74PMHKebwBhmXajhWusvYGapPACUmW/rmd1lakN0Iq0jU+1KDrPpf4eGQK/DpnbJEPtPRMn6Zm3RV2Z/jhk81kgL9+XOh4JhYvB3qO5tnhIXZOgDMRZPtT+uwhy0YgcKvgm12y6/kOvvEgc1icurDHFjX9mPkhFjRFiCrESoPvRT0xbPLRwc/manIjMv0I3+MVaoqW2Yjz3GrWDU/oH99CXEmFcK0+5YgA12XhZvXCbzmeqPXSXFpQ9HN1nUN3PBbzhpBA1K/Kv8sMEqcJEDjNv11YD7S6bWsTnf4tpQK9XwAqn81S+eYF1Nm1770XA3p3+WWlsmbYeO/DF0s3ziCZDs+32erWkcCSmi3ou7wKeSMFw9aJnJD/o0MONrtoaW+8xIg50PoyCIGxC/kaAPc8hGzAOtFz2wteXDDV7+Qmhz2jw2L3wDzcZGCtiTON355CjsmSIGjxUUzkXpV1w6noCEJpt0WGc5zUTJUoo+0xxocMbz~-1~-1~1759752272~AAQAAAAE%2f%2f%2f%2f%2fzpUd6Hx0nfZd8nBkugYLTAe2qeJnsXV5WjSTEQ0lwWdVbEyjk2uJXAI6IXZ%2fCJsn8QfIErnjfC6HQzmw3oEkj02VpP5OI%2fguyuzRQ4otqWwPAb51Txkbfz1p6lACq+XA19Epuyd6yHkHhGGzhSr3ULGlk%2f+O%2fDI+7BxQmPFCw%3d%3d~-1; MMYTUUID=aOOuEykwEy3jKU2dJCygnAAAACE; bm_sv=34CD8C5B265738332AF9AE0A8C76CD58~YAAQncMzuOp3s5eZAQAA1fxfuR3mFS5dC3dLqWdiVU/pMGMtOgstAxn8aXkeHYkrJTbO5EkiQUuAsxm32LJSSxwVjF3OVxVqb8DKDryFVpQGcxfGrXZvEVu4VxOZ6aTwFcszI5tNZIjghe9f8xRgF55GjV1JyLS7nICzHvyXF6deCb9+c4vqtbdoWgQxpLV5otDEhdZHuq3K1TPPC7uiJWtDl/skvnoICQOpv+PO5yPJjWTELy0zHYGRvKuB2ibQrd5qTQ==~1; s_pers=%20s_vnum%3D1761935400900%2526vn%253D11%7C1761935400900%3B%20s_depth%3D2%7C1759753499537%3B%20s_lv%3D1759751700027%7C1854359700027%3B%20s_lv_s%3DLess%2520than%25201%2520day%7C1759753500027%3B%20gpv_pn%3Dseo%253Adomestic%2520hotels%253Acategory%7C1759753500041%3B%20s_invisit%3Dtrue%7C1759753500046%3B%20s_nr3650%3D1759751700049-Repeat%7C2075111700049%3B%20s_nr30%3D1759751700052-Repeat%7C1762343700052%3B%20s_nr120%3D1759751700054-Repeat%7C1770119700054%3B%20s_nr7%3D1759751700057-Repeat%7C1760356500057%3B; ak_bmsc=5E82929D7CCA3F1D4C3DE0C0B3BC1B98~000000000000000000000000000000~YAAQncMzuBh4s5eZAQAA7v9fuR3doo7RnvHNhT8xzphCAICHBtE5JyJRo1xtkyb01c26D0KeAbul4Yu/mHvTEn769Az8JjchdyrtsV4UpK5rroj57/nQvnMW5rYOdt/C5TmUOHFzoQiHge4QvbBxacVdgLXx+bpKOFz62wVSrODxt90JyOdmjyEh1Kh6mwygc2jHkU3662+8UGwNssAM5Gt+fJV4";

        try {
        	Response res = Jsoup.connect(url_new)
                    .ignoreContentType(true)
                    
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36")
                    .header("Accept-Language", "en-US,en;q=0.5")   
                    .header("Referer", "https://www.makemytrip.com")
                    .header("Usr-Mcid", "68630175928077928637636001852950524706")
                    
                    .method(Connection.Method.POST)
                    .cookie("Cookie", cookie_val)
                    .requestBody(payload)
                    .timeout(60000)
                    .execute();
            int statusCode = res.statusCode();
            System.out.println("Status Code: " + statusCode);

            if (statusCode == 200) {
                // Get the raw response body as a string
                String jsonResponse = res.body();
                
                // Print the raw JSON to the console
                System.out.println("--- RAW JSON RESPONSE ---");
                System.out.println(jsonResponse);
                System.out.println("--- END OF RESPONSE ---");
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}