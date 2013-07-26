package org.castafiore.shoppingmall.imports;


public abstract class AbstractImporterTemplate<T> implements ImporterTemplate<T> {

	private int batchSize = 10;

	@Override
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
