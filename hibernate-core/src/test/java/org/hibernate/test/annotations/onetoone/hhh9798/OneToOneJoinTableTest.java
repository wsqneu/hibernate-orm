/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.test.annotations.onetoone.hhh9798;

import org.hibernate.Session;
import org.hibernate.Transaction;

import org.junit.Test;

import org.hibernate.testing.TestForIssue;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;

@TestForIssue(jiraKey = "HHH-9798")
public class OneToOneJoinTableTest extends BaseCoreFunctionalTestCase {

	@Test(expected = org.hibernate.exception.ConstraintViolationException.class)
	public void storeNonUniqueRelationship() throws Throwable {
		Session session = null;
		try {
			session = openSession();
			Transaction tx = session.beginTransaction();

			Item someItem = new Item( "Some Item" );
			session.save( someItem );

			Shipment shipment1 = new Shipment( someItem );
			session.save( shipment1 );

			Shipment shipment2 = new Shipment( someItem );
			session.save( shipment2 );

			tx.commit();
		}
		finally {
			if ( session != null ) {
				session.close();
			}
		}
	}

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
				Shipment.class,
				Item.class
		};
	}
}