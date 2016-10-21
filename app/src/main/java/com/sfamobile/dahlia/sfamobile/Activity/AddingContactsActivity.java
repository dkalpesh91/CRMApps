package com.sfamobile.dahlia.sfamobile.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sfamobile.dahlia.sfamobile.Adapter.ContactDocumentAdapter;
import com.sfamobile.dahlia.sfamobile.Adapter.ViewMultipleContactListAdapter;
import com.sfamobile.dahlia.sfamobile.Model.Catalog;
import com.sfamobile.dahlia.sfamobile.Model.ContactPersonModel;
import com.sfamobile.dahlia.sfamobile.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddingContactsActivity extends AppCompatActivity {
    ListView list = null;
    ExpandableListAdapter listAdapter;
    Catalog product = null;
    Catalog product1 = null;
//
    Uri mUri;

    EditText mEditName2 = null;
    EditText mEditRole2 = null;
    EditText mEditEmail2 = null;
    EditText mEditNumber2 = null;
    EditText mCountryCode = null;
    EditText mEditAddPhotos = null;
    TextView mNameSaving = null;
    TextView mRollSaving = null;
    TextView mEmailSaving = null;
    TextView mContactSaving = null;

    private Button mSave_button = null;
    private Button mDelete_button = null;
    private Button mEdit_button = null;
    ScrollView mScroll_button = null;

    TextView mActivityNameTV = null;
    ImageView mActivityBackIMV = null;
    ImageView mActivityAddIMV = null;
    ImageView mActivityAddImage = null;

    LinearLayout mAddContactLayout = null;
    LinearLayout mAddPhotoLayout = null;
    GridView mGridContactImages = null;

    Bitmap bitmap;

    private ContactDocumentAdapter contactDocumentAdapter;

    HashMap<String, String> imageUrl;


    private ArrayList<ContactPersonModel> contactlist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_contacts_2);
        mSave_button = (Button) findViewById(R.id.button_save);


        mActivityNameTV = (TextView) findViewById(R.id.screen_label_tv);
        mActivityNameTV.setText("Add Contact");
        mActivityBackIMV = (ImageView) findViewById(R.id.back_arrow_img);
        mActivityBackIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AddingContactsActivity.this, NewLeadActivity.class);
                startActivity(intent);
                finish();

            }
        });
        mActivityAddIMV = (ImageView) findViewById(R.id.add_item_img);
        mActivityAddImage = (ImageView) findViewById(R.id.image_share_photo);
        mAddContactLayout = (LinearLayout) findViewById(R.id.add_contact_layout);
        mAddPhotoLayout = (LinearLayout) findViewById(R.id.layout_add_photo);
        mGridContactImages = (GridView) findViewById(R.id.image_layout);

        mEditName2 = (EditText) findViewById(R.id.edit_Username2);
        mEditRole2 = (EditText) findViewById(R.id.edit_Role2);
        mEditEmail2 = (EditText) findViewById(R.id.edit_Email2);
        mEditNumber2 = (EditText) findViewById(R.id.edit_Number2);
        mCountryCode = (EditText) findViewById(R.id.code);
        mEditAddPhotos = (EditText) findViewById(R.id.edit_photos);
        list = (ListView) findViewById(R.id.expandablelist);

//        mEditName2.setVisibility(View.INVISIBLE);
//        mEditRole2.setVisibility(View.INVISIBLE);
//        mEditEmail2.setVisibility(View.INVISIBLE);
//        mEditNumber2.setVisibility(View.INVISIBLE);
//        mCountryCode.setVisibility(View.INVISIBLE);
//        mSave_button.setVisibility(View.INVISIBLE);

        getContactData();
        final ViewMultipleContactListAdapter adapter = new ViewMultipleContactListAdapter(this, contactlist);
        list.setAdapter(adapter);

        mActivityAddIMV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddContactLayout.getVisibility() == View.VISIBLE) {
                    mAddContactLayout.setVisibility(View.GONE);
                } else {
                    mAddContactLayout.setVisibility(View.VISIBLE);

                    mEditName2.setText("");
                    mEditRole2.setText("");
                    mEditEmail2.setText("");
                    mEditNumber2.setText("");
                    mCountryCode.setText("");
                    mEditAddPhotos.setText("");
                }

            }
        });
        mActivityAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        mSave_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String sFirstName = mEditName2.getText().toString();
                String sRole = mEditRole2.getText().toString();
                String sEmailId = mEditEmail2.getText().toString();
                String sPhoneNo = mEditNumber2.getText().toString();
                String sCountrycode = mCountryCode.getText().toString();

                if (!didValidateFields(sFirstName)) {
                    mEditName2.setError("username name should not be null");
                } else if (!didValidateFields(sEmailId)) {
                    mEditEmail2.setError("email should not be blank");
                } else if (!validateEmail(sEmailId)) {
                    mEditEmail2.setError("valid invalid mail Id");

                } else if (!didValidateFields(sPhoneNo)) {
                    mEditNumber2.setError("mobile number  should not be blank");
                } else if (!didValidateFields(sRole)) {
                    mEditRole2.setError("company name should not be blank");
                }
