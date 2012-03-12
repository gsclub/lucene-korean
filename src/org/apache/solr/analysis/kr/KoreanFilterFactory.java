package org.apache.solr.analysis.kr;

import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.kr.KoreanFilter;
import org.apache.solr.analysis.BaseTokenFilterFactory;

public class KoreanFilterFactory extends BaseTokenFilterFactory {

	private boolean bigrammable = true;
	
	private boolean hasOrigin = true;
	
	public TokenStream create(TokenStream tokenstream) {

		return new KoreanFilter(tokenstream, bigrammable, hasOrigin);
	}

	public void setBigrammable(boolean bool) {
		this.bigrammable = bool;
	}
	
	public void setHasOrigin(boolean bool) {
		this.hasOrigin = bool;
	}
	
}
