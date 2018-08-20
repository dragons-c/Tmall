package ecjtu.mall.utils;

public class Page {
    private int start;//开始位置
    private  int count;//每页显示的数量
    private int total;//总共的数据
    private String param;//参数
    private static  final  int defaultCount = 5;
    public  Page(){
        count = defaultCount;
    }
    public Page(int start,int count){
            this();
            this.start = start;
            this.count = count;
    }
    //计算页数
    public int getTotalPage(){
        int totalPage ;
        if(total%count == 0){
            totalPage = total/count ;
        }else{
            totalPage = total/count + 1;
        }
        if(0 == totalPage){
            totalPage = 1;
        }
        return totalPage;
    }
    public int getLast(){
        int last;
        if(total%count == 0){
            last = total - count ;
        }else{
            last = total - total%count ;
        }
        last = last<0?0:last;
        return last;
    }
    public boolean isHasPreviouse(){
        if(start == 0){
            return false;
        }else{
            return true;
        }
    }
    public  boolean isHasNext(){
        if(getLast() == start){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                ", param='" + param + '\'' +
                '}';
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
