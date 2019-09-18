package seed.tools.restful;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class RestfulAntPageResult<T extends Object> {

    @JSONField(name = "message")
    private String message;

    @JSONField(name = "status")
    private int status = 200;


    @JSONField(name = "result")
    private Result result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public RestfulAntPageResult(int pageNo, int pageSize, int totalCount, List<T> data) {
        this.result = new Result<T>(pageNo,pageSize,totalCount,data);
    }


    static class  Result<T extends Object>{
        public Result(int pageNo,int pageSize,int totalCount, List<T> data) {
            this.data = data;
            this.pageNo = pageNo;
            this.pageSize = pageSize;
            this.totalCount = totalCount;
            this.totalPage = (int)totalCount/pageSize + 1;
        }
        @JSONField(name = "data")
        private List<T> data ;

        @JSONField(name = "pageSize")
        private int pageSize ;

        @JSONField(name = "pageNo")
        private int pageNo ;

        @JSONField(name = "totalPage")
        private int totalPage ;

        @JSONField(name = "totalCount")
        private int  totalCount;

        public List<T> getData() {
            return data;
        }

        public void setData(List<T> data) {
            this.data = data;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }
    }
}