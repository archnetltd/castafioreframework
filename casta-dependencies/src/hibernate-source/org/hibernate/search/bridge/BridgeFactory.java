/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 //$Id: BridgeFactory.java 15567 2008-11-16 15:06:50Z sannegrinovero $
package org.hibernate.search.bridge;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.AssertionFailure;
import org.hibernate.HibernateException;
import org.hibernate.annotations.common.reflection.ReflectionManager;
import org.hibernate.annotations.common.reflection.XClass;
import org.hibernate.annotations.common.reflection.XMember;
import org.hibernate.search.SearchException;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.bridge.builtin.BigDecimalBridge;
import org.hibernate.search.bridge.builtin.BigIntegerBridge;
import org.hibernate.search.bridge.builtin.BooleanBridge;
import org.hibernate.search.bridge.builtin.DateBridge;
import org.hibernate.search.bridge.builtin.DoubleBridge;
import org.hibernate.search.bridge.builtin.EnumBridge;
import org.hibernate.search.bridge.builtin.FloatBridge;
import org.hibernate.search.bridge.builtin.IntegerBridge;
import org.hibernate.search.bridge.builtin.LongBridge;
import org.hibernate.search.bridge.builtin.ShortBridge;
import org.hibernate.search.bridge.builtin.StringBridge;
import org.hibernate.search.bridge.builtin.UriBridge;
import org.hibernate.search.bridge.builtin.UrlBridge;

/**
 * This factory is responsible for creating and initializing build-in and custom <i>FieldBridges</i>.
 *
 * @author Emmanuel Bernard
 * @author John Griffin
 */
public class BridgeFactory {
	private static Map<String, FieldBridge> builtInBridges = new HashMap<String, FieldBridge>();

	private BridgeFactory() {
	}

	public static final TwoWayFieldBridge DOUBLE = new TwoWayString2FieldBridgeAdaptor( new DoubleBridge() );

	public static final TwoWayFieldBridge FLOAT = new TwoWayString2FieldBridgeAdaptor( new FloatBridge() );

	public static final TwoWayFieldBridge SHORT = new TwoWayString2FieldBridgeAdaptor( new ShortBridge() );

	public static final TwoWayFieldBridge INTEGER = new TwoWayString2FieldBridgeAdaptor( new IntegerBridge() );

	public static final TwoWayFieldBridge LONG = new TwoWayString2FieldBridgeAdaptor( new LongBridge() );

	public static final TwoWayFieldBridge BIG_INTEGER = new TwoWayString2FieldBridgeAdaptor( new BigIntegerBridge() );

	public static final TwoWayFieldBridge BIG_DECIMAL = new TwoWayString2FieldBridgeAdaptor( new BigDecimalBridge() );

	public static final TwoWayFieldBridge STRING = new TwoWayString2FieldBridgeAdaptor( new StringBridge() );

	public static final TwoWayFieldBridge BOOLEAN = new TwoWayString2FieldBridgeAdaptor( new BooleanBridge() );

	public static final TwoWayFieldBridge CLAZZ = new TwoWayString2FieldBridgeAdaptor( new org.hibernate.search.bridge.builtin.ClassBridge() );

	public static final TwoWayFieldBridge Url = new TwoWayString2FieldBridgeAdaptor( new UrlBridge() );

	public static final TwoWayFieldBridge Uri = new TwoWayString2FieldBridgeAdaptor( new UriBridge() );

	public static final FieldBridge DATE_YEAR = new TwoWayString2FieldBridgeAdaptor( DateBridge.DATE_YEAR );
	public static final FieldBridge DATE_MONTH = new TwoWayString2FieldBridgeAdaptor( DateBridge.DATE_MONTH );
	public static final FieldBridge DATE_DAY = new TwoWayString2FieldBridgeAdaptor( DateBridge.DATE_DAY );
	public static final FieldBridge DATE_HOUR = new TwoWayString2FieldBridgeAdaptor( DateBridge.DATE_HOUR );
	public static final FieldBridge DATE_MINUTE = new TwoWayString2FieldBridgeAdaptor( DateBridge.DATE_MINUTE );
	public static final FieldBridge DATE_SECOND = new TwoWayString2FieldBridgeAdaptor( DateBridge.DATE_SECOND );
	public static final TwoWayFieldBridge DATE_MILLISECOND =
			new TwoWayString2FieldBridgeAdaptor( DateBridge.DATE_MILLISECOND );

