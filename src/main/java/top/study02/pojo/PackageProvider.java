package top.study02.pojo;

import java.util.List;

public class PackageProvider {
    private List<Provider> providers;
    private Integer totalPageNum;

    public PackageProvider() {
    }

    public PackageProvider(List<Provider> providers, Integer totalPageNum) {
        this.providers = providers;
        this.totalPageNum = totalPageNum;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }

    public Integer getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(Integer totalPageNum) {
        this.totalPageNum = totalPageNum;
    }
}
