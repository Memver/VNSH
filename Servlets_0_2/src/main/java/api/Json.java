package api;

public class Json{
    private int time;
    private int[] values;

    public Json() {
    }

    public Json(int[] values) {
        this.values = values;
    }

    public Json(int time, int[] values) {
        this.time = time;
        this.values = values;
    }

    public int getTime() {
        return time;
    }
    public int[] getValues() {
        return values;
    }

    public void setTime(int time) {
        this.time = time;
    }
    public void setValues(int[] values) {
        this.values = values;
    }
}