	static {
		builtInBridges.put( Double.class.getName(), DOUBLE );
		builtInBridges.put( double.class.getName(), DOUBLE );
		builtInBridges.put( Float.class.getName(), FLOAT );
		builtInBridges.put( float.class.getName(), FLOAT );
		builtInBridges.put( Short.class.getName(), SHORT );
		builtInBridges.put( short.class.getName(), SHORT );
		builtInBridges.put( Integer.class.getName(), INTEGER );
		builtInBridges.put( int.class.getName(), INTEGER );
		builtInBridges.put( Long.class.getName(), LONG );
		builtInBridges.put( long.class.getName(), LONG );
		builtInBridges.put( BigInteger.class.getName(), BIG_INTEGER );
		builtInBridges.put( BigDecimal.class.getName(), BIG_DECIMAL );
		builtInBridges.put( String.class.getName(), STRING );
		builtInBridges.put( Boolean.class.getName(), BOOLEAN );
		builtInBridges.put( boolean.class.getName(), BOOLEAN );
		builtInBridges.put( Class.class.getName(), CLAZZ );
		builtInBridges.put( URL.class.getName(), Url );
		builtInBridges.put( URI.class.getName(), Uri );

		builtInBridges.put( Date.class.getName(), DATE_MILLISECOND );
	}

	/**
	 * This extracts and instantiates the implementation class from a ClassBridge
	 * annotation.
	 *
	 * @param cb the ClassBridge
	 * @return FieldBridge
	 */
	public static FieldBridge extractType(ClassBridge cb)
	{
		FieldBridge bridge = null;

		if ( cb != null ) {
			Class impl = cb.impl();
			//TODO better error information ( see guessType() )
			if (impl != null) {
				try {
					Object instance = impl.newInstance();
					if ( FieldBridge.class.isAssignableFrom( impl ) ) {
						bridge = (FieldBridge) instance;
					}
					else if ( org.hibernate.search.bridge.TwoWayStringBridge.class.isAssignableFrom( impl ) ) {
						bridge = new TwoWayString2FieldBridgeAdaptor(
								(org.hibernate.search.bridge.TwoWayStringBridge) instance );
					}
					else if ( org.hibernate.search.bridge.StringBridge.class.isAssignableFrom( impl ) ) {
						bridge = new String2FieldBridgeAdaptor( (org.hibernate.search.bridge.StringBridge) instance );
					}
					else {
						throw new SearchException("@ClassBridge implementation implements none of the field bridge interfaces: "
								+ impl );
					}
					if ( cb.params().length > 0 && ParameterizedBridge.class.isAssignableFrom( impl ) ) {
						Map<String, String> params = new HashMap<String, String>( cb.params().length );
						for ( Parameter param : cb.params() ) {
							params.put( param.name(), param.value() );
						}
						( (ParameterizedBridge) instance ).setParameterValues( params );
					}
				}
				catch (Exception e) {
					throw new HibernateException( "Unable to instantiate FieldBridge for " + ClassBridge.class.getName(), e );
				}
			}
		}
		if ( bridge == null ) throw new SearchException( "Unable to guess FieldBridge for " + ClassBridge.class.getName() );

		return bridge;
	}

	public static FieldBridge guessType(Field field, XMember member, ReflectionManager reflectionManager) {
		FieldBridge bridge;
		org.hibernate.search.annotations.FieldBridge bridgeAnn;
		//@Field bridge has priority over @FieldBridge
		if ( field != null && void.class != field.bridge().impl() ) {
			bridgeAnn = field.bridge();
		}
		else {
			bridgeAnn = member.getAnnotation( org.hibernate.search.annotations.FieldBridge.class );
		}
		final String memberName = member.getName();
		if ( bridgeAnn != null ) {
			bridge = doExtractType( bridgeAnn, memberName );
		}
		else if ( member.isAnnotationPresent( org.hibernate.search.annotations.DateBridge.class ) ) {
			Resolution resolution =
					member.getAnnotation( org.hibernate.search.annotations.DateBridge.class ).resolution();
			bridge = getDateField( resolution );
		}
		else {
			//find in built-ins
			XClass returnType = member.getType();
			bridge = builtInBridges.get( returnType.getName() );
			if ( bridge == null && returnType.isEnum() ) {
				bridge = new TwoWayString2FieldBridgeAdaptor(
						new EnumBridge( reflectionManager.toClass( returnType ) )
				);
			}
		}
		//TODO add classname
		if ( bridge == null ) throw new SearchException( "Unable to guess FieldBridge for " + memberName );
		return bridge;
	}

