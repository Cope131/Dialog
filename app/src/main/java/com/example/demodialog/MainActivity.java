package com.example.demodialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4,
            btnSetDate, btnSetTime, btnFormatDate, btnFormatTime,
            btnDateAdditions;
    TextView tvDate, tvTime;
    LinearLayout linearLayout;

    ArrayList<Integer> selectedItemsId;

    Calendar calendar = Calendar.getInstance();

    //Date
    String dateFormat = "dd/mm/yyyy";
    int curYear = calendar.get(Calendar.YEAR);
    int curMonth = calendar.get(Calendar.MONTH);
    int curDay = calendar.get(Calendar.DAY_OF_MONTH);
    int curDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

    //Time
    Boolean hourFormat24 = true;
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        tvDate = findViewById(R.id.textViewDate);
        tvTime = findViewById(R.id.textViewTime);
        btnSetDate = findViewById(R.id.buttonSetDate);
        btnSetTime = findViewById(R.id.buttonSetTime);
        btnFormatDate = findViewById(R.id.buttonFormatDate);
        btnFormatTime = findViewById(R.id.buttonFormatTime);
        btnDateAdditions = findViewById(R.id.buttonDateAdditions);
        linearLayout = findViewById(R.id.linearLayoutMain);

        //click exit
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        //click rate
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });

        //click report problem
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog2();
            }
        });

        //click change background color
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSingleChoiceList();
            }
        });

        //click set date
        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        //click set time
        btnSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();
            }
        });

        //click format date
        btnFormatDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showDialogDateFormatSelection();
            }
        });

        //click format hour
        btnFormatTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showDialogHourFormatSelection();
            }
        });

        //click Add Date Features
        btnDateAdditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showDialogAdditionalFeatures();
            }
        });

    }

    // ALERT DIALOG
    public void showDialog() {
        //=== create and set dialog builder ===
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);

        //=== set dialog characteristics
        adb.setTitle("Demo Dialog")
           .setMessage("Do you want to exit the app?")
           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            })
           .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(MainActivity.this, "Exiting App Cancelled", Toast.LENGTH_LONG).show();
                }
            });

        //=== create alert dialog ===
        AlertDialog ad = adb.create();
        //=== show dialog box ===
        ad.show();
    }

    // DIALOG WITH SINGLE CHOICE LIST
    public void showDialogSingleChoiceList() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Pick Background Color")
                .setItems(R.array.color_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LinearLayout mainLayout = findViewById(R.id.parent);
                        String color = "#FFFFFF"; //default - white
                        switch (i) {
                            case 0 :
                                color = "#E38690"; //red
                                break;
                            case 1:
                                color = "#7DD0B6"; //green
                                break;
                            case 2 :
                                color = "#9DC6D8"; //blue
                        }
                        linearLayout.setBackgroundColor(Color.parseColor(color));
                    }
                });
        AlertDialog ad = adb.create();
        ad.show();
    }

    // METHOD 1: CUSTOM DIALOG (CONTENT) - overriding the onCreateDialog
    public void showCustomDialog() {
        CustomDialog cd = new CustomDialog();
        //show dialog box
        cd.show(getSupportFragmentManager(), "Rating");
    }

    // METHOD 2: CUSTOM DIALOG (All)
    public void showCustomDialog2() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);

        View view = getLayoutInflater().inflate(R.layout.layout_dialog2, null);
        adb.setView(view);

        //bind to get input
        final EditText etProblem = view.findViewById(R.id.editTextProblem);
        //bind to set click listener
        Button btnCancel = view.findViewById(R.id.buttonCancel);
        Button btnSubmit = view.findViewById(R.id.buttonSubmit);

        final AlertDialog ad = adb.create();
        ad.show();

        //check empty input
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(etProblem.getText().toString().trim().equals(""))) {
                    Toast.makeText(MainActivity.this, "Report Submitted",
                            Toast.LENGTH_SHORT).show();
                    //exits dialog
                    ad.dismiss();
                } else
                    Toast.makeText(MainActivity.this, "Please fill in the description box",
                            Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //exits dialog
                ad.dismiss();
            }
        });
    }

    // - DATE -
    // DATE PICKER DIALOG
    public void showDatePickerDialog() {

        //=== Initialize Date Picker Dialog with current date & subsequently with users input ===
        DatePickerDialog dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                 Calendar calendar = Calendar.getInstance();
                 calendar.set(year, monthOfYear, dayOfMonth);
                 curYear = calendar.get(Calendar.YEAR);
                 curMonth = calendar.get(Calendar.MONTH);
                 curDay = calendar.get(Calendar.DAY_OF_MONTH);
                 curDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                 updateDate();
            }
        }, curYear, curMonth, curDay);

        //=== display date picker dialog ===
        dpd.show();
    }

    // DIALOG WITH SINGLE CHOICE LIST (DATE FORMAT)
    public void showDialogDateFormatSelection() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Select Date Format")
                .setItems(R.array.date_format_array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i) {
                            case 0 :
                                dateFormat = "dd/mm/yyyy";
                                break;
                            case 1 :
                                dateFormat = "mm/dd/yyyy";
                                break;
                            case 2 :
                                dateFormat = "yyyy/mm/dd";
                        }
                        updateDate();
                    }
                });
        AlertDialog ad = adb.create();
        ad.show();
    }

    // DIALOG WITH MULTIPLE CHOICE LIST (CHECKBOX)
    public void showDialogAdditionalFeatures() {
        selectedItemsId = new ArrayList<>(); //empty every time dialog is showed
        final AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Select Date Features")
                .setMultiChoiceItems(R.array.date_add_array, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isCheck) {
                                if (isCheck)
                                    selectedItemsId.add(i); //add selected item (checked)
                                else if (selectedItemsId.contains(i))
                                    selectedItemsId.remove(Integer.valueOf(i)); //remove selected item (unchecked)
                            }
                        })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        updateDate();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog ad = adb.create();
        ad.show();
    }

    // UPDATE DATE FORMAT AND VALUES
    public void updateDate() {
        String dateAdds = (selectedItemsId.contains(0)) ?
                "\n" + dayOfWeek(curDayOfWeek)  : "";
        if (dateFormat.equals("dd/mm/yyyy"))
            tvDate.setText(curDay + "/" + (curMonth + 1) + "/" + curYear + dateAdds);
        else if (dateFormat.equals("mm/dd/yyyy"))
            tvDate.setText((curMonth + 1) + "/" + curDay + "/" + curYear + dateAdds);
        else
            tvDate.setText(curYear + "/" + (curMonth + 1) + "/" + curDay + dateAdds);
    }

    public String dayOfWeek(int day) {
        String dayOfWeek = "";
        switch(day) {
            case 1 : dayOfWeek = "Sunday"; break;
            case 2 : dayOfWeek = "Monday"; break;
            case 3 : dayOfWeek = "Tuesday"; break;
            case 4 : dayOfWeek = "Wednesday"; break;
            case 5 : dayOfWeek = "Thursday"; break;
            case 6 : dayOfWeek = "Friday"; break;
            case 7 : dayOfWeek = "Saturday"; break;
        }
        return dayOfWeek;
    }

    // - TIME -
    // TIME PICKER DIALOG
    public void showTimePickerDialog() {
        TimePickerDialog tpd = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                hour = h;
                min = m;
                updateTime();
            }
        }, hour, min, hourFormat24);
        tpd.show();
    }

    // DIALOG WITH SINGLE CHOICE LIST (HOUR FORMAT)
    public void showDialogHourFormatSelection() {
        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
        adb.setTitle("Select Hour Format")
                .setItems(R.array.hour_format, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i) {
                            case 0 :
                                hourFormat24 = true;
                                break;
                            case 1 :
                                hourFormat24 = false;
                        }
                        updateTime();

                    }
                });
        AlertDialog ad = adb.create();
        ad.show();
    }

    // UPDATE TIME FORMAT
    public void updateTime() {
        int hour12 = (hour >= 12 && hour <=24) ? hour - 12 : hour;
        if (hourFormat24)
            tvTime.setText(String.format("%02d:%02d", hour, min));
        else
            tvTime.setText(String.format("%02d:%02d", hour12, min));
        Log.d("hour12 = ", hour12 + "");
        Log.d("hour = ", hour + "");
    }
}
