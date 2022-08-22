package com.egova;

import com.egova.json.utils.JsonUtils;
import com.egova.redis.RedisUtils;
import com.soul.Bootstrap;
import com.soul.pooling.config.PoolingConfig;
import com.soul.pooling.mqtt.producer.MqttMsgProducer;
import com.soul.pooling.utils.ImagesUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.soul.pooling.task.RangeTask.SCENARIO_RANGE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Bootstrap.class})
public class BootstrapTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    PoolingConfig poolingConfig;

    @Autowired
    private MqttMsgProducer mqttMsgProducer;

    @Test
    public void crud() {
        String s = "String";
        RedisUtils.getService().opsForHash().put(SCENARIO_RANGE, String.valueOf(1), JsonUtils.serialize(s));
        RedisUtils.getService().opsForHash().put(SCENARIO_RANGE, String.valueOf(2), s);
        stringRedisTemplate.opsForHash().put(SCENARIO_RANGE, String.valueOf(3), JsonUtils.serialize(s));
        stringRedisTemplate.opsForHash().put(SCENARIO_RANGE, String.valueOf(4), s);
    }


    @Test
    public void test() {
        // 创建消息记录, 以及指定stream
        StringRecord stringRecord = StreamRecords.string(Collections.singletonMap("name", "KevinBlandy")).withStreamKey("mystream");
        RecordId recordId = this.stringRedisTemplate.opsForStream().add(stringRecord);
        // 是否是自动生成的
        boolean autoGenerated = recordId.shouldBeAutoGenerated();
        // id值
        String value = recordId.getValue();
        // 序列号部分
        long sequence = recordId.getSequence();
        // 时间戳部分
        long timestamp = recordId.getTimestamp();
    }


    @Test
    public void images2Base64() {
        String s = ImagesUtils.GetImageStr("/Users/nemo/work/origin.jpg");
        int length = s.length();
        ImagesUtils.GenerateImage(s, "/Users/nemo/work/1.jpg");
        System.out.println(s);

    }

    @Test
    public void msgWebchinese() {
        RestTemplate template = new RestTemplate();
        String uid = "visac";
        String key = "4c62c218b2fa4c04b5c4";
        String smsMob = "13720208320";
        String smsText = "尊敬的用户，您的家人可能在家发生异常状态，请您尽快联系家人或登录康居宝小程序查看确认。【国家数字建造技术创新中心】";
        String url = String.format("%sUid=%s&Key=%s&smsMob=%s&smsText=%s", "http://utf8.api.smschinese.cn/?", uid, key, smsMob, smsText);
        ResponseEntity<String> response = template.getForEntity(url, String.class);
        System.out.println(response.getBody());

    }


}