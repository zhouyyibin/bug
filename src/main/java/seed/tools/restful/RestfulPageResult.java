package seed.tools.restful;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

public class RestfulPageResult<T extends Object> {

    @JSONField(name = "count")
    private long count;

    @JSONField(name = "list")
    private List<T> list = new ArrayList<T>();

    public RestfulPageResult(int page,long count, List<T> list) {
        this.count = count;
        this.list = list;
    }

    /**
     * 获取记录的分页总数。
     * @return 分页总数
     */
    public long getCount() {
        return count;
    }

    /**
     * 获取记录列表。
     * @return 记录列表
     */
    public List<T> getList() {
        return list;
    }
}