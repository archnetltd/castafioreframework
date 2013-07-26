package org.castafiore.shoppingmall.checkout;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.shoppingmall.delivery.Delivery;
import org.castafiore.wfs.types.Article;
import org.hibernate.annotations.Type;

@Entity()
public class Order extends Article {
	
	public Order(){
		super();
	}
	
	@Type(type="text")
	private String orderedFrom;

	private String code;

	private BigDecimal total = new BigDecimal(0);
	
	//Cash, Check, Credit
	@Type(type="text")
	private String paymentMethod;
	
	@Type(type="text")
	private String chequeNo;
	
	@Type(type="text")
	private String pointOfSale;
	
	@Type(type="text")
	private String orderedBy;
	
	@Type(type="text")
	private String bankName;
	
	private Date dateOfTransaction = new Date();
	
	private Date startInstallmentDate = new Date();
	
	@Type(type="text")
	private String source = "Unknown";
	
	
	private BigDecimal installment = BigDecimal.ZERO;
	
	private BigDecimal joiningFee = BigDecimal.ZERO;
	
	
	

	public Date getStartInstallmentDate() {
		if(dateOfTransaction != null && startInstallmentDate != null)
		{
			if(dateOfTransaction.before(startInstallmentDate)){
				return dateOfTransaction;
			}
		}
			
		if(startInstallmentDate == null)
			return getDateOfTransaction();
		return startInstallmentDate;
	}

	public void setStartInstallmentDate(Date startInstallmentDate) {
		this.startInstallmentDate = startInstallmentDate;
	}

	public BigDecimal getJoiningFee() {
		if(joiningFee == null){
			joiningFee = BigDecimal.ZERO;
		}
		return joiningFee;
	}

	public void setJoiningFee(BigDecimal joiningFee) {
		this.joiningFee = joiningFee;
	}

	public BigDecimal getInstallment() {
		if(installment == null){
			installment = BigDecimal.ZERO;
		}
		return installment;
	}

	public void setInstallment(BigDecimal installment) {
		this.installment = installment;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getDateOfTransaction() {
		if(dateOfTransaction == null){
			dateOfTransaction = getDateCreated().getTime();
		}
		return dateOfTransaction;
	}

	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getPointOfSale() {
		return pointOfSale;
	}

	public void setPointOfSale(String pointOfSale) {
		this.pointOfSale = pointOfSale;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public String getOrderedFrom() {
		return orderedFrom;
	}

	public void setOrderedFrom(String orderedFrom) {
		this.orderedFrom = orderedFrom;
	}

	public BigDecimal getTotal() {
		return total;
	}
	
	public BigDecimal getTax(){
		BigDecimal tax = total.multiply(new BigDecimal("0.15"));
		return tax;
	}
	
	public BigDecimal getSubTotal(){
		BigDecimal tax = total.multiply(new BigDecimal("0.85"));
		return tax;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<OrderEntry> getEntries() {
		return getFiles(OrderEntry.class).toList();
	}
	
	public void update(){
		total = new BigDecimal(0);
		for(OrderEntry entry : getFiles(OrderEntry.class).toList()){
			total = total.add(entry.getTotal());
		}
		
	}
	
	
	
	
	public ShippingInformation getShippingInformation(){
		return (ShippingInformation)getFile("shipping");
	}
	
	public BillingInformation getBillingInformation(){
		return (BillingInformation)getFile("billing");
	}
	
	
	public BillingInformation createBillingInformation(){
		return createFile("billing", BillingInformation.class);
	}
	
	public ShippingInformation createShippingInformation(){
		return createFile("shipping", ShippingInformation.class);
	}
	
	public Delivery createDelivery(){
		return createFile("delivery", Delivery.class);
	}
	
	
	public SalesOrderEntry createEntry(String code){
		SalesOrderEntry entry = getFile(code, SalesOrderEntry.class);
		if(entry == null){
			entry = createFile(code, SalesOrderEntry.class);
		}
		
		return entry;
	}
	
	public Delivery getDelivery(){
		return (Delivery)getFile("delivery");
	}

	public void deleteEntry(String code) {
		getFile(code).remove();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	

}
