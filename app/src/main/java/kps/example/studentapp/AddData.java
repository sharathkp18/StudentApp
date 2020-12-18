package kps.example.studentapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddData extends AppCompatActivity {
private ImageView  camera;
private CircleImageView image;
private Bitmap photo;

private static final int STORAGE_PERMISSION_CODE = 101;
private EditText studClass,section;
private TextInputEditText name,school,bloodroup,dob,fatherName,motherName,parentCon,
        address1,address2,city,state,zip,emergencyCon,location;
private RadioGroup gender;
private Button submit;
sqliteDB objDb;
LocationManager locationManager;
final int LOCATION_REQUEST=1;
String latitude,longitude,locationString;

    DatePickerDialog picker;
private Spinner classDrop,sectionDrop,bloodDrop;
    private static final int CAMERA_REQUEST = 1888;
     private static final int PLACE_PICKER_REQUEST_CODE = 1;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_add_data);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         try {
             //checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
             objDb = new sqliteDB(this);
             camera = findViewById(R.id.cam);
             image = findViewById(R.id.studImg);
             name = findViewById(R.id.name);
             studClass = findViewById(R.id.studClass);
             section = findViewById(R.id.section);
             school = findViewById(R.id.school);
             gender = findViewById(R.id.gender);
             bloodroup = findViewById(R.id.blood);
             dob = findViewById(R.id.dob);
             fatherName = findViewById(R.id.father);
             motherName = findViewById(R.id.mather);
             parentCon = findViewById(R.id.parContact);
             address1 = findViewById(R.id.add1);
             address2 = findViewById(R.id.add2);
             city = findViewById(R.id.city);
             state = findViewById(R.id.state);
             zip = findViewById(R.id.zip);
             emergencyCon = findViewById(R.id.emeContact);
             location = findViewById(R.id.location);

              submit = findViewById(R.id.submitBtn);

             cameraOnClick();
             onClassClick();
             onSectionClick();
             bloodGroups();
             dateOfBirth();
             submitData();
             getLocation();
         }catch (Exception e){
             e.printStackTrace();
         }
     }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddData.this,Main.class));
    }
     public void getLocation(){
          location.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
                  try {
                      startActivityForResult(builder.build(AddData.this),PLACE_PICKER_REQUEST_CODE);
                  } catch (GooglePlayServicesRepairableException e) {
                      e.printStackTrace();
                  } catch (GooglePlayServicesNotAvailableException e) {
                      e.printStackTrace();
                  }
              }
          });
     }
     public void submitData(){
          submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  try {
                      if (name.getText().toString().isEmpty() || school.getText().toString().isEmpty()||photo==null
                              || dob.getText().toString().isEmpty() || bloodroup.getText().toString().isEmpty()
                              || fatherName.getText().toString().isEmpty() || motherName.getText().toString().isEmpty()
                              || parentCon.getText().toString().isEmpty() || address1.getText().toString().isEmpty()
                              || city.getText().toString().isEmpty() || state.getText().toString().isEmpty()
                              || zip.getText().toString().isEmpty() || emergencyCon.getText().toString().isEmpty()
                              ||(gender.getCheckedRadioButtonId()==-1)||studClass.getText().toString().equals("--")
                              || section.getText().toString().equals("--") || dob.getText().toString().equals("--/--/--")
                              || location.getText().toString().equals("Click here to set location") ){
                          Toast.makeText(AddData.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                      } else if (objDb.addStudent(name.getText().toString(), getBytes(photo)
                              , studClass.getText().toString(), section.getText().toString(), school.getText().toString()
                              , getGender(), dob.getText().toString(), bloodroup.getText().toString(), fatherName.getText().toString()
                              , motherName.getText().toString(), parentCon.getText().toString(), address1.getText().toString()
                              , address2.getText().toString(), city.getText().toString(), state.getText().toString(), zip.getText().toString()
                              , emergencyCon.getText().toString(),locationString)) {
                          Toast.makeText(AddData.this, "Student data submitted", Toast.LENGTH_SHORT).show();
                          startActivity(new Intent(AddData.this, Main.class));
                      } else {
                          Toast.makeText(AddData.this, "Student data not submitted", Toast.LENGTH_SHORT).show();
                      }
                   }catch (Exception e){
                      e.printStackTrace();
                  }
              }
          });
     }
     public void dateOfBirth(){
         dob.setInputType(InputType.TYPE_NULL);
         dob.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final Calendar cal = Calendar.getInstance();
                 int day = cal.get(Calendar.DAY_OF_MONTH);
                 int month = cal.get(Calendar.MONTH);
                 int year = cal.get(Calendar.YEAR);
                 // date picker dialog
                 picker = new DatePickerDialog(AddData.this,
                         new DatePickerDialog.OnDateSetListener() {
                             @Override
                             public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                 dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                             }
                         }, year, month, day);
                 picker.show();
                 picker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
                 picker. getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
             }
         });
     }
     public void bloodGroups(){
         bloodroup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 bloodDrop=new Spinner(AddData.this,Spinner.MODE_DIALOG);
                 bloodDrop.setPrompt("Student Blood Group");
                 ArrayList<String> classList=new ArrayList<>();
                 classList.add("A+");
                 classList.add("B+");
                 classList.add("A-");
                 classList.add("B-");
                 classList.add("AB+");
                 classList.add("AB-");
                 ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddData.this,android.R.layout.simple_spinner_item,classList);
                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 bloodDrop.setAdapter(adapter);
                 bloodDrop.performClick();
                 bloodDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                         bloodroup.setText(bloodDrop.getSelectedItem().toString());
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> parent) {

                     }
                 });
                 bloodroup.setText(bloodDrop.getSelectedItem().toString());
             }
         });
     }
     public String getGender(){
         RadioButton rd=new RadioButton(this);
          try {
              int id = gender.getCheckedRadioButtonId();
              rd = findViewById(id);
          }catch (Exception e){
              e.printStackTrace();
          }
         return rd.getText().toString() + " ";
     }
     public void onClassClick(){
          studClass.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  classDrop=new Spinner(AddData.this,Spinner.MODE_DIALOG);
                  classDrop.setPrompt("Student Class");
                  ArrayList<String> classList=new ArrayList<>();
                  classList.add("1");
                  classList.add("2");
                  classList.add("3");
                  classList.add("4");
                  classList.add("5");
                  classList.add("6");
                  classList.add("7");
                  classList.add("8");
                  classList.add("9");
                  classList.add("10");
                  ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddData.this,android.R.layout.simple_spinner_item,classList);
                  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                  classDrop.setAdapter(adapter);
                  classDrop.performClick();
                  classDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                      @Override
                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                          studClass.setText(classDrop.getSelectedItem().toString());
                      }

                      @Override
                      public void onNothingSelected(AdapterView<?> parent) {

                      }
                  });
                  studClass.setText(classDrop.getSelectedItem().toString());
              }
          });
     }
     public void onSectionClick(){
         section.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 sectionDrop=new Spinner(AddData.this,Spinner.MODE_DIALOG);
                 sectionDrop.setPrompt("Student Section");
                 ArrayList<String> classList=new ArrayList<>();
                 classList.add("A");
                 classList.add("B");
                 classList.add("C");
                 classList.add("D");
                 classList.add("E");
                 classList.add("F");
                 ArrayAdapter<String> adapter=new ArrayAdapter<String>(AddData.this,android.R.layout.simple_spinner_item,classList);
                 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                 sectionDrop.setAdapter(adapter);
                 sectionDrop.performClick();
                 sectionDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                         section.setText(sectionDrop.getSelectedItem().toString());
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> parent) {

                     }
                 });
                 section.setText(sectionDrop.getSelectedItem().toString());
             }
         });
     }
     public void cameraOnClick(){
         camera.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 startActivityForResult(cameraIntent, CAMERA_REQUEST);
             }
         });
     }
    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if(requestCode==PLACE_PICKER_REQUEST_CODE){
             if(resultCode==RESULT_OK){
                 Place place=PlacePicker.getPlace(data,this);
                 latitude=String.valueOf(place.getLatLng().latitude);
                 longitude=String.valueOf(place.getLatLng().longitude);
                 locationString=latitude+" "+longitude;
                 location.setText("Your location is captured");
             }
         }
         if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
             photo = (Bitmap) data.getExtras().get("data");
             image.setImageBitmap(photo);
         }
     }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(AddData.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(AddData.this,
                    new String[] { permission },
                    requestCode);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(AddData.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(AddData.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}