public class KafkaSpoutConfig<K, V> implements Serializable {

    private final Map<String, Object> kafkaProps;
    private Subscription subscription;
    private final SerializableDeserializer<K> keyDes;
    private final Class<? extends Deserializer<K>> keyDesClazz;
    private final SerializableDeserializer<V> valueDes;
    private final Class<? extends Deserializer<V>> valueDesClazz;
    private RecordTranslator<K, V> translator;
    private long pollTimeoutMs = DEFAULT_POLL_TIMEOUT_MS;
    private long offsetCommitPeriodMs = DEFAULT_OFFSET_COMMIT_PERIOD_MS;
    private FirstPollOffsetStrategy firstPollOffsetStrategy = FirstPollOffsetStrategy.UNCOMMITTED_EARLIEST;
    private int maxUncommittedOffsets = DEFAULT_MAX_UNCOMMITTED_OFFSETS;
    private KafkaSpoutRetryService retryService = DEFAULT_RETRY_SERVICE;
    private final Map<String, Object> kafkaProps;
    private final Subscription subscription;
    private final SerializableDeserializer<K> keyDes;
    private final Class<? extends Deserializer<K>> keyDesClazz;
    private final SerializableDeserializer<V> valueDes;
    private final Class<? extends Deserializer<V>> valueDesClazz;
    private final long pollTimeoutMs;
    private final RecordTranslator<K, V> translator;
    private final long offsetCommitPeriodMs;
    private final int maxUncommittedOffsets;
    private final FirstPollOffsetStrategy firstPollOffsetStrategy;
    private final KafkaSpoutRetryService retryService;

        public Deserializer<K> getKeyDeserializer() {
            if (keyDesClazz != null) {
                try {
                    return keyDesClazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Could not instantiate key deserializer " + keyDesClazz);
                }
            }
            return keyDes;
        }

        public Deserializer<V> getValueDeserializer() {
            if (valueDesClazz != null) {
                try {
                    return valueDesClazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Could not instantiate value deserializer " + valueDesClazz);
                }
            }
            return valueDes;
        }

        @Override
        public String toString() {
            return "KafkaSpoutConfig{" +
                    "kafkaProps=" + kafkaProps +
                    ", key=" + getKeyDeserializer() +
                    ", value=" + getValueDeserializer() +
                    ", pollTimeoutMs=" + pollTimeoutMs +
                    ", offsetCommitPeriodMs=" + offsetCommitPeriodMs +
                    ", maxUncommittedOffsets=" + maxUncommittedOffsets +
                    ", firstPollOffsetStrategy=" + firstPollOffsetStrategy +
                    ", subscription=" + subscription +
                    ", translator=" + translator +
                    ", retryService=" + retryService +
                    '}';
        }

}
