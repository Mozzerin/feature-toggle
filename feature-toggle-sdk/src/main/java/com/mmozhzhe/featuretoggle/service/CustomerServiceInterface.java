package com.mmozhzhe.featuretoggle.service;

import com.mmozhzhe.featuretoggle.exception.CustomerServiceException;
import com.mmozhzhe.featuretoggle.model.SearchRequestDto;
import com.mmozhzhe.featuretoggle.model.SearchResponseDto;

public interface CustomerServiceInterface {

    SearchResponseDto search(SearchRequestDto searchRequestDto) throws CustomerServiceException;

}
