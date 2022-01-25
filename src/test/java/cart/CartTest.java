package cart;

import helper.JedisHelper;
import org.json.simple.JSONArray;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CartTest {
	private static final String TESTUSER = "12521";

	static JedisHelper helper;

	private Cart cart;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = JedisHelper.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.destroyPool();
	}

	@Before
	//1
	public void setUp() throws Exception {
		this.cart = new Cart(helper, TESTUSER);
		assertNotNull(this.cart);
	}

	@Test
	public void testAddProduct() {
		//2
		assertEquals("OK", this.cart.addProduct("151", "원두커피", 1));
		assertEquals("OK", this.cart.addProduct("156", "캔커피", 5));
	}

	@Test
	public void testGetProductList() {
		//3
		JSONArray products = this.cart.getProductList();
		//3
		assertNotNull(products);
		//4
		assertEquals(2, products.size());
	}

	@Test
	//5
	public void testDeleteProduct() {
		String[] products = {"151"};
		int result = this.cart.deleteProduct(products);
		assertEquals(1, result);
	}
	
//	@Test
//	public void testFlushCart() {
//		//6
//		assertTrue(this.cart.flushCart() > -1);
//		//7
//		assertTrue(this.cart.flushCartDeprecated() > -1);
//	}
}
