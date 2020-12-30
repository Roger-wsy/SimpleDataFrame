package math;

import data.DataFrameReader;
import data.rowCol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Statistics {

    DataFrameReader r = new DataFrameReader();
    public static List<List<String>> COLUMNDATA = new ArrayList<>();
    rowCol rc = new rowCol();
    public static List<float[]> calculate = new ArrayList<>();

    public Statistics(){
        COLUMNDATA = rc.getCOLUMNDATA();
        calculate = changeToFloat();
    }


    public float[] variance() {
        float [] mean = mean();
        float [] variance = new float[mean.length];

        for(int start = 0; start<variance.length; start++){
            float [] temp = calculate.get(start);
            for(int j = 0; j<temp.length; j++) {
                variance[start] += Math.pow(temp[j] - mean[start], 2);
            }
            variance[start] /= temp.length;
        }
        return variance;
    }

    public float[] sd() {
        float [] variance = variance();
        float [] sd = new float[variance.length];
        for(int i = 0; i<sd.length; i++){
            sd[i] = (float) Math.sqrt(variance[i]);
        }
        return sd;
    }

    public float[] min() {
        int minIndex = 0;
        float [] min = new float[calculate.size()];
        for(int i = 0; i<calculate.size(); i++){
            float [] temp = calculate.get(i);
            for(int j = 0; j<temp.length; j++){
                if(temp[j]<temp[minIndex]) minIndex = j;
            }

            min[i] = temp[minIndex];
        }
        return min;
    }

    public float[] max() {
        int maxIndex = 0;
        float [] max = new float[calculate.size()];
        for(int i = 0; i<calculate.size(); i++){
            float [] temp = calculate.get(i);
            for(int j = 0; j<temp.length; j++){
                if(temp[j]>temp[maxIndex]) maxIndex = j;
            }

            max[i] = temp[maxIndex];
        }
        return max;
    }

    public float[] mean() {
        int size = calculate.size();
        float[] avg = new float[size];

        for(int j = 0; j<calculate.size(); j++){
            avg[j] = sum(calculate.get(j))/calculate.get(j).length;
        }
        return avg;
    }

    public float mode() {
        return 0;
    }

    public float[] median() {
        float [] medianArr = new float[calculate.size()];
        for(int i = 0; i<calculate.size(); i++){
            float [] temp = calculate.get(i);
            Arrays.sort(temp);
            if(temp.length%2 == 1){
                int median = (temp.length+1)/2 - 1;
                medianArr[i] = temp[median];
            }
            else{
                int median = temp.length/2 - 1;
                float mean = (temp[median] + temp[median+1])/2;
                medianArr[i] = mean;
            }
        }
        return medianArr;
    }

    public float[] range() {
        float[] max = max();
        float [] min = min();
        float [] range = new float[calculate.size()];

        for(int i = 0; i<range.length; i++){
            range[i] = max[i] - min[i];
        }

        return range;
    }

    public List<float[]> StandardScale(){
        float [] mean = mean();
        float [] sd = sd();
        List<float[]> sdScale = new ArrayList<>();

        int z = 0;
        for(int i = 0; i< calculate.size(); i++){
            float [] insert = new float[calculate.get(i).length];
            float[] temp = calculate.get(i);
            for(int j = 0; j<temp.length; j++){
                insert[j] = (temp[j] - mean[z]) / sd[z];
            }

            z++;
            sdScale.add(insert);
        }
        return sdScale;
    }

    public float[] minMaxScaling(){

        return new float[]{};
    }

    private boolean isFloat(String str){
        try{
            Float.parseFloat(str);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    private float sum(float [] numbers){
        float sum = 0;
        for(int first = 0; first<numbers.length; first++){
            sum+= numbers[first];
        }
        return sum;
    }

    private List<float[]> changeToFloat(){
        List<float[]> floatN = new ArrayList<>();

        for(int i = 0; i<COLUMNDATA.size(); i++) {
            List<String> temp = COLUMNDATA.get(i);
            float[] temp1 = new float[temp.size()];
            int j = 0;
            for (int start = 0; start < temp.size(); start++) {
                String temp2 = temp.get(start);
                if (isFloat(temp2)) {
                    temp1[j] = Float.parseFloat(temp2);
                    j++;
                }
            }
            try {
                floatN.add(temp1);
            } catch (NullPointerException e) {

            }

        }
        return floatN;
    }

}
