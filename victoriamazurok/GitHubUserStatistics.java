import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GitHubUserStatistics {
    public final static String USER_NAME;


    public static void main(String[] args) {
        String apiUrl = "https://api.github.com/users/";
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");


            if (conn.getResponseCode() != 200) {
                System.out.println("Failed : HTTP error code : " + conn.getResponseCode());
                return;
            }


            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();


            String json = sb.toString();
            printStatistics(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void printStatistics(String json) {
        // Simple extraction using regex (for demonstration, not robust)
        String name = extractValue(json, "name");
        String publicRepos = extractValue(json, "public_repos");
        String followers = extractValue(json, "followers");
        String following = extractValue(json, "following");
        String createdAt = extractValue(json, "created_at");


        System.out.println("GitHub User Statistics:");
        System.out.println("Name: " + name);
        System.out.println("Public Repos: " + publicRepos);
        System.out.println("Followers: " + followers);
        System.out.println("Following: " + following);
        System.out.println("Account Created At: " + createdAt);
    }


    private static String extractValue(String json, String key) {
    String pattern = "\\\"" + key + "\\\":(\\\".*?\\\"|\\d+)";
        java.util.regex.Pattern r = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher m = r.matcher(json);
        if (m.find()) {
            String value = m.group(1);
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            return value;
        }
        return "N/A";
    }
}

