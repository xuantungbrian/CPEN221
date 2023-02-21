package cpen221.mp3.wikimediator;
import cpen221.mp3.fsftbuffer.Bufferable;
import org.fastily.jwiki.core.Wiki;

class PageObj implements Bufferable {  // needs to have id as its implementing Bufferable
    private final Wiki wiki = new Wiki.Builder().withDomain("en.wikipedia.org").build();
    private final String id;
    private final String text;
    public PageObj (String id) {
        this.id = id;
        text = wiki.getPageText(id);
    }
    public String id() {
        return id;
    }
    public String getPageText() {
        return new String(text);
    }
}