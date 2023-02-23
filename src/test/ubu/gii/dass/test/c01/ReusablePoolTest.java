/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Claudia Landeira 
 * @author Jonas Martinez 
 * @author Alvaro Hoyuelos 
 *
 */
public class ReusablePoolTest {
	
	ReusablePool p1, p2 = null;
	Reusable r1, r2, r3 = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		p1 = ReusablePool.getInstance();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		assertNotNull(p1);
		assertTrue(p1 instanceof ReusablePool);
		p2 = ReusablePool.getInstance();
		assertEquals(p1,p2);
		
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 */
	@Test
	public void testAcquireReusable() {

		// Comprobamos que r1 y r2 son nulos
		assertNull(this.r1);
		assertNull(this.r2);
		
		// Tratamos de adquirir dos recursos nuevos del pool
		try {
			this.r1 = this.p1.acquireReusable();
			this.r2 = this.p1.acquireReusable();
			assertNotNull(this.r1);
			assertNotNull(this.r2);
		} catch (NotFreeInstanceException e) {
			fail("Ha saltado la excepcion NotFreeInstanceException.");
		}
		
		// Tratamos de adquirir un recurso nuevo del pool que ya esta vacio
		try {
			this.r3 = this.p1.acquireReusable();
			fail("r3 no deberia adquirir un nuevo recurso del pool al este estar vacio.");
		}
		catch(NotFreeInstanceException e){
			assertNull(this.r3);
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 */
	@Test
	public void testReleaseReusable() {
		try {
			this.r1 = this.p1.acquireReusable();
			this.r2 = this.p1.acquireReusable();
			
		} catch (NotFreeInstanceException e) {
			fail("Excepcion por intentar liberar una instancia reusable.");
		}
		
		try {
			this.p1.releaseReusable(this.r1);	
			this.p1.releaseReusable(this.r2);	
		} catch (DuplicatedInstanceException e) {
			fail("Excepcion por intenta liberar una instancia ya creada.");
		}
		
		try {
			this.p1.releaseReusable(this.r2);
			fail("No deberia poder hacer Release del mismo dos veces.");
		}
		catch(DuplicatedInstanceException e){
		}
	}
}
