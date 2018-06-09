package lk.ijse.mountCalvary.controller;

public class Month {
    static Month[] months;
    private int value;

    public Month(int value) {
        this.value = value;
    }

    public static Month[] getAllMonth(){
        if(months == null){
            final Month[] month_local = new Month[12];
            for (int i = 1; i <= 12; i++){
                month_local[i-1] = new Month(i);
            }
            months = month_local;
        }
        return months;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        switch (value) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            case -1:
                return "All";
            default:
                return "ERROR";

        }
    }
}
