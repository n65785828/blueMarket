package top.study02.service;

import top.study02.pojo.PackageProvider;
import top.study02.pojo.Provider;

import java.util.List;

public interface ProviderService {
    PackageProvider getProvidersByPageNumAndPageSize(int pageNum, int pageSize);

    int deleteProviderById(int id);

    Provider getProviderByPrimaryKey(int id);

    int upDateProviderByPrimaryKey(Provider provider);

    int addProvider(Provider provider);

    Provider getProviderByProId(Provider provider);

    Provider getProviderByName(Provider provider);

    List<Provider> getProviderInBills();

    List<Provider> getAllProviders();
}
