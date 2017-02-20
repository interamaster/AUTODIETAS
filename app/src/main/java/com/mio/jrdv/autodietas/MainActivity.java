package com.mio.jrdv.autodietas;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //vamos a ver la lista de calendars:

/*

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] projection = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.NAME,
                CalendarContract.Calendars.CALENDAR_COLOR
        };

        Cursor calendarCursor = managedQuery(uri, projection, null, null, null);

        Log.d("INFIO","calendars");

*/

  /*      final String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.CALENDAR_COLOR
        };

        final ContentResolver cr = getContentResolver();
        final Uri uri = CalendarContract.Calendars.CONTENT_URI;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Log.d("INFIO","calendars");
        Cursor cur = cr.query(uri, EVENT_PROJECTION, null, null, null);
        //final List<Calendar> result = Lists.newArrayList();




        while (cur.moveToNext()) {
        // do something with the cursor:
           Long id = cur.getLong(0);
           String name = cur.getString(1);
           int color = cur.getInt(2);


            Log.d("info","id="+id+" name: "+name+" color="+color);


            // D/info: id=1 name: My calendar color=-11749922
            //         id=2 name: Samsung Calendar color=-16759123
            //        id=3 name: mio from icloud color=-6644481

        //
        }
*/


        // grab the contentResolver to perform a query
        ContentResolver resolver = getContentResolver();

        /*
        // a list of which fields we want to retrieve for each calendar
        String[] EVENT_PROJECTION = new String[]{CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.OWNER_ACCOUNT};

            */

        //mejopr esto con color
        // a list of which fields we want to retrieve for each calendar
        final String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.CALENDAR_COLOR
        };
// the uri to query (provided by the Calendars class)
        Uri uri = CalendarContract.Calendars.CONTENT_URI;

// create a filter to retrieve calendars we want
// in this instance, we have an id of a calendar we want to find
        String selection = "(" + CalendarContract.Calendars._ID + " = ?)";
        String id="1";
        String[] selectionArgs = new String[]{id};

// resolve the query and get a cursor (similar to iterator in java.util) to collection of calendars
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = resolver.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);


          while (cursor.moveToNext()) {
        // do something with the cursor:
           Long idcalendar = cursor.getLong(0);
           String name = cursor.getString(1);
           int color = cursor.getInt(2);


            Log.d("info","id="+idcalendar+" name: "+name+" color="+color);

             //id=1 name: My calendar color=-11749922
        //
    }



        Log.d("INFIO","calendars");



        //ahora vamos a ver los eventos:

        VerEvevntosCalendarID( );


    }

    private void VerEvevntosCalendarID() {

        // determine which fields we want in our events, like before
        String[] EVENT_PROJECTION = new String[]{CalendarContract.Events.TITLE,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END,
                CalendarContract.Events.ALL_DAY};

// retrieve the ContentResolver
        ContentResolver resolver = getContentResolver();

// use the uri given by Instances, but in a way that we can add information to the URI
        Uri.Builder eventsUriBuilder = CalendarContract.Instances.CONTENT_URI.buildUpon();

// add the begin and end times to the URI to use these to limit the list to events between them
        ContentUris.appendId(eventsUriBuilder, beginTime);
        ContentUris.appendId(eventsUriBuilder, endTime);

// build the finished URI
        Uri eventUri = eventsUriBuilder.build();

// filter the selection, like before
        String selection = "((" + Events.CALENDAR_ID + " = ?))";
        String[] selectionArgs = new String[]{"" + cal.getCalId()};

// resolve the query, this time also including a sort option
        Cursor eventCursor = resolver.query(eventUri, EVENT_PROJECTION, selection, selectionArgs, CalendarContract.Instances.BEGIN + " ASC");





    }


}
