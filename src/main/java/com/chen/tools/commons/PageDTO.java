package com.chen.tools.commons;

import java.util.Objects;

/**
 * @author chao.zheng
 * @Description:页码页数
 * @date 2016年8月1日 下午4:29:38
 */
public class PageDTO extends BaseSerializable {

    /**
     *
     */
    private static final long serialVersionUID = 8577884917367061920L;
    private Integer pageNo = 1;
    private Integer pageSize = 10;

    public PageDTO() {
    }

    public PageDTO(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return Objects.nonNull(pageNo)?pageNo:1;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return Objects.nonNull(pageSize)?pageSize:10;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "PageDTO [pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
    }
}
