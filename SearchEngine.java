import java.io.IOException;
import java.net.URI;
import java.util.HashSet;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    HashSet<String> words = new HashSet<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Welcome to my amazing search engine 8) Go to /add?s={word}" 
                + "to add a word and /search?s={str} to return all words that contain str");
        // } else if (url.getPath().equals("/add")) {
            
        //     return String.format("Number incremented!");
        } else {
            // System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if (!words.contains(parameters[1])) {
                        words.add(parameters[1]);
                        return String.format("Added %s to the search engine! :D", parameters[1]);
                    } else {
                        return String.format("The given string already exists :D");
                    }
                }
            } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String list = "";
                    for (String s : words) {
                        if (s.contains(parameters[1]))
                            list += s + "\n";
                    }
                    return list;
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
