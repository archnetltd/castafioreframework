package org.castafiore.shoppingmall.crm;


public class OrdersIndexer {
//	private IndexWriter writer;
//	private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
//	
//	private String indexDir = "c:\\java\\index";
//	
//	public List<OrderInfo> getOrdersToIndex(){
//		OrderInfo info = new OrderInfo();
//		OrderService service = new OrderService();
//		List status = new ArrayList(1);
//		//status.add(11);
//		List<OrderInfo> infos= service.searchByExample(info, null, null, true, null,0, 30000);
//		return infos;
//	}
//	
//	
//	public void openStore()throws Exception{
//		if(writer == null ){
//			FSDirectory dir = FSDirectory.open(new File(indexDir));
//
//			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
//			writer = new IndexWriter(dir, config);
//		}
//		
//	}
//	
//	
//	public void doIndex()throws Exception{
//		openStore();
//		List<OrderInfo> orders = getOrdersToIndex();
//		System.out.println(orders.size() + " orders to index");
//		for(OrderInfo info : getOrdersToIndex()){
//			System.out.println("indexing " + info.getFsCode());
//			Document d = index(info);
//			writer.addDocument(d);
//		}
//		writer.commit();
//		writer.close();
//		writer = null;
//		System.gc();
//		
//		System.out.println("finished indexing " + orders.size() + " orders");
//	}
//	
//	
//	public List<OrderInfo> searchTerm(String term)throws Exception{
//		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexDir)));
//		IndexSearcher searcher = new IndexSearcher(reader);
//		TopScoreDocCollector collector = TopScoreDocCollector.create(50, true);
//		Query q = new QueryParser(Version.LUCENE_43, "contents",analyzer).parse(term);
//		searcher.search(q, collector);
//		List<OrderInfo> result = new ArrayList<OrderInfo>(50);
//		ScoreDoc[] hits = collector.topDocs().scoreDocs;
//
//		// 4. display results
//		System.out.println("Found " + hits.length + " hits.");
//		for (int i = 0; i < hits.length; ++i) {
//			int docId = hits[i].doc;
//			final OrderInfo info = new OrderInfo();
//			final Document d = searcher.doc(docId);
//			ReflectionUtils.doWithFields(OrderInfo.class, new FieldCallback() {
//				
//				@Override
//				public void doWith(Field field) throws IllegalArgumentException,IllegalAccessException {
//					if(true){
//						Object o = field.get(info);
//						if(o != null){
//							if(field.getType().isAssignableFrom(String.class)){
//								String s = d.getField(field.getName()).stringValue();
//								field.set(info, s);
//								//d.add(new StringField(field.getName(), o.toString(),Store.YES));
//							}
//							else if(field.getType().isAssignableFrom(Date.class)){
//								try{
//									Number s = d.getField(field.getName()).numericValue();
//									Date val = new Date(s.longValue());
//									field.set(info, val);
//							
//								}catch(Exception e){
//									
//								}
//							}else if(field.getType().isAssignableFrom(Integer.class)){
//								try{
//									Number s = d.getField(field.getName()).numericValue();
//									
//									field.set(info, s.intValue());
//							
//								}catch(Exception e){
//									
//								}
//							}else if(field.getType().isAssignableFrom(BigDecimal.class)){
//								try{
//									Number s = d.getField(field.getName()).numericValue();
//									BigDecimal val = new BigDecimal(s.doubleValue());
//									field.set(info, val);
//							
//								}catch(Exception e){
//									
//								}
//							}
//						}
//					}
//					
//				}
//			});
//			
//			result.add(info);
//			
//		}
//		
//		return result;
//		
//		
//	}
//	
//	public Document index(final OrderInfo info){
//		final Document d = new Document();
//		
//		
//		ReflectionUtils.doWithFields(OrderInfo.class, new FieldCallback() {
//			
//			@Override
//			public void doWith(Field field) throws IllegalArgumentException,IllegalAccessException {
//				if(true){
//					Object o = field.get(info);
//					if(o != null){
//						if(field.getType().isAssignableFrom(String.class))
//							d.add(new StringField(field.getName(), o.toString(),Store.YES));
//						else if(field.getType().isAssignableFrom(Date.class)){
//							Date val = (Date) field.get(info);
//							d.add(new LongField(field.getName(), val.getTime(), Store.YES));
//						}else if(field.getType().isAssignableFrom(Integer.class)){
//							Integer val = (Integer) field.get(info);
//							d.add(new IntField(field.getName(), val, Store.YES));
//						}else if(field.getType().isAssignableFrom(BigDecimal.class)){
//							BigDecimal val = (BigDecimal) field.get(info);
//							d.add(new DoubleField(field.getName(), val.doubleValue(), Store.YES));
//						}
//					}
//				}
//				
//			}
//		});
//		return d;
//		//d.add(new StringField("", value, stored))
//	}

}
