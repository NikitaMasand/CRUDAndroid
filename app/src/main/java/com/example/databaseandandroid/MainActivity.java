package com.example.databaseandandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    EditText editName, editSurname, editMarks, editID;
    Button btnAddData, btnViewAll, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editID = (EditText) findViewById(R.id.editText_id);

        btnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_viewAll);
        btnUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        addData();
        viewAll();
        updateData();
        deleteData();


    }

    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      boolean isInserted =  myDb.insertData(
                                                            editName.getText().toString(),
                                                            editSurname.getText().toString(),
                                                            editMarks.getText().toString()
                                                            );
                      if(isInserted==true) {
                          Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                      }
                      else {
                          Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                      }
                    }


                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            //show message
                            showMessage("Error", "No data found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id: "+res.getString(0)+"\n");
                            buffer.append("Name: "+res.getString(1)+"\n");
                            buffer.append("Surname: "+res.getString(2)+"\n");
                            buffer.append("Marks: "+res.getString(3)+"\n\n");
                        }

                        //show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void updateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isUpdated = myDb.updateData(editID.getText().toString(),
                                        editName.getText().toString(),
                                        editSurname.getText().toString(),
                                        editMarks.getText().toString()
                                );
                       if(isUpdated==true){
                           Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                       }
                       else {
                           Toast.makeText(MainActivity.this, "Could not update data", Toast.LENGTH_LONG).show();
                       }
                    }
                }
        );
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int deletedRows = myDb.deleteData(editID.getText().toString());
                        if(deletedRows > 0 ) {
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                        }
                        else  {
                            Toast.makeText(MainActivity.this, "Could not delete data", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
