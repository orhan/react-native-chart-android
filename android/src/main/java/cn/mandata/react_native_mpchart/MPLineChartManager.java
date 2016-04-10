package cn.mandata.react_native_mpchart;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.graphics.Color;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2015/11/6.
 */
public class MPLineChartManager extends MPBarLineChartManager {
    private String CLASS_NAME="MPLineChart";
    private Random random;//用于产生随机数

    private LineChart chart;
    private LineData data;
    private LineDataSet dataSet;
    private ThemedReactContext mReactContext;

    @Override
    public String getName() {
        return this.CLASS_NAME;
    }

    @Override
    protected LineChart createViewInstance(ThemedReactContext reactContext) {
        LineChart chart=new LineChart(reactContext);
        mReactContext = reactContext;

        return  chart;
    }

    //{XValues:[],YValues:[{Data:[],Label:""},{}]}
    @ReactProp(name="data")
    public void setData(LineChart chart,ReadableMap rm){

        ReadableArray xArray=rm.getArray("xValues");
        ArrayList<String> xVals=new ArrayList<String>();
        for(int m=0;m<xArray.size();m++){
            xVals.add(xArray.getString(m));
        }
        ReadableArray ra=rm.getArray("yValues");
        LineData chartData=new LineData(xVals);
        for(int i=0;i<ra.size();i++){
            ReadableMap map=ra.getMap(i);
            ReadableArray data=map.getArray("data");
            String label=map.getString("label");
            float[] vals=new float[data.size()];
            ArrayList<Entry> entries=new ArrayList<Entry>();
            for (int j=0;j<data.size();j++){
                vals[j]=(float)data.getDouble(j);
                Entry be=new Entry((float)data.getDouble(j),j);
                entries.add(be);
            }
            /*BarEntry be=new BarEntry(vals,i);
            entries.add(be);*/
            ReadableMap config= map.getMap("config");
            LineDataSet dataSet=new LineDataSet(entries,label);
            if(config.hasKey("drawCircles")) dataSet.setDrawCircles(config.getBoolean("drawCircles"));
            if(config.hasKey("drawValues")) dataSet.setDrawValues(config.getBoolean("drawValues"));
            if(config.hasKey("drawCubic")) dataSet.setDrawCubic(config.getBoolean("drawCubic"));
            if(config.hasKey("circleRadius")) dataSet.setCircleRadius((float) config.getDouble("circleRadius"));
            if(config.hasKey("circleColor")) dataSet.setCircleColor(Color.parseColor(config.getString("color")));
            if(config.hasKey("lineWidth")) dataSet.setLineWidth((float) config.getDouble("lineWidth"));
            if(config.hasKey("valueFontName"))
                dataSet.setValueTypeface(Typeface.createFromAsset(mReactContext.getAssets(), "fonts/" + config.getString("valueFontName") + ".ttf"));
            if(config.hasKey("colors")){
                ReadableArray colorsArray = config.getArray("colors");
                ArrayList<Integer> colors = new ArrayList<>();
                for(int c = 0; c < colorsArray.size(); c++){
                    colors.add(Color.parseColor(colorsArray.getString(c)));
                }
                dataSet.setColors(colors);
            }else
            if(config.hasKey("color")) {
                int[] colors=new int[]{Color.parseColor(config.getString("color"))};
                dataSet.setColors(colors);
            }
            chartData.addDataSet(dataSet);
        }
        chart.setBackgroundColor(Color.WHITE);
        chart.setData(chartData);
        chart.invalidate();
    }
}
