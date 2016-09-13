package com.sfamobile.dahlia.sfamobile.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.sfamobile.dahlia.sfamobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ScanCardsActivity extends AppCompatActivity {

    ImageView mImageview = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_cards);

        mImageview = (ImageView) findViewById(R.id.m_imageview);


        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);

                    // viewImage.setImageBitmap(bitmap);

                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix"
                            + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);

                        TessBaseAPI tessBaseApi = new TessBaseAPI();
                        tessBaseApi.init(outFile.toString(), "eng");
                        tessBaseApi.setImage(bitmap);
                        String extractedText = tessBaseApi.getUTF8Text();
                        tessBaseApi.end();

                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                //Uri selectedImage = data.getData();
                 Bitmap poolBitmap = null;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                    poolBitmap = Bitmap.createScaledBitmap(bitmap, 160,
                            160,
                            false);


                } catch (Exception e) {
                    e.printStackTrace();
                }

//                ImageView imageView = (ImageView) findViewById(R.id.imgView);
//                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


               String datapath = "/mnt/sdcard/tesseract";


//                File dir = android.os.Environment.getExternalStorageDirectory();
//                if (!dir.exists()) {
//                    if (!dir.mkdirs()) {
//                        Log.e("", "ERROR: Creation of directory " + datapath + " failed, check does Android Manifest have permission to write to external storage.");
//                    }
//                } else {
//                    Log.i("", "Created directory " + datapath);
//                }


//                TessBaseAPI tessBaseApi = new TessBaseAPI();
//                tessBaseApi.init("/mnt/sdcard/tesseract/","eng");
//                tessBaseApi.setImage(poolBitmap);
//                String extractedText = tessBaseApi.getUTF8Text();
//                tessBaseApi.end();


                File dataPath = Environment.getDataDirectory();
                // or any other dir where you app has file write permissions

                File tessSubDir = new File(datapath);

                tessSubDir.mkdirs();

                TessBaseAPI tessBaseAPI = new TessBaseAPI();
                String DATA_PATH = Environment.getRootDirectory().getPath();

                tessBaseAPI.setDebug(true);
               // tessBaseAPI.init(tessSubDir.getAbsolutePath(), "eng"); //Init the Tess with the trained data file, with english language

                tessBaseAPI.setImage(poolBitmap);

                String text = tessBaseAPI.getUTF8Text();

                tessBaseAPI.end();

                mImageview.setImageBitmap(poolBitmap);

               // Log.w("image from gallery", picturePath + "");
                // viewImage.setImageBitmap(thumbnail);
            }
        }
    }

    private void getTextFromImage() {
        String idol_ocr_service = "https://api.havenondemand.com/1/api/async/ocrdocument/v1";

    }


//    private class DataLongOperationAsynchTask1 extends AsyncTask<String, Void, String[]> {
//        // ProgressDialog dialog = new ProgressDialog(this);
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // dialog.setMessage("Please wait...");
//            // dialog.setCanceledOnTouchOutside(false);
//            // dialog.show();
//        }
//
//        @Override
//        protected String[] doInBackground(String... params) {
//            String response;
//            try {
//                response = getLatLongByURL(params[0]);
//                Log.d("response", "" + response);
//                return new String[]{response};
//            } catch (Exception e) {
//                return new String[]{"error"};
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String... result) {
//            try {
//                JSONObject jsonObject = new JSONObject(result[0]);
//
//                double lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
//                        .getJSONObject("location").getDouble("lng");
//
//                double lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
//                        .getJSONObject("location").getDouble("lat");
//
//                Log.d("latitude", "" + lat);
//                Log.d("longitude", "" + lng);
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            // if (dialog.isShowing()) {
//            // dialog.dismiss();
//            // }
//        }
//    }
//
//
//    public void getLatLongByURL(String requestURL) {
////        URL url;
////        String response = "";
////        try {
////            url = new URL(requestURL);
////
////            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
////            conn.setReadTimeout(15000);
////            conn.setConnectTimeout(15000);
////            conn.setRequestMethod("GET");
////            conn.setDoInput(true);
////            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
////            conn.setDoOutput(true);
////            int responseCode = conn.getResponseCode();
////
////            if (responseCode == HttpsURLConnection.HTTP_OK) {
////                String line;
////                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////                while ((line = br.readLine()) != null) {
////                    response += line;
////                }
////            } else {
////                response = "";
////            }
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        return response;
//
//
//        String imagePath = "E:\\workspace1.5\\Test.jpg";
//        String xmlEtalonFileName = "englishStandarts";
//        String fontCollectionFileName = "arialAndTimesAndCourierRegular.xml.bin";
//        String resourcesFilePath = "E:\\workspace1.5\\resources.zip";
//
//        // Create an instance of OcrEngine class but providing required
//        // parameters
//        OcrEngine ocr = new OcrEngine(ResourcesSource.BINARY_ZIP_FILE,
//                resourcesFilePath, xmlEtalonFileName, fontCollectionFileName);
//        ocr.getConfig().setNeedRotationCorrection(false);
//        File image = new File(imagePath);
//        ocr.setImage(image);
//
//        // Add language
//        ILanguage language = Language.load("english");
//        ocr.getLanguages().addLanguage(language);
//
//        // Perform OCR and get extracted text
//        try {
//            if (ocr.process()) {
//                System.out.println("\ranswer -> " + ocr.getText());
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }






}