	/** assume not null bridgeAnn */
	private static FieldBridge doExtractType(org.hibernate.search.annotations.FieldBridge bridgeAnn, String memberName) {
		assert bridgeAnn != null : "doExtractType assume bridge instance not null";
		FieldBridge bridge;
		Class impl = bridgeAnn.impl();
		if (impl == void.class)
			throw new SearchException("@FieldBridge with no implementation class defined in: " + memberName );
		try {
			Object instance = impl.newInstance();
			if ( FieldBridge.class.isAssignableFrom( impl ) ) {
				bridge = (FieldBridge) instance;
			}
			else if ( TwoWayStringBridge.class.isAssignableFrom( impl ) ) {
				bridge = new TwoWayString2FieldBridgeAdaptor(
						( TwoWayStringBridge) instance );
			}
			else if ( org.hibernate.search.bridge.StringBridge.class.isAssignableFrom( impl ) ) {
				bridge = new String2FieldBridgeAdaptor( (org.hibernate.search.bridge.StringBridge) instance );
			}
			else {
				throw new SearchException("@FieldBridge implementation implements none of the field bridge interfaces: "
						+ impl + " in " + memberName
				);
			}
			if ( bridgeAnn.params().length > 0 && ParameterizedBridge.class.isAssignableFrom( impl ) ) {
				Map<String, String> params = new HashMap<String, String>( bridgeAnn.params().length );
				for ( Parameter param : bridgeAnn.params() ) {
					params.put( param.name(), param.value() );
				}
				( (ParameterizedBridge) instance ).setParameterValues( params );
			}
		}
		catch (Exception e) {
			//TODO add classname
			throw new SearchException( "Unable to instanciate FieldBridge for " + memberName, e );
		}
		return bridge;
	}

	public static FieldBridge getDateField(Resolution resolution) {
		switch (resolution) {
			case YEAR:
				return DATE_YEAR;
			case MONTH:
				return DATE_MONTH;
			case DAY:
				return DATE_DAY;
			case HOUR:
				return DATE_HOUR;
			case MINUTE:
				return DATE_MINUTE;
			case SECOND:
				return DATE_SECOND;
			case MILLISECOND:
				return DATE_MILLISECOND;
			default:
				throw new AssertionFailure( "Unknown Resolution: " + resolution );
		}
	}

	/**
	 * Takes in a fieldBridge and will return you a TwoWayFieldBridge instance.
	 *
	 * @param fieldBridge
	 *
	 * @return a TwoWayFieldBridge instance if the Field Bridge is an instance of a TwoWayFieldBridge.
	 *
	 * @throws SearchException if the FieldBridge passed in is not an instance of a TwoWayFieldBridge.
	 */

	public static TwoWayFieldBridge extractTwoWayType(org.hibernate.search.annotations.FieldBridge fieldBridge) {
		FieldBridge fb = extractType( fieldBridge );
		if ( fb instanceof TwoWayFieldBridge ) {
			return ( TwoWayFieldBridge ) fb;
		}
		else {
			throw new SearchException( "FieldBridge passed in is not an instance of " + TwoWayFieldBridge.class.getSimpleName() );
		}
	}

	/**
	 * This extracts and instantiates the implementation class from a ClassBridge
	 * annotation.
	 *
	 * @param fieldBridgeAnnotation the FieldBridge annotation
	 *
	 * @return FieldBridge
	 */
	public static FieldBridge extractType(org.hibernate.search.annotations.FieldBridge fieldBridgeAnnotation) {
		FieldBridge bridge = null;

		if ( fieldBridgeAnnotation != null ) {
			bridge = doExtractType( fieldBridgeAnnotation, null );
		}

		if ( bridge == null ) {
			throw new SearchException(
					"Unable to guess FieldBridge for " + org.hibernate.search.annotations.FieldBridge.class.getName()
			);
		}

		return bridge;
	}


}
