package com.example.doancalendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.doancalendar.MainActivity.MSSV;


public class DocDuLieuTKB extends AppCompatActivity {
    ListView lv;
 ArrayList<Thu> mang;
 Button btnTichHop, btnQuayLai;
 ProgressDialog dialog;
 Spinner spTuan;
 Spinner ChonHocki;
 public static String hocki="";
 public static final String[] SCOPES = {"https://googleapis.com/auth/calendar"};
 public GoogleAccountCredential credential;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        lv = (ListView) findViewById(R.id.LvLichHoc);
        mang = new ArrayList<Thu>();

       // credential = GoogleAccountCredential.usingOAuth2(
             //   getApplicationContext(), Arrays.asList(SCOPES))
              //  .setBackOff(new ExponentialBackOff());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               // dialog = ProgressDialog.show(DocDuLieuTKB.this, "", "Vui lòng chờ trong giây lát...", true);
               // new DocJson().execute("https://absorbing-pollen-diplodocus.glitch.me/?studentID="+MSSV+"&yearStudy=2019-2020&termID=HK02&week=1");
            }
        });
        initC();
        ChonHocki.setSelection(1);
        if (ChonHocki.getSelectedItem()=="Học kỳ 1"){
            hocki="HK01";
        }
        else if (ChonHocki.getSelectedItem()=="Học kỳ 2"){
            hocki="HK02";
        }
        else if (ChonHocki.getSelectedItem()=="Học kỳ 3"){
            hocki="HK03";
        }
    }

    class DocJson extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            return docnoidung(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray mangjson = new JSONArray(s);
                for(int i = 1; i<mangjson.length();i++){
                    JSONObject thu = mangjson.getJSONObject(i);
                    JSONObject txtSang = mangjson.getJSONObject(i);
                    JSONObject txtChieu = mangjson.getJSONObject(i);
                    JSONObject txtToi = mangjson.getJSONObject(i);
                    mang.add(new Thu(
                            thu.getString("0"),
                            txtSang.getString("Sáng"),
                            txtChieu.getString("Chiều"),
                            txtToi.getString("Tối")
                    ));
                }
                ListAdapter adapter = new ListAdapter(
                        getApplicationContext(),
                        R.layout.tuan,
                        mang
                );
                lv.setAdapter(adapter);
                //Toast.makeText(getApplicationContext()," "+mang.size(),Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.cancel();

            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }
    private static String docnoidung(String theUrl){
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
    public void initC(){
        btnQuayLai=findViewById(R.id.btnQuayLai);
       btnQuayLai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent it = new Intent(DocDuLieuTKB.this,MainActivity.class);
               startActivity(it);
               finish();
           }
       });
       btnTichHop = findViewById(R.id.btnTichHop);
       btnTichHop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //oneEvent();
             //  click("https://calendar.google.com");
           }
       });
       ChonHocki=findViewById(R.id.spHK);
        SpinnerHocki();
       ChonHocki.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerTuan();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       spTuan=findViewById(R.id.spTuan);
        spTuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dialog = ProgressDialog.show(DocDuLieuTKB.this, "", "Vui lòng chờ trong giây lát...", true);
                new DocJson().execute("https://absorbing-pollen-diplodocus.glitch.me/?studentID="+MSSV+"&yearStudy=2019-2020&termID="+ChonHocki.getSelectedItem()+"&week="+spTuan.getSelectedItem());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void SpinnerHocki() {

        ArrayList<String> arrayhk = new ArrayList<String>();
        arrayhk.add("HK01");
        arrayhk.add("HK02");
        arrayhk.add("HK03");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayhk);
        ChonHocki.setAdapter(arrayAdapter);

    }

    public void SpinnerTuan()
    {
        if (ChonHocki.getSelectedItem()=="HK01")
        {
            ArrayList<String> arrayTuan=new ArrayList<String>();
            arrayTuan.add("1");
            arrayTuan.add("2");
            arrayTuan.add("3");
            arrayTuan.add("4");
            arrayTuan.add("5");
            arrayTuan.add("6");
            arrayTuan.add("7");
            arrayTuan.add("8");
            arrayTuan.add("9");
            ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayTuan);
            spTuan.setAdapter(arrayAdapter);
        }
        if(ChonHocki.getSelectedItem()=="HK02") {
            ArrayList<String> arrayTuan=new ArrayList<String>();
            arrayTuan.add("15");
            arrayTuan.add("16");
            arrayTuan.add("17");
            arrayTuan.add("18");
            arrayTuan.add("19");
            arrayTuan.add("20");
            arrayTuan.add("21");
            arrayTuan.add("22");
            arrayTuan.add("23");
            ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayTuan);
            spTuan.setAdapter(arrayAdapter);
        }
        if(ChonHocki.getSelectedItem()=="HK03") {
            ArrayList<String> arrayTuan=new ArrayList<String>();
            arrayTuan.add("46");
            arrayTuan.add("47");
            arrayTuan.add("48");
            arrayTuan.add("49");
            arrayTuan.add("50");
            ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayTuan);
            spTuan.setAdapter(arrayAdapter);
        }
    }

   /* public static rx.Observable<String> AddNewEvent(final GoogleAccountCredential credential)
    {
        return Observable.defer(() -> {
            return Observable.just(createEvent(credential));
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
    public static String createEvent(GoogleAccountCredential mCredential) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Calendar service = new Calendar.Builder(transport, jsonFactory, mCredential)
                .setApplicationName("Calendar")
                .build();
        Event event = new Event()
                .setSummary("")
                .setLocation("KTX Đại Học Đà Lạt")
                .setDescription("");

        DateTime startDateTime = new DateTime("2020-06-07T07:00:00+07:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Ho_Chi_Minh");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2020-06-07T07:00:00+07:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Ho_Chi_Minh");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail("abir@aksdj.com"),
                new EventAttendee().setEmail("asdasd@andlk.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        try {
            event = service.events().insert(calendarId, event).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return event.getHtmlLink();
    }
    public void click(String url)  {
        AddNewEvent(credential);
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setData(Uri.parse(url));
        startActivity(it);
    }
    public void oneEvent(){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra("beginTime", cal.getTimeInMillis());
        intent.putExtra("allDay", true);
        intent.putExtra("rrule", "FREQ=YEARLY");
        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
        intent.putExtra("title", "Test Import 1 sự kiện");
        startActivity(intent);
    }*/
}
