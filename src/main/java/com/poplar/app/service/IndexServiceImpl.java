package com.poplar.app.service;

import com.poplar.server.appExecutor.anno.Service;

/**
 * Created by work on 15-8-21.
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Override
    public String getNameJson(){
        return "{\"id\":21,\"name\":\"fangrui\"}";
    }
}
