package app.eatit.appeatit;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class ConfirmarReservaActivity extends AppCompatActivity {

    private TextView date_hour;
    private Button btnDialogCancel, btnDialogSelect;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_reserva);

        dialog = new Dialog(ConfirmarReservaActivity.this);
        dialog.setContentView(R.layout.dialog_date);
        dialog.setTitle(R.string.title_dialog_date);
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        btnDialogCancel = (Button) dialog.findViewById(R.id.btnDialogCancel);
        btnDialogSelect = (Button) dialog.findViewById(R.id.btnDialogSelect);
        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnDialogSelect.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d("Log","Date Picker - "+datePicker.getDayOfMonth());
                date_hour.setText(datePicker.getDayOfMonth()+"-"+datePicker.getMonth()+"-"+datePicker.getYear());

                dialog.dismiss();
            }
        });

        date_hour = (TextView) findViewById(R.id.date_hour);
        date_hour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                dialog.show();
                return true;
            }
        });

    }
}
