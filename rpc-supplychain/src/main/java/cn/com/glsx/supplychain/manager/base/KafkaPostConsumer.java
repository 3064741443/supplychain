package cn.com.glsx.supplychain.manager.base;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.oreframework.jms.core.MessageConsumer;
import org.oreframework.jms.core.MessageMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public abstract class KafkaPostConsumer implements MessageConsumer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaPostConsumer.class);
    @Autowired
    @Qualifier("consumerProperties")
    protected Properties consumerProperties;
    protected ConsumerConfig consumerConfig;
    protected String topic;
    @Value("${spring.kafka.consumer.partitionsNum}")
    protected Integer partitionsNum = 6;
    protected ConsumerConnector connector;
    protected MessageMetadata metadata;
    protected ThreadLocal<MessageMetadata> localMetadata = new ThreadLocal();
    protected ThreadLocal<byte[]> localMessage = new ThreadLocal();

    protected KafkaPostConsumer(String topic) {
        this.topic = topic;
    }

    @PostConstruct
    public void init() {
        if (this.consumerProperties == null) {
            logger.info("Producer Properties is null, init Failed.");
        }

        Map<String, Integer> topicCountMap = new HashMap();
        if (this.consumerConfig == null) {
            this.consumerConfig = new ConsumerConfig(this.consumerProperties);
        }

        this.connector = Consumer.createJavaConsumerConnector(this.consumerConfig);
        String[] topics = this.topic.split(",");

        for(int i = 0; i < topics.length; ++i) {
            topicCountMap.put(topics[i], this.partitionsNum);
        }

        Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams = this.connector.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = new ArrayList();

        for(int i = 0; i < topics.length; ++i) {
            streams.addAll((Collection)topicMessageStreams.get(topics[i]));
        }

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException var8) {
            logger.error(var8.getMessage());
        }

        final ExecutorService executor = Executors.newFixedThreadPool(this.partitionsNum * topics.length);
        Iterator var6 = streams.iterator();

        while(var6.hasNext()) {
            final KafkaStream<byte[], byte[]> stream = (KafkaStream)var6.next();
            executor.submit(new Runnable() {
                public void run() {
                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
                    MessageAndMetadata messageAndMetadata = null;

                    while(it.hasNext()) {
                        messageAndMetadata = it.next();
                        MessageMetadata metadata = new MessageMetadata(messageAndMetadata.topic(), messageAndMetadata.partition(), messageAndMetadata.offset());
                        byte[] message = (byte[])messageAndMetadata.message();
                        KafkaPostConsumer.this.setMessage(message);
                        KafkaPostConsumer.this.setMetadata(metadata);

                        try {
                            KafkaPostConsumer.this.processMessage(message);
                        } catch (Throwable var6) {
                            KafkaPostConsumer.logger.error(var6.getMessage(), var6);
                        }
                    }

                }
            });
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                executor.shutdown();
            }
        }));
    }

    public void setConsumerConfig(ConsumerConfig consumerConfig) {
        this.consumerConfig = consumerConfig;
    }

    @PreDestroy
    public void destroy() {
        if (null != this.connector) {
            this.connector.shutdown();
        }

    }

    public MessageMetadata getMetadata() {
        return (MessageMetadata)this.localMetadata.get();
    }

    public void setMetadata(MessageMetadata metadata) {
        this.localMetadata.set(metadata);
    }

    public byte[] getMessage() {
        return (byte[])this.localMessage.get();
    }

    public void setMessage(byte[] message) {
        this.localMessage.set(message);
    }

    public void setConsumerProperties(Properties consumerProperties) {
        this.consumerProperties = consumerProperties;
    }

}
