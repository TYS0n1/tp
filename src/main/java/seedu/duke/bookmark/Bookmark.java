package seedu.duke.bookmark;

import seedu.duke.command.AddBookmarkCommand;
import seedu.duke.exception.DukeException;
import seedu.duke.exception.DukeExceptionType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a bookmark of a URL of a webpage.
 * It stores the URL and a description of the webpage.
 * It also contains a method which can extract the URL and description from a given string in a certain format.
 */
public class Bookmark {
    private String module;
    private String description;
    private String url;
    private static final String SEPARATOR = " | ";

    /**
     * Constructs a bookmark object containing a URL and description of the webpage.
     *
     * @param url The URL of the webpage.
     * @param description The description of the webpage.
     */
    public Bookmark(String module, String description, String url) {
        this.module = module.trim();
        this.description = description.trim();
        this.url = url.trim();
    }
    
    /**
     * Returns the topic, URL and description that can be detected from the given input.
     *
     * @param input the string input by the user.
     * @return a list of strings containing the topic, URL and the description
     */
    public static List<String> extractModuleDescriptionAndUrl(String input) throws DukeException {
        input = input.substring(AddBookmarkCommand.ADD_KW.length()).trim();
        List<String> moduleDescriptionUrl = new ArrayList<>(Arrays.asList(input.split(" ", 3)));
        if (moduleDescriptionUrl.size() == 2) {
            moduleDescriptionUrl.add(0, "");  // No entry for module
        }
        if (moduleDescriptionUrl.size() != 3) {
            throw new DukeException(DukeExceptionType.INVALID_BOOKMARK_INPUT);
        }
        if (moduleDescriptionUrl.get(1).isBlank() || moduleDescriptionUrl.get(2).isBlank()) {
            throw new DukeException(DukeExceptionType.EMPTY_DESCRIPTION);
        }
        if (!isUrlValid(moduleDescriptionUrl.get(2))) {
            throw new DukeException(DukeExceptionType.INVALID_URL);
        }

        return moduleDescriptionUrl;
    }

    private static Boolean isUrlValid(String url) {
        boolean isValid = false;
        if (url.startsWith("www.") || url.startsWith("https://")) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * This method opens a web browser and open the URL of the bookmark.
     */
    public void launch() throws DukeException {
        try {
            launchUrl();
        } catch (IOException e) {
            throw new DukeException(DukeExceptionType.ERROR_LAUNCHING_URL);
        }
    }

    private void launchUrl() throws IOException {
        Runtime rt = Runtime.getRuntime();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            launchUrlForWindows(rt);
        } else if (os.contains("mac")) {
            launchUrlForMac(rt);
        } else if (os.contains("nix") || os.contains("nux")) {
            launchUrlForLinux(rt);
        }
    }

    private void launchUrlForWindows(Runtime rt) throws IOException {
        rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
    }

    private void launchUrlForMac(Runtime rt) throws IOException {
        rt.exec("open " + url);
    }

    private void launchUrlForLinux(Runtime rt) throws IOException {
        String link;
        if (url.startsWith("www.")) {
            link = "https://" + url;
        } else {
            link = url;
        }
        rt.exec("xdg-open " + link);
    }

    /**
     * Returns the URL of the bookmark.
     *
     * @return a string of the URL.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the description of the bookmark.
     *
     * @return a string of the description of the bookmark.
     */
    public String getDescription() {
        return description;
    }

    public String getModule() {
        return module;
    }

    public String getBookmarkAsString() {
        return  ("[" + module + "] " + description + " " +  url + System.lineSeparator());
    }

    public String getExport() {
        return module + SEPARATOR + description + SEPARATOR + url;
    }

    public static Bookmark initBookmark(String data) {
        List<String> details =  Arrays.asList(data.split("\\|"));
        String module = details.get(0).trim();
        String description = details.get(1).trim();
        String url = details.get(2).trim();
        Bookmark bookmark = new Bookmark(module, description, url);
        return bookmark;
    }
}

