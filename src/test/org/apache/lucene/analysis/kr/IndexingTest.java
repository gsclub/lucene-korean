package org.apache.lucene.analysis.kr;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import junit.framework.TestCase;

public class IndexingTest extends TestCase {

	private Directory directory;
	
	protected void setUp() throws Exception {
		directory = FSDirectory.open(new File("index"));		
	}
	
	private IndexWriter getWriter() throws IOException {
		return new IndexWriter(directory, new KoreanAnalyzer(), IndexWriter.MaxFieldLength.UNLIMITED);
	}
	
	public void testIndexWriter() throws IOException {
		
		IndexWriter writer = getWriter();
		
		String description = "Approved for entry into archive by p pant (momo31@gmail.com) on 2011-11-18T05:08:46Z (GMT) No. of bitstreams: 0";
		String publisher = "漢陽大學校";
		String title = "硏究開發費 會計에 關한 硏究";
		
		
		Document doc = new Document();
		doc.add(new Field("description", description, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("publisher", publisher, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
		
		writer.addDocument(doc);
		
		writer.close();
		
	}
	
}
