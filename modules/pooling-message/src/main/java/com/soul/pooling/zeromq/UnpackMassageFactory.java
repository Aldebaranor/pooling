package com.soul.pooling.zeromq;

import com.soul.pooling.facade.UnpackMessageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Song
 * @Date 2022/7/28 16:59
 */

@Data
@Service
public class UnpackMassageFactory {

    @Autowired
    private final Map<String, UnpackMessageService> map = new ConcurrentHashMap();


    public UnpackMessageService get(String code) {
        UnpackMessageService service = map.get(code);
        if (service == null) {
            throw new RuntimeException("未定义UnpackMessageService");
        }

        return service;
    }
}
