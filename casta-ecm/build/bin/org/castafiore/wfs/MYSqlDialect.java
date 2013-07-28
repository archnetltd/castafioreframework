package org.castafiore.wfs;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class MYSqlDialect extends MySQLDialect{

	public MYSqlDialect() {
		super();
		  registerHibernateType(-1, Hibernate.STRING.getName());
	        registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}

}
