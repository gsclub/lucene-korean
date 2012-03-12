package org.apache.solr.analysis.kr;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.kr.KoreanTokenizer;
import org.apache.lucene.util.Version;
import org.apache.solr.analysis.BaseTokenizerFactory;

public class KoreanTokenizerFactory extends BaseTokenizerFactory {

	private Version version;
	
	public KoreanTokenizerFactory() {
		version = Version.LUCENE_CURRENT;
	}
	
	public KoreanTokenizerFactory(Version v) {
		version = v;
	}
	
	public Tokenizer create(Reader input) {
		return new KoreanTokenizer(version, input);
	}

}
