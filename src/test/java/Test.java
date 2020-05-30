import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eeo.entity.Hour24Data;
import com.eeo.entity.InOutData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONString;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;



public class Test {

    public static void main(String[] args) throws IOException, ParseException {

       // JSON.toJSON(new Hour24Data());
        System.out.println(JSON.toJSON(new Hour24Data()));

    }
}
