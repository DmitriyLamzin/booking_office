package com.github.dmitriylamzin.client.service;



import com.github.dmitriylamzin.service.TheatreBookerRemote;

import com.github.dmitriylamzin.service.TheatreBoxRemote;
import com.github.dmitriylamzin.service.TheatreInfoRemote;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Logger;


///**
// * Created by Dmitriy_Lamzin on 6/6/2017.
// */
public class EjbProducer {

    private final Context context;

    private Logger logger = Logger.getLogger(EjbProducer.class.getName());

    public EjbProducer() throws NamingException {
        this.context = new InitialContext();
    }


    @Produces
    @MyQualifier
    public TheatreInfoRemote getTheatreInfo() throws NamingException {
        TheatreInfoRemote lookup = (TheatreInfoRemote) context.lookup("java:global/booking_office_ear/booking_office_ejb/TheatreInfoService!com.github.dmitriylamzin.service.TheatreInfoRemote");
        logger.info(lookup.toString());
        return lookup;
    }

    @Produces
    public TheatreBookerRemote getTheatreBooker() throws NamingException {
        TheatreBookerRemote lookup = (TheatreBookerRemote) context.lookup("java:global/booking_office_ear/booking_office_ejb/TheatreBooker!com.github.dmitriylamzin.service.TheatreBookerRemote");
        logger.info(lookup.toString());
        return lookup;
    }

    @Produces
    public TheatreBoxRemote getTheatreBox() throws NamingException {
        TheatreBoxRemote lookup = (TheatreBoxRemote) context.lookup("java:global/booking_office_ear/booking_office_ejb/TheatreBox!com.github.dmitriylamzin.service.TheatreBoxRemote");
        logger.info(lookup.toString());
        return lookup;

    }
}


