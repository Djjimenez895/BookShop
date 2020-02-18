package com.pluralsight.bookstore.rest;

import com.pluralsight.bookstore.model.Book;
import com.pluralsight.bookstore.model.Language;
import com.pluralsight.bookstore.repository.BookRepository;
import com.pluralsight.bookstore.util.IsbnGenerator;
import com.pluralsight.bookstore.util.NumberGenerator;
import com.pluralsight.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class BookEndPointTest {
    @Deployment(testable = false)
    public static Archive createDeploymentPackage() {
        return ShrinkWrap.create(WebArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(TextUtil.class)
                .addClass(Language.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addClass(BookEndPoint.class)
                .addClass(JAXRSConfiguration.class)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml");
    }

    @Test
    public void createBook(@ArquillianResteasyResource("api/books") WebTarget webTarget) {
        // Test counting books
        Response response = webTarget.path("count").request().get();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Test find all
        response = webTarget.path("").request().get();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Creates a book
        Book book = new Book("isbn", "a title", 12F, 123, Language.ENGLISH, new Date(), "http://blahblah", "description");
        response = webTarget.request(MediaType.APPLICATION_JSON).post(Entity.entity(book, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }
}
