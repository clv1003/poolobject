/**
 * 
 */
package ubu.gii.dass.test.c01;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ubu.gii.dass.c01.Client;
import ubu.gii.dass.c01.DuplicatedInstanceException;
import ubu.gii.dass.c01.NotFreeInstanceException;
import ubu.gii.dass.c01.Reusable;
import ubu.gii.dass.c01.ReusablePool;

/**
 * @author Claudia Landeira 
 * @author Jonas Martinez 
 */
public class ReusablePoolTest {
	
	ReusablePool p1, p2 = null;
	Reusable r1, r2, r3 = null;
	Client cliente = new Client();
	
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
		p1 = null;
		p2 = null;
		r1 = null;
		r2 = null;
		r3 = null;
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		//Comprobamos que p1 no es null
		assertNotNull(p1);
		//Comprobamos si p1 es de clase ReusablePool
		assertTrue(p1 instanceof ReusablePool);
		//Guardamos en p2 la instancia y comprobamos si p1 y p2 son iguales
		p2 = ReusablePool.getInstance();
		assertEquals(p1,p2);
		
		System.out.println("[Test Get Instance] : OK");
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#acquireReusable()}.
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testAcquireReusable() throws NotFreeInstanceException {
		r1 = null;
		r2 = null;
		r3 = null;

		// Comprobamos que r1, r2 y r3 son nulos
		assertNull(this.r1);
		assertNull(this.r2);
		assertNull(this.r3);

		
		// Tratamos de adquirir dos recursos nuevos del pool y que ambos tiene valor no null y ser Reusable
		try {
			this.r1 = this.p1.acquireReusable(); 
			this.r2 = this.p1.acquireReusable(); 
			
			assertNotNull(this.r1);
			assertNotNull(this.r2);
			assertTrue(r1 instanceof Reusable);
			assertTrue(r2 instanceof Reusable);
			
			System.out.println("[Test Acquire Reusable 1] : OK");
			
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
			
			System.out.println("[Test Acquire Reusable 2] : OK");
		}
	}

	/**
	 * Test method for {@link ubu.gii.dass.c01.ReusablePool#releaseReusable(ubu.gii.dass.c01.Reusable)}.
	 * @throws DuplicatedInstanceException
	 * @throws NotFreeInstanceException
	 */
	@Test
	public void testReleaseReusable() throws DuplicatedInstanceException, NotFreeInstanceException {
		r1 = null;
		r2 = null;

		// Comprobamos que r1 y r2 son nulos
		assertNull(this.r1);
		assertNull(this.r2);

		try {
			this.r1 = this.p1.acquireReusable(); //recogemos valor
			String hr1 = r1.util(); //guardamos su valor hash
			assertNotNull(this.r1); //comprobamos que no esta vacio
			assertTrue(r1 instanceof Reusable); //comprobamos que es un objeto Reusable
			
			this.p1.releaseReusable(r1); //liberamos
			
			this.r2 = this.p1.acquireReusable(); //adquirimos valor
			String hr2 = r2.util(); //guardamos su valor hash
			assertNotNull(this.r2); //comprobamos que no esta vacio
			assertTrue(r2 instanceof Reusable); //comprobamos que es un objeto Reusable
			
			assertEquals(hr1, hr2); //comprobamos que sean iguales
			
			this.p1.releaseReusable(r2); //liberamos
			
			System.out.println("[Test Reslease Reusable 1] : OK");
			
		} catch (NotFreeInstanceException e) {
			fail();
		}
		
		//Probamos a liberar un Reusable ya liberados
		try {
			this.p1.releaseReusable(this.r2); //intentamos liberar
			fail("No se puede liberar un Reusable ya liberado");
		}
		catch(DuplicatedInstanceException e){
			System.out.println("[Test Reslease Reusable 2] : OK");
		}
	}
	
	/**
	 * Test method for {@link ubu.gii.dass.c01.Client#main(ubu.gii.dass.c01.Client)}.
	 * @throws DuplicatedInstanceException 
	 * @throws NotFreeInstanceException 
	 */
	// HACE QUE ROMPAN TODOS LOS DEMAS TEST
	/*
	@Test
	public void testClient() throws NotFreeInstanceException, DuplicatedInstanceException {
		//Client cliente = new Client(); //creamos un nuevo cliente
		
		assertNotNull(cliente); //comprobamos que no este vacio
		assertTrue(cliente instanceof Client); //comprobamos que es un Client
		Client.main(null);
		
	}*/
}
