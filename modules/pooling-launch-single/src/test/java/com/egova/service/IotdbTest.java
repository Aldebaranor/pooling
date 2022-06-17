//package com.egova.service;
//
//import com.egova.BootstrapTest;
//import com.egova.iotdb.IotdbTemplate;
//import com.egova.iotdb.TSData;
//import com.egova.model.PageResult;
//import com.flagwind.persistent.model.CombineClause;
//import com.flagwind.persistent.model.Paging;
//import com.flagwind.persistent.model.SingleClause;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * iotdb测试
// *
// * @author 奔波儿灞
// * @since 1.0.0
// */
//@Slf4j
//public class IotdbTest extends BootstrapTest {
//
//    @Autowired
//    private IotdbTemplate iotdbTemplate;
//
//    /**
//     * 短语查询
//     */
//    @Test
//    public void selectClause() {
//        CombineClause clause = CombineClause.and(
//                SingleClause.equal("hardware", "keyboard"),
//                SingleClause.greaterThanEqual("temperature", 30.2f)
//        );
//        PageResult<TSData> result = iotdbTemplate.select("root.demo", clause, new Paging());
//        log.info("result: {}", result);
//    }
//
//}
