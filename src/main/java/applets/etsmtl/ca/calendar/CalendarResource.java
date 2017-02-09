package applets.etsmtl.ca.calendar;


import applets.etsmtl.ca.calendar.model.CalendarEvent;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Path("calendar")
public class CalendarResource {

    private static final String CALENDAR_URL = "http://www.google.com/calendar/ical/etsmtl.net_shfr1g6kdra1dcjdl0orb6jico%40group.calendar.google.com/public/basic.ics";
    private SimpleDateFormat dateUrlParser = new SimpleDateFormat("yyyy-MM-dd");

    @GET
    @Path("{id}/{start_date}/{end_date}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CalendarEvent> getCalendarEvents(
            @PathParam("id") String id,
            @PathParam("start_date") String startDateParam,
            @PathParam("end_date") String endDateParam
    ) {

        List<CalendarEvent> calendarEvents = new ArrayList<>();

        //At the moment, there is only one source for the calendar
        if (!id.equals("ets"))
            return calendarEvents;

        try {
            Date startDate = dateUrlParser.parse(startDateParam);
            Date endDate = dateUrlParser.parse(endDateParam);

            calendarEvents = getCalendarEvents().stream()
                    .filter(calendarEvent ->
                            calendarEvent.getStartDate().after(startDate) &&
                                    calendarEvent.getStartDate().before(endDate))
                    .collect(Collectors.toList());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendarEvents;
    }

    private ArrayList<CalendarEvent> getCalendarEvents() {
        ArrayList<CalendarEvent> calendarEvents = new ArrayList<>();
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("calendar");

        //Return data from the cache, if available
        Element element = cache.get("calendar");
        if (element != null) {
            return (ArrayList<CalendarEvent>) element.getObjectValue();
        }

        InputStream stream;

        //Download and parse .ics and refresh the cache
        try {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(CALENDAR_URL)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();

            String redirect = response.header("Location");
            if (redirect != null) {
                request = new Request.Builder()
                        .url(redirect)
                        .get()
                        .build();
                response = client.newCall(request).execute();
            }

            stream = response.body().byteStream();

            CalendarBuilder builder = new CalendarBuilder();
            Calendar calendar = builder.build(stream);


            SimpleDateFormat dateParser = new SimpleDateFormat("yyyyMMdd");

            for (CalendarComponent calendarComponent : calendar.getComponents()) {
                Date dtstart = dateParser.parse(calendarComponent.getProperty("DTSTART").getValue());
                String uid = calendarComponent.getProperty("UID").getValue();
                Date dtend = dateParser.parse(calendarComponent.getProperty("DTEND").getValue());
                String summary = calendarComponent.getProperty("SUMMARY").getValue();

                calendarEvents.add(new CalendarEvent(dtstart, uid, dtend, summary));
            }

            cache.put(new Element("calendar", calendarEvents));

        } catch (ParserException | IOException | ParseException e) {
            e.printStackTrace();
        }

        return calendarEvents;
    }
}