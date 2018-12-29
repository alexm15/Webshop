/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.products;

import java.sql.SQLException;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alexander
 */
public class CatalogueTest
{

    private Catalogue catalogue;
    private FakeProductRepository fakeProductRepository;

    @Before
    public void setUp()
    {
        fakeProductRepository = new FakeProductRepository();
        fakeProductRepository.add(new Product(3, "Test3", "TestCat", true, true, true, "", "", "", "", "", 1.44));
        fakeProductRepository.add(new Product(4, "Test4", "TestCat", true, true, true, "", "", "", "", "", 1.44));
        fakeProductRepository.add(new Product(1, "Test1", "TestCat", true, true, true, "", "", "", "", "", 1.44));
        fakeProductRepository.add(new Product(2, "Test2", "TestCat", true, true, true, "", "", "", "", "", 1.44));

        catalogue = new Catalogue(fakeProductRepository);
    }

    @After
    public void tearDown()
    {
        fakeProductRepository = null;
        catalogue = null;
    }

    @Test
    public void testCreationOfCatalogueLoadsListOfProducts()
    {
        assertThat(catalogue.getProducts().size(), is(4));
    }

    @Test
    public void testLoadedProductsIsSorted()
    {
        assertThat(catalogue.getProducts().get(0).getName(), is("Test1"));
        assertThat(catalogue.getProducts().get(1).getName(), is("Test2"));
        assertThat(catalogue.getProducts().get(2).getName(), is("Test3"));
        assertThat(catalogue.getProducts().get(3).getName(), is("Test4"));
    }
    
    @Test
    public void testAddProduct()
    {
        catalogue.createProduct(5, "Added Product", "Added cat", true, true, true, "", "", "", "", "", 5.55);
        assertThat(catalogue.getProducts().get(4).getName(), is("Added Product"));
        assertThat(fakeProductRepository.products.get(4).getName(), is("Added Product"));
    }
    
    @Test
    public void testChangeProductDetails()
    {
        assertThat(fakeProductRepository.products.get(0).getName(), is("Test1"));
        catalogue.changeProductDetails(0, "UpdatedProduct", "", true, true, true, "", "", "", "", "", 12.22);
        assertThat(fakeProductRepository.products.get(0).getName(), is("UpdatedProduct"));
    }
    
    @Test
    public void testLoadsProductsFromTextFile()
    {
        Catalogue textFileCatalogue = new Catalogue(new FakeExceptionProductRepository());
        assertThat(textFileCatalogue.getProducts().size(), is(16));
    }
    
    @Test
    public void testLoadsProductsFromTextFileIsSorted()
    {
        Catalogue textFileCatalogue = new Catalogue(new FakeExceptionProductRepository());
        assertThat(textFileCatalogue.getProducts().get(0).getName(), is("FaldskÃ¦rms bukser"));
        assertThat(textFileCatalogue.getProducts().get(1).getName(), is("Farverig bluse"));
        assertThat(textFileCatalogue.getProducts().get(2).getName(), is("Farverige bukser"));
        assertThat(textFileCatalogue.getProducts().get(3).getName(), is("Flot kjole"));
        assertThat(textFileCatalogue.getProducts().get(4).getName(), is("Grim bluse"));
        assertThat(textFileCatalogue.getProducts().get(5).getName(), is("Grim kjole"));
    }

}
