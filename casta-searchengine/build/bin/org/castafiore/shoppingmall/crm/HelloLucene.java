package org.castafiore.shoppingmall.crm;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;

import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;


import java.io.IOException;

public class HelloLucene {
  public static void main(String[] args) throws IOException, ParseException {
    // 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
    StandardAnalyzer analyzer = new StandardAnalyzer();

    // 1. create the index
    Directory index = new RAMDirectory();

   // IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_35, analyzer);

    IndexWriter w = new IndexWriter(index, analyzer,MaxFieldLength.LIMITED);
    
    /*
     * FS00035/12
sdfsdf sdf
23423 23423
FS00036/12
Rossaye Abdool Kureem
R. Tagore Avenue Mesnil Phoenix
     */
    addDoc(w, "FS00035/12");
    addDoc(w, "sdfsdf sdf");
    addDoc(w, "23423 23423");
    addDoc(w, "Rossaye Abdool Kureem");
    addDoc(w, "R. Tagore Avenue Mesnil Phoenix");
    w.close();

    // 2. query
    String querystr = args.length > 0 ? args[0] : "FS00035/12";

    // the "title" arg specifies the default field to use
    // when no field is explicitly specified in the query.
    Query q = new QueryParser("title", analyzer).parse(querystr);

    // 3. search
    int hitsPerPage = 10;
    IndexSearcher searcher = new IndexSearcher(index);
    TopDocCollector collector = new TopDocCollector(hitsPerPage);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
    
    // 4. display results
    System.out.println("Found " + hits.length + " hits.");
    for(int i=0;i<hits.length;++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      System.out.println((i + 1) + ". " + d.get("title"));
    }

    // searcher can only be closed when there
    // is no need to access the documents any more. 
    searcher.close();
  }

  private static void addDoc(IndexWriter w, String value) throws IOException {
    Document doc = new Document();
    doc.add(new Field("title", value, Field.Store.YES, Field.Index.ANALYZED));
    w.addDocument(doc);
  }
}
