
//Cliff Grandin
//NetID: cjg189
//
//Kartik Sonavane
//NetID: ks1324
//

package gui.view;

public class Song {
	
	String name;
	String artist;
	String album;
	String year;
	
	public Song() {
		this.name = "";
		this.artist = "";
		this.album = "";
		this.year = "";
	}
	
	public Song(String name, String artist){
		this.name = name;
		this.artist = artist;
	}
	
	public Song(String name, String artist, String album, String year){
		this.name = name; 
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public String getName() {
		return name;
	}
	
	public String getArtist() {
		
		return artist;
	}
	
public String getAlbum(){
		
		return album;
	}
	
	public String getYear(){
		
		return year;
	}
	
	@Override
	public String toString(){
		
		return name + "\t\t\t\t" + artist;
		
		//return "\nName: " + name + "\nArtist: " + artist + "\nAlbum: " + album + "\nYear: " + year + "\n"; 
	}

public String fileWriterToString(){
	
	//return "name: " + name + "\n" + "artist: " + artist + "\n" + "album: " + album + "\n" + "year: " + year + "\n\n";
	return "name: " + name + "\n" + "artist: " + artist + "\n" + "album: " + album + "\n" + "year: " + year + "\n\n";
		
		//return "name: " + name + "\nartist: " + artist + "\nalbum: " + album + "\nyear: " + year + "\n\n";
	}
	
/*	@Override
	public String toString(){
		
		return name + "\t" + artist + "\t" + album + "\t" + year;
		
		//return "\nName: " + name + "\nArtist: " + artist + "\nAlbum: " + album + "\nYear: " + year + "\n"; 
	}
*/
}
