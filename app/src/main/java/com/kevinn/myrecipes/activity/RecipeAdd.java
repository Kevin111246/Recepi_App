package com.kevinn.myrecipes.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.kevin.myrecipes.R;
import com.kevinn.myrecipes.fragment.RecipesFragment;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
//import cn.pedant.SweetAlert.SweetAlertDialog;

public class RecipeAdd extends AppCompatActivity {
    private ImageView complaintImage;
    private File file = new File("");
    private Button submitButton;
    private Button uploadButton;
//    private SweetAlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        complaintImage = (ImageView) findViewById(R.id.complaint_content_image);
        submitButton = (Button) findViewById(R.id.complaint_content_button);
        uploadButton = (Button) findViewById(R.id.complaint_content_issue);

        complaintImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onPickImage();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(RecipeAdd.this,RecipesFragment.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        JSONObject abc = new JSONObject();
        if(file.exists()){
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    String response = "";
                    try{
                                /*
                                session = new SessionManager(getApplicationContext());
                                HashMap<String, String> hm = session.getUserDetails();
                                Log.i("MyLogging",hm.get(SessionManager.KEY_NAME));
                                Log.i("MyLogging",hm.get(SessionManager.KEY_PASS));
                                */

                        FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                        String thePath = file.getAbsolutePath();
                        int bytesRead, bytesAvailable, bufferSize;
                        byte[] buffer;
                        int maxBufferSize = 1024 * 1024;
                        URL url_2 = new URL("https://dev.sighthoundapi.com/v1/");

                        HttpURLConnection c = (HttpURLConnection) url_2.openConnection();
                        c.setDoInput(true); // Allow Inputs
                        c.setDoOutput(true); // Allow Outputs
                        c.setUseCaches(false); // Don't use a Cached Copy
                        c.setRequestMethod("POST");
                        c.setRequestProperty("Connection", "Keep-Alive");
                        c.setRequestProperty("ENCTYPE", "multipart/form-data");
                        c.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "*****");

                        c.setRequestProperty("uploaded_file", thePath);

                        DataOutputStream dos = new DataOutputStream(c.getOutputStream());


                        ////////////////////////////////////////////////////////////////////
                        //post data allocation
                        // add parameters
                        dos.writeBytes("--" + "*****" + "\r\n");
                        dos.writeBytes("Content-Disposition: form-data; name=\"param\""       //key
                                + "\r\n");
                        dos.writeBytes("\r\n");

                        // assign value
                        dos.writeBytes(strings[0]);         //value
                        dos.writeBytes("\r\n");
                        dos.writeBytes("--" + "*****" + "\r\n");



                        // add parameters
                        dos.writeBytes("--" + "*****" + "\r\n");
                        dos.writeBytes("Content-Disposition: form-data; name=\"action\""
                                + "\r\n");
                        dos.writeBytes("\r\n");

                        // assign value
                        dos.writeBytes("\r\n");
                        dos.writeBytes("--" + "*****" + "\r\n");
                        ////////////////////////////////////////////////////////////


                        // send image
                        dos.writeBytes("--" + "*****" + "\r\n");
                        dos.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename='"
                                + thePath + "'" + "\r\n");

                        dos.writeBytes("\r\n");

                        // create a buffer of  maximum size
                        bytesAvailable = fileInputStream.available();

                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        buffer = new byte[bufferSize];

                        // read file and write it into form...
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                        while (bytesRead > 0) {
                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                        }

                        dos.writeBytes("\r\n");
                        dos.writeBytes("--" + "*****" + "--" + "\r\n");

                        int responseCode=c.getResponseCode();
                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br=new BufferedReader(new InputStreamReader(c.getInputStream()));
                            while ((line=br.readLine()) != null) {
                                response+=line;
                            }
                        }
                        else {
                            Log.i("InfoLog","Response Code = " + String.valueOf(responseCode));
                            response="";

                        }
                        Log.i("InfoLog","Upload Image Response = "+response);
                    } catch (MalformedURLException ex) {

                        ex.printStackTrace();

                        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                    }catch (IOException e) {
                        e.printStackTrace();
                        Log.i("Info Log","IOException!!!!!");
                    }catch(Exception e){
                        e.printStackTrace();
                        Log.i("Info Log","Exception");
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    runPostExecuteComplaint();

                }
            };//.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,abc.toString());
        }else{
            new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... strings) {
                    String response = "";
                    try {

                                /*
                                session = new SessionManager(getApplicationContext());
                                HashMap<String, String> hm = session.getUserDetails();
                                Log.i("MyLogging",hm.get(SessionManager.KEY_NAME));
                                Log.i("MyLogging",hm.get(SessionManager.KEY_PASS));
                                Log.i("MyLogging",id);
                                */

                        HashMap<String,String> postDataParams = new HashMap<>();


                        URL url = new URL("https://dev.sighthoundapi.com/v1/");

                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(50000);
                        conn.setConnectTimeout(50000);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        conn.setDoInput(true);
                        conn.setDoOutput(true);


                        OutputStream os = conn.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));
                        writer.write(getPostDataString(postDataParams));

                        writer.flush();
                        writer.close();
                        os.close();
                        int responseCode=conn.getResponseCode();

                        if (responseCode == HttpsURLConnection.HTTP_OK) {
                            String line;
                            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            while ((line=br.readLine()) != null) {
                                response+=line;
                            }
                        }
                        else {
                            response="";

                        }
                        Log.i("Info Log","Response = " + response);
                        conn.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    runPostExecuteComplaint();

                }
            };//.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,abc.toString());
        }


    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first){
                first = false;
            }
            else{
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    public void onPickImage()
    {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        Uri imageUri = Uri.fromFile(photo);
        complaintImage.setImageURI(imageUri);
        startActivityForResult(intent, 100);

    }

    private void runPostExecuteComplaint(){


//        submitButton.setEnabled(true);
//        if(loadingDialog != null){
////            loadingDialog.cancel();
//        }
//        new SweetAlertDialog(this,SweetAlertDialog.NORMAL_TYPE)
//                .setContentText("Complaint Ticket Submitted, We will proceed your complaint as soon as possible")
//                .setConfirmText("OK")
//                .show();

    }

    @Override
    public boolean onSupportNavigateUp() {                     // for the toolbar back
        //This method is called when the up button is pressed. Just the pop back stack.
        finish();
        return super.onSupportNavigateUp();
    }


}
