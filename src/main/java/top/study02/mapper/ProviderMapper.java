package top.study02.mapper;

import top.study02.pojo.Provider;

import java.util.List;

public interface ProviderMapper {

    List<Provider> getAllProviders();

    int deleteProviderByPrimaryKey(int id);

    Provider getProviderByPrimaryKey(int id);

    int upDateProviderByPrimaryKey(Provider provider);

    int addProvider(Provider provider);

    Provider getProviderByProId(Provider provider);

    Provider getProviderByProviderName(Provider provider);

    List<Provider> getProviderInBills();
}
