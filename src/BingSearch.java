import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.naming.ContextNotEmptyException;
import javax.security.sasl.AuthorizeCallback;
import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.omg.CORBA.portable.InputStream;

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
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL(PROTOCOL + AUTHORITY +  PATH + formatQuery(getStringSearch()));
		System.out.println("Pegando conteúdo de: " + url);
		
		System.out.println(getContent(url));
		
		
		
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
		String query = "q=" + searchString.replaceAll(" ", "+") + "&count=100";
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

//		BufferedReader buffReader = new BufferedReader(reader);
//		StringBuffer content = new StringBuffer();
//		String line;
//		do {
//			line = buffReader.readLine();
//		    content.append(line + "\n");
//		} while (line != null);
//		return content;
		
		EditorKit editor = new HTMLEditorKit();
		HTMLDocument document = (HTMLDocument) editor.createDefaultDocument();
		editor.read(reader, document, 0);
		
		HTMLDocument.Iterator it = document.getIterator(HTML.Tag.A);
	}
}
