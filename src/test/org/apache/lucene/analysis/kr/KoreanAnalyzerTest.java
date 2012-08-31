package org.apache.lucene.analysis.kr;

import java.io.StringReader;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ReusableAnalyzerBase.TokenStreamComponents;
import org.apache.lucene.analysis.kr.utils.HanjaUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.util.Version;

import junit.framework.TestCase;

public class KoreanAnalyzerTest extends TestCase {

	/**
	 * t.getPositionIncrement() 는 같은 단어에서 추출되었는지, 다른 단어에서 추출되었는지를 알려준다.
	 * 즉 1이면 현재의 색인어는 새로운 단어에서 추출된 것이고
	 * 0 이면 이전 색인어와 같은 단어에서 추출된 것이다.
	 * 이 값은 검색 랭킹에 영향을 미친다.  즉 값이 작으면 검색랭킹이 올라가서 검색시 상위에 올라오게 된다.
	 * @throws Exception
	 */
	public void testKoreanTokenizer() throws Exception {
		
		String source = "우리나라라면에서부터 일본라면이 파생되었잖니?";
		source = "呵呵大笑 가교복합체와 가공액을 포함하였다.";
		
		long start = System.currentTimeMillis();
		
		KoreanAnalyzer analyzer = new KoreanAnalyzer();
		analyzer.setHasOrigin(false);
		TokenStream stream = analyzer.tokenStream("s",  new StringReader(source));
	    TokenStream tok = new KoreanFilter(stream);
	    
		while(tok.incrementToken()) {
			CharTermAttribute termAttr = stream.getAttribute(CharTermAttribute.class);
			OffsetAttribute offAttr = stream.getAttribute(OffsetAttribute.class);
			PositionIncrementAttribute posAttr =  stream.getAttribute(PositionIncrementAttribute.class);
			TypeAttribute typeAttr =  stream.getAttribute(TypeAttribute.class);
						
			System.out.println(new String(termAttr.buffer(),0,termAttr.length()));
		}
		
		System.out.println((System.currentTimeMillis()-start)+"ms");
	}
	
	public void testStandardTokenizer() throws Exception {
		
		String source = "우리나라라면에서부터 일본라면이 파생되었잖니?";
		source = "너는 너는 다시 내게 돌아 올거야. school is a good place 呵呵大笑 呵呵大笑";
		
		long start = System.currentTimeMillis();
		
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_32);
		TokenStream stream = analyzer.tokenStream("s",  new StringReader(source));
	    TokenStream tok = new StandardFilter(Version.LUCENE_32,stream);
	    
		while(tok.incrementToken()) {
			CharTermAttribute termAttr = stream.getAttribute(CharTermAttribute.class);
			OffsetAttribute offAttr = stream.getAttribute(OffsetAttribute.class);
			PositionIncrementAttribute posAttr =  stream.getAttribute(PositionIncrementAttribute.class);
			TypeAttribute typeAttr =  stream.getAttribute(TypeAttribute.class);
						
			System.out.println(new String(termAttr.buffer()));
		}
		
		System.out.println((System.currentTimeMillis()-start)+"ms");
	}
	
	
	public void testJavaEscape() throws Exception {
		
		String str = StringEscapeUtils.unescapeHtml("&#48085;");
		System.out.println(str);
		
		//落落長松
		String han = StringEscapeUtils.unescapeJava("0x3400");
		han = StringEscapeUtils.escapeJava("落");
		
		System.out.println(han);
		
	}
	
	public void testConvertHanja() throws Exception {
		
		String han = "呵呵大笑";

		for(int jj=0;jj<han.length();jj++) {
			char[] result = HanjaUtils.convertToHangul(han.charAt(jj));
			for(int i=0;i<result.length;i++)
				System.out.print(result[i]);
			
			System.out.println();
		}
	}
	
	public void testHanjaConvert() throws Exception {
		
		String source = "呵呵大笑  落落長松 ";
		
		long start = System.currentTimeMillis();
		
		KoreanAnalyzer analyzer = new KoreanAnalyzer();
		TokenStream stream = analyzer.tokenStream("s",  new StringReader(source));
	    TokenStream tok = new KoreanFilter(stream);
	    
		while(tok.incrementToken()) {
			CharTermAttribute termAttr = stream.getAttribute(CharTermAttribute.class);
			OffsetAttribute offAttr = stream.getAttribute(OffsetAttribute.class);
			PositionIncrementAttribute posAttr =  stream.getAttribute(PositionIncrementAttribute.class);
			TypeAttribute typeAttr =  stream.getAttribute(TypeAttribute.class);
						
			System.out.println(new String(termAttr.buffer()));
		}
		
		System.out.println((System.currentTimeMillis()-start)+"ms");
	}
	
}
