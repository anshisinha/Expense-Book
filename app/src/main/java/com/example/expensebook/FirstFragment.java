package com.example.expensebook;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    final String SBI_REGULAR_EXPRESSION = "SBI";

    BankMessageLoader bankMessageLoader;

    private ArrayList<BankMessage> bankMessagesArray;

    private String bankName = "";

    Cursor cursor;
    private String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    ScrollView expensesScrollView;

    TextView expensesTextView;

    TextView monthHeading;

    Spinner monthSpinnerView;

    private String monthToDisplay;

    private Double totalAmountForCurrentMonth = Double.valueOf(0.0D);

    TextView totalAmountForCurrentMonthView;

    Typeface typeface;

    private void goThroughAllMessagesAndDisplay(String paramString) {
        String str = "";
        this.totalAmountForCurrentMonth = Double.valueOf(0.0D);
        this.expensesTextView.setText("");
        for (int b = 0; b < this.bankMessagesArray.size(); b++) {
            ((BankMessage)this.bankMessagesArray.get(b)).getMessageBody();
            String str1 = ((BankMessage)this.bankMessagesArray.get(b)).getMonthSent();
            String str2 = ((BankMessage)this.bankMessagesArray.get(b)).getYearSent();
            if ((new SimpleDateFormat("yyyy")).format(new Date()).equals(str2) && str1.equals(paramString)) {
                str1 = ((BankMessage)this.bankMessagesArray.get(b)).getDateSent();
                str2 = ((BankMessage)this.bankMessagesArray.get(b)).getPersonWithWhomTransactionWasDone();
                double d = ((BankMessage)this.bankMessagesArray.get(b)).getAmount();
                this.totalAmountForCurrentMonth = Double.valueOf(this.totalAmountForCurrentMonth.doubleValue() + d);
                this.expensesTextView.setTypeface(this.typeface);
                if (!str1.equals(str))
                    this.expensesTextView.append(str1 + "\n");
                NumberFormat numberFormat1 = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                this.expensesTextView.append("    " + numberFormat1.format(Double.valueOf(d)) + "   --   ");
                this.expensesTextView.append(str2);
                this.expensesTextView.append(" \n");
                String str3 = str1;
            }
        }
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        this.totalAmountForCurrentMonthView.setText(numberFormat.format(this.totalAmountForCurrentMonth));
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        return paramLayoutInflater.inflate(R.layout.fragment_first, paramViewGroup, false);
    }

    public void onItemSelected(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        this.monthToDisplay = this.monthSpinnerView.getSelectedItem().toString();
        this.monthHeading.setText("Expenditure in " + this.monthToDisplay + ":");
        goThroughAllMessagesAndDisplay(this.monthToDisplay);
    }

    public void onNothingSelected(AdapterView<?> paramAdapterView) {}

    public void onViewCreated(View paramView, Bundle paramBundle) {
        this.monthHeading = (TextView)paramView.findViewById(R.id.expenseLabelView);
        this.totalAmountForCurrentMonthView = (TextView)paramView.findViewById(R.id.total_ft_amount);
        this.expensesScrollView = (ScrollView)paramView.findViewById(R.id.expensesScrollView);
        this.expensesTextView = (TextView)paramView.findViewById(R.id.expensesTextView);
        this.typeface = ResourcesCompat.getFont(getContext(), R.font.going_to_school_font);
        this.bankMessageLoader = new BankMessageLoader(getContext(), "KOTAKB");
        this.bankMessagesArray = new ArrayList<>();
        this.bankMessagesArray = this.bankMessageLoader.loadMessages();
        this.monthSpinnerView = (Spinner)paramView.findViewById(R.id.monthSpinnerView);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource((Context)getActivity(), R.array.months_array,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.monthSpinnerView.setAdapter((SpinnerAdapter)arrayAdapter);
        this.monthSpinnerView.setOnItemSelectedListener(this);
        this.monthToDisplay = (new SimpleDateFormat("MMM")).format(new Date());
        this.monthSpinnerView.setSelection(Integer.parseInt((new SimpleDateFormat("MM")).format(new Date())) - 1);
        goThroughAllMessagesAndDisplay(this.monthToDisplay);
    }
}


/* Location:              C:\Users\Anshi sinha\Desktop\MAD BACKUP\dex-tools-2.1\classes3-dex2jar.jar!\com\example\madproject\FirstFragment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */