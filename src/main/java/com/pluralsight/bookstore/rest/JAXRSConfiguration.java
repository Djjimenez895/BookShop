package com.pluralsight.bookstore.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("api") // This means everything under api is a rest api endpoint
public class JAXRSConfiguration extends Application {
}
