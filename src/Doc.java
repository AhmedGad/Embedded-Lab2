import java.util.Vector;

public class Doc {
	private String title;
	private String author;
	private String content;
	private int id;
	
	public Vector variableKaman3ashanElNefs = null;
	
	public Doc(String title, String author, String content, int id) {
		this.title = title;
		this.author = author;
		this.content = content;
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
