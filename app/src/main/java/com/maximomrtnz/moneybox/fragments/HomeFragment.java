package com.maximomrtnz.moneybox.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.maximomrtnz.moneybox.R;
import com.maximomrtnz.moneybox.commons.DateUtils;
import com.maximomrtnz.moneybox.model.Movement;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends BaseFragment {

    private TextView mIncomes;
    private TextView mExpenses;
    private TextView mDate;
    private TextView mBalance;
    private PieChart mChart;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment(){
        super();
    }

    @Override
    protected void onMovementListChange() {
        mExpenses.setText(mAmountExpenses.toString());
        mIncomes.setText(mAmountIncomes.toString());
        mBalance.setText(String.valueOf(mAmountIncomes-mAmountExpenses));

        loadReportByCategories();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mIncomes = (TextView)rootView.findViewById(R.id.incomes);
        mExpenses = (TextView)rootView.findViewById(R.id.expenses);
        mBalance = (TextView)rootView.findViewById(R.id.balance);
        mDate = (TextView)rootView.findViewById(R.id.date);
        mChart = (PieChart) rootView.findViewById(R.id.pieChart);

        Long fromDate = DateUtils.getFirstDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH));

        Long toDate = DateUtils.getLastDate(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH));

        mDate.setText(MessageFormat.format("{0}, {1}", new String[]{DateUtils.getMonthName(getContext(),new GregorianCalendar()),String.valueOf(Calendar.getInstance().get(Calendar.YEAR))}));

        loadMovements(fromDate,toDate);

        return rootView;
    }

    public void loadReportByCategories(){

        Map<String,Float> amountByCategory = new HashMap<>();

        for(Movement movement : mMovements){

            if(movement.getType().toLowerCase().equals("expense")){

                if(!amountByCategory.containsKey(movement.getCategory())){
                    amountByCategory.put(movement.getCategory(),0f);
                }

                amountByCategory.put(movement.getCategory(),amountByCategory.get(movement.getCategory())+movement.getAmount().floatValue());

            }

        }

        ArrayList<Entry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        int i = 0;

        for(String category : amountByCategory.keySet()){
            yVals1.add(new Entry(amountByCategory.get(category), i));
            xVals.add(category);
            i++;
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");

        dataSet.setSliceSpace(amountByCategory.keySet().size());

        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(xVals, dataSet);

        data.setValueTextSize(11f);

        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);

        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

    }

}
