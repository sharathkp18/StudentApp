package kps.example.studentapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ViewData extends AppCompatActivity {
    LinearLayout usersList,scrollOneView;
    View customLayout;
    AlertDialog dialog;
    sqliteDB dbObj;
    TextView studName;
    boolean wantToCloseDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            dbObj = new sqliteDB(this);
            usersList = findViewById(R.id.listLayout);
            Cursor response = dbObj.getAllStudent();
            if (response.moveToFirst()) {
                while (!response.isAfterLast()){
                    onAddField(response);
                response.moveToNext();}
            }else{
                Toast.makeText(ViewData.this, "No student are registered", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
}
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewData.this,Main.class));
    }

    public void onAddField(Cursor response) {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.student_tag, null);
            TextView studName = rowView.findViewById(R.id.studName);
            TextView studInfo = rowView.findViewById(R.id.studInfo);
            ImageView studImg = rowView.findViewById(R.id.studImg);
            ImageButton goArrow = rowView.findViewById(R.id.goArrow);
                 studName.setText(response.getString(1));
                studInfo.setText(response.getString(3)+" - "+response.getString(4)+" / "
                                 +response.getString(5));
                studImg.setImageBitmap(getImage(response.getBlob(2)));
                usersList.addView(rowView, usersList.getChildCount());
                goArrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ViewData.this, "Function not implemented", Toast.LENGTH_SHORT).show();
                       // showAlertDialogForAddTravelDetail("User Information", email.getText().toString());
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
    /*public void showAlertDialogForAddTravelDetail(String title, final String email) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.yourDialog);
            builder.setTitle(title);
            wantToCloseDialog=false;
            customLayout = getLayoutInflater().inflate(R.layout.appro_user_one, null);
            final TextView userView = customLayout.findViewById(R.id.userNameView);
            final TextView emailView = customLayout.findViewById(R.id.emailView);
            final TextView addressView = customLayout.findViewById(R.id.addressView);
            final TextView phoneView = customLayout.findViewById(R.id.phonrNoView);
            final TextView dobView = customLayout.findViewById(R.id.dobView);
            final TextView passwordView = customLayout.findViewById(R.id.passwordView);
            final TextView roleView = customLayout.findViewById(R.id.roleView);
            final TextView countryView = customLayout.findViewById(R.id.countryView);
            final TextView idView = customLayout.findViewById(R.id.countryView);

            final TextView userEdit = customLayout.findViewById(R.id.userNameEdit);
            final TextView emailEdit = customLayout.findViewById(R.id.emailEdit);
            final TextView addressEdit = customLayout.findViewById(R.id.addressEdit);
            final TextView phoneEdit = customLayout.findViewById(R.id.phoneNoEdit);
            final TextView dobEdit = customLayout.findViewById(R.id.dobEdit);
            final TextView passwordEdit = customLayout.findViewById(R.id.passwordEdit);
            final Spinner roleEdit = customLayout.findViewById(R.id.roleEdit);
            final Spinner countryEdit = customLayout.findViewById(R.id.countryEdit);
            final TextView idEdit = customLayout.findViewById(R.id.idEdit);

            ArrayList<String> location = retriveDropDown("Location", "DropDown_IMP", inventoryDatabase);
            ArrayAdapter<String> locationL = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, location);
            countryEdit.setPrompt(locationH);
            countryEdit.setAdapter(locationL);
            ArrayList<String> role = retriveDropDown("Role", "Role_Table",genericDatabase);
            ArrayAdapter<String> roleL = new ArrayAdapter<>(this, R.layout.spinner_style, R.id.textview, role);
            roleEdit.setPrompt("Role");
            roleEdit.setAdapter(roleL);

            ResultSet result = cc.sqlModule(selectOperation, "UserDetails", allColumns, "EmailID='" + email + "'",
                    genericDatabase);
            while (result.next()) {
                userEdit.setText(result.getString("FirstName")+" "+result.getString("LastName"));
                emailEdit.setText(result.getString("EmailID"));
                addressEdit.setText(result.getString("Address"));
                phoneEdit.setText(result.getString("PhoneNo"));
                dobEdit.setText(result.getString("DateOfBirth"));
                passwordEdit.setText(result.getString("Password"));
            }
            builder.setView(customLayout).setCancelable(false)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (idEdit.getText().toString().isEmpty() || roleEdit.getSelectedItem().toString().equals("-select-")
                                || countryEdit.getSelectedItem().toString().equals("-select-")){
                            String warning = "";
                            if (idEdit.getText().toString().isEmpty()) {
                                warning = "enter email";
                            }else if (roleEdit.getSelectedItem().toString().equals("-select-")) {
                                warning = "Choose Employee Role";
                            } else if (countryEdit.getSelectedItem().toString().equals("-select-")) {
                                warning = "Choose Country";
                            }
                            Toast.makeText(ApprovalUser.this, warning, Toast.LENGTH_SHORT).show();
                        } else {
                            String[] empolyeeDetailsColumns = {"EmployeeID", "FirstName", "LastName", "Address","EmailID","PhoneNo", "DateOfBirth","Country"};
                            String[] empolyeeDetailsValues = {idEdit.getText().toString(),
                                    firstWord(userEdit.getText().toString()),  lastWord(userEdit.getText().toString()),
                                    addressEdit.getText().toString(), emailEdit.getText().toString(),phoneEdit.getText().toString(),dobEdit.getText().toString(),
                                    countryEdit.getSelectedItem().toString()};
                            int result = cc.sqlModule(insertOperation, empoyeeDetailsTable,empolyeeDetailsColumns,null,empolyeeDetailsValues,genericDatabase);
                            if (result >= 1) {
                                Toast.makeText(ApprovalUser.this, "New User Approved", Toast.LENGTH_LONG).show();
                                String[] userDetailsCol={"Status"};
                                String[] userDetailsValues={"Approved"};
                                String[] loginCol={"EmployeeID","Employee_name","Password","Role"};
                                String[] loginValues={idEdit.getText().toString(),userEdit.getText().toString(),passwordEdit.getText().toString(),
                                        roleEdit.getSelectedItem().toString()};
                                cc.sqlModule(updateOperation,"UserDetails",userDetailsCol,"EmailID='"+email+"'",userDetailsValues,genericDatabase);
                                cc.sqlModule(insertOperation, loginTable,loginCol,null,loginValues,genericDatabase);
                                recreateActivity();
                                try {
                                    //Creating SendMail object
                                    sendEmail(emailEdit.getText().toString(),
                                            "Amp Services", "Welcome,Your Approved by Amp\nYou can login with ID:"+
                                                    idEdit.getText().toString()+"\n"+"Password"+":"+passwordEdit.getText().toString());
                                } catch (Exception e) {
                                    Log.e("Sending mail", e.getMessage(), e);
                                }
                            } else {
                                Toast.makeText(ApprovalUser.this, "New User Not Approved", Toast.LENGTH_LONG).show();
                            }
                            wantToCloseDialog = true;
                        }
                        if (wantToCloseDialog)
                            dialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/
}