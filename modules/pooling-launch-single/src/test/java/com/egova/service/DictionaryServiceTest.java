package com.egova.service;

import com.egova.BootstrapTest;
import com.egova.entity.DictionaryGroup;
import com.egova.generic.service.DictionaryService;
import com.egova.json.utils.JsonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 奔波儿灞
 * @since 1.0.0
 */
public class DictionaryServiceTest extends BootstrapTest {

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    public void group() {
        DictionaryGroup group = JsonUtils.deserialize("{\"type\":\"add\",\"name\":\"测试\",\"id\":\"1\"}", DictionaryGroup.class);
        String result = dictionaryService.insertGroup(group);
        System.out.println(result);
    }

    @Test
    public void groupTypeTree() {
        List<DictionaryGroup> tree = dictionaryService.groupTypeTree();
        System.out.println(tree);
    }

}
