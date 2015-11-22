import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.text.BadLocationException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;


/**
 * 
 */

/**
 * @author Hamurabi Araújo
 *
 */
public class BingSearch {
	private static final String PROTOCOL = "http://";
	private static final String AUTHORITY = "bing.com/";
	private static final String PATH = "search?";
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws BadLocationException 
	 */
	public static void main(String[] args) throws IOException, BadLocationException {
		// TODO Auto-generated method stub
		URL url = new URL(PROTOCOL + AUTHORITY +  PATH + formatQuery(getStringSearch()));
		System.out.println("Pegando conteúdo de: " + url);
		System.out.println(getContent(url));
		
		Document doc = Jsoup.parse(getContent(url).toString());
		Elements links = doc.select("h2 > a[href]"); //li.b_algo > .b_title > 
		
		print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }

	}
	
	private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
	
	/**
	 * @return the string to search
	 * @throws IOException
	 */
	public static String getStringSearch() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String searchString = reader.readLine();
		return searchString;
	}
	
	/**
	 * @param searchString the string to search unformatted
	 * @return query formatted
	 */
	public static String formatQuery(String searchString) {
		String query = "q=" + searchString.replaceAll(" ", "+") + "&count=50";
		return query;
	}
	
	/**
	 * @param url to pull the content
	 * @return a StringBuffer with the all content 
	 * @throws IOException
	 * @throws BadLocationException 
	 */
	public static StringBuffer getContent(URL url) throws IOException, BadLocationException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.connect();
		InputStreamReader reader = new InputStreamReader((java.io.InputStream) connection.getContent());

		BufferedReader buffReader = new BufferedReader(reader);
		StringBuffer content = new StringBuffer();
		String line;
		do {
			line = buffReader.readLine();
		    content.append(line + "\n");
		} while (line != null);
		return content;
	}
}
