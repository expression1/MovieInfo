package net.pms.movieinfo.plugins;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class FILMDELTAPlugin implements Plugin
{
	private int fs;
	private StringBuffer sb;
	private String newURL;
	private ArrayList<String> castlist = new ArrayList<String>();

	public void importFile(BufferedReader in)
	{
		try {
			BufferedReader br = in;
			sb = new StringBuffer();
			String eachLine = null;
			if(br != null)
			eachLine = br.readLine();

			while (eachLine != null) {
				sb.append(eachLine);
				eachLine = br.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getTitle() 
	{
		if(sb != null)
		fs = sb.indexOf("<title>");
		String title = null;
		if (fs > -1) {
			title = sb.substring(fs + 7, sb.indexOf("</title>", fs));
			title = title.replace(" - Filmdelta - Filmdatabas p&aring; svenska", "");
			}
		return title;
	}
	public String getPlot()
	{
		fs = sb.indexOf("<div class=\"text\">");
		String plot = null;
		if (fs > -1) {
			plot = sb.substring(fs + 18,sb.indexOf("</div>",fs+18));
			plot = plot.replace("<p>", "").replace("</p>","");
			plot = plot.trim();
//			System.out.println(this.getClass().getSimpleName() + " " + plot);
		}
		return plot;
	}
	public String getDirector()
	{
		
		return null;
	}
	public String getGenre()
	{
		return null;
	}
	public String getTagline()
	{
		return null;
	}
	public String getRating()
	{
		fs = sb.indexOf("<h4>Medlemmarna</h4>");
		if(fs >-1)
			fs = sb.indexOf("Snitt:",fs);
		String rating = null;
		if (fs > -1) {
			rating = sb.substring(fs + 6, sb.indexOf("</", fs+6));
			rating = rating.trim();
		
		if (rating.matches("[0-9]\\.{0,1}[0-9]{0,1}"))
			rating = Double.parseDouble(rating)*2 + "";
			rating += "/10";
		}
		return rating;
	}
	public String getVideoThumbnail()
	{
		return null;
	}
	public ArrayList<String> getCast()
	{
		return castlist;
	}
	public String getTvShow() {return "TV-Serie";}
	public String getCharSet() {return "8859_1";}
	public String getGoogleSearchSite()
	{
		return "filmdelta.se/filmer";
	}
	public String getVideoURL()
	{
		return "http://www.filmdelta.se/filmer###MOVIEID###";
	}
	public String lookForMovieID(BufferedReader in) {
		try {
			String inputLine, temp;
			StringWriter content = new StringWriter();

			while ((inputLine = in.readLine()) != null)
				content.write(inputLine);

			in.close();
			content.close();
			temp = content.toString();
			int fs = temp.indexOf("http://www.filmdelta.se/filmer");
			int end = temp.indexOf("\"", fs+30);
			newURL = null;
			if (fs > -1) {
				newURL = temp.substring(fs + 30, end);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//System.out.println("lookForImdbID Exception: " + e);
			// e.printStackTrace();
		}
		//System.out.println(this.getClass().getName() + "lookForMovieID Returns " + newURL);
		return newURL; //To use as ###MOVIEID### in getVideoURL()
	}
	@Override
	public String getAgeRating() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getTrailerURL() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