//                else if(!didValidateFields(sCountrycode))
//                {
//                    mCountryCode.setError("country code should not be blank");
//                }

                else {
                    mEditName2.setError(null);

                    mEditRole2.setError(null);
                    mEditEmail2.setError(null);
                    mEditNumber2.setError(null);

                    ContactPersonModel childPosition = new ContactPersonModel();
                    childPosition.setName(mEditName2.getText().toString());
                    childPosition.setRole(mEditRole2.getText().toString());
                    childPosition.setEmailId(mEditEmail2.getText().toString());
                    childPosition.setPhoneNumber(mEditNumber2.getText().toString());

                    contactlist.add(childPosition);

                    adapter.notifyDataSetChanged();

                    mAddContactLayout.setVisibility(View.GONE);
                    //  Toast.makeText(RegisterActivity.this, "Registered sucessfully", Toast.LENGTH_SHORT).show();
                }
            }


        });
        imageUrl = new HashMap<>();
        contactDocumentAdapter = new ContactDocumentAdapter(AddingContactsActivity.this, imageUrl);
        mGridContactImages.setAdapter(contactDocumentAdapter);


    }

    public boolean didValidateFields(String fields)

    {
        return fields.length() > 0;
    }

    public boolean validateEmail(String edtEmail) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher;
        matcher = pattern.matcher(edtEmail);
        return matcher.matches();
    }


    private void getContactData() {
        contactlist = new ArrayList<ContactPersonModel>();

        ContactPersonModel contactPersonModel = new ContactPersonModel();

        contactPersonModel.setName("Ravi V");
        contactPersonModel.setEmailId("ravi@gmail.com");
        contactPersonModel.setPhoneNumber("9292929292");
        contactPersonModel.setRole("CEO");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Naveen G");
        contactPersonModel.setEmailId("Naveen@gmail.com");
        contactPersonModel.setPhoneNumber("9696969696");
        contactPersonModel.setRole("Area Manager");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Rahul");
        contactPersonModel.setEmailId("rahul@gmail.com");
        contactPersonModel.setPhoneNumber("9898998989");
        contactPersonModel.setRole("Field Executive");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Viraj");
        contactPersonModel.setEmailId("viraj@yahoo.com");
        contactPersonModel.setPhoneNumber("7878787878");
        contactPersonModel.setRole("Sales Manager");
        contactlist.add(contactPersonModel);

        contactPersonModel.setName("Vishal");
        contactPersonModel.setEmailId("vishal@gmail.com");
        contactPersonModel.setPhoneNumber("7878787878");
        contactPersonModel.setRole("Field Executive");
        contactlist.add(contactPersonModel);


    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    mUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "imgnm_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, 1);
//
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        builder.show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String path = mUri.getPath();
                if (path.length() > 0) {
                    String filepath = path;
                    String filename = filepath.substring(filepath.lastIndexOf("/") + 1, filepath.length());
                    String filetype = ".jpg";
                    mEditAddPhotos.setVisibility(View.GONE);
                    mGridContactImages.setVisibility(View.VISIBLE);
                    imageUrl.put("Camera", mUri.toString());
                    contactDocumentAdapter.notifyDataSetChanged();

                    try {
                        //Getting the Bitmap from Gallery
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                        new UploadImage(bitmap);
                        //Setting the Bitmap to ImageView
                        // imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                File file = new File(filePath);
                String strFileName = file.getName();

                mEditAddPhotos.setVisibility(View.GONE);
                mGridContactImages.setVisibility(View.VISIBLE);
                imageUrl.put("Camera", selectedImage.toString());
                contactDocumentAdapter.notifyDataSetChanged();


                cursor.close();
            }
        }


    }

    private class UploadImage extends AsyncTask<Void, Void, Void> {
        Bitmap image;

        public UploadImage(Bitmap image) {
            this.image = image;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("image", encodedImage));

            HttpParams httpRequestParams = getHttpRequestParams();
            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost("http://manpreet.netau.net/SavePicture.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private HttpParams getHttpRequestParams() {

        HttpParams httpRequestParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpRequestParams, 1000 * 30);
        HttpConnectionParams.setSoTimeout(httpRequestParams, 1000 * 30);
        return httpRequestParams;

    }

}
