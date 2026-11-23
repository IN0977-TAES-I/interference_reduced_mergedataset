public class KafkaSpoutConfig<K, V> implements Serializable {

    private final Map<String, Object> kafkaProps;
    private final Deserializer<K> keyDeserializer;
    private final Deserializer<V> valueDeserializer;
    private final long pollTimeoutMs;
    private final long offsetCommitPeriodMs;
    private final int maxUncommittedOffsets;
    private final FirstPollOffsetStrategy firstPollOffsetStrategy;
    private final KafkaSpoutStreams kafkaSpoutStreams;
    private final KafkaSpoutTuplesBuilder<K, V> tuplesBuilder;
    private final KafkaSpoutRetryService retryService;
    private final Map<String, Object> kafkaProps;
    private SerializableDeserializer<K> keyDeserializer;
    private SerializableDeserializer<V> valueDeserializer;
    private long pollTimeoutMs = DEFAULT_POLL_TIMEOUT_MS;
    private long offsetCommitPeriodMs = DEFAULT_OFFSET_COMMIT_PERIOD_MS;
    private FirstPollOffsetStrategy firstPollOffsetStrategy = FirstPollOffsetStrategy.UNCOMMITTED_EARLIEST;
    private final KafkaSpoutStreams kafkaSpoutStreams;
    private int maxUncommittedOffsets = DEFAULT_MAX_UNCOMMITTED_OFFSETS;
    private final KafkaSpoutTuplesBuilder<K, V> tuplesBuilder;
    private final KafkaSpoutRetryService retryService;

        public List<String> getSubscribedTopics() {
            return kafkaSpoutStreams instanceof KafkaSpoutStreamsNamedTopics ?
                new ArrayList<>(((KafkaSpoutStreamsNamedTopics) kafkaSpoutStreams).getTopics()) :
                null;
        }

        public Pattern getTopicWildcardPattern() {
            return kafkaSpoutStreams instanceof KafkaSpoutStreamsWildcardTopics ?
                    ((KafkaSpoutStreamsWildcardTopics)kafkaSpoutStreams).getTopicWildcardPattern() :
                    null;
        }

        @Override
        public String toString() {
            return "KafkaSpoutConfig{" +
                    "kafkaProps=" + kafkaProps +
                    ", keyDeserializer=" + keyDeserializer +
                    ", valueDeserializer=" + valueDeserializer +
                    ", pollTimeoutMs=" + pollTimeoutMs +
                    ", offsetCommitPeriodMs=" + offsetCommitPeriodMs +
                    ", maxUncommittedOffsets=" + maxUncommittedOffsets +
                    ", firstPollOffsetStrategy=" + firstPollOffsetStrategy +
                    ", kafkaSpoutStreams=" + kafkaSpoutStreams +
                    ", tuplesBuilder=" + tuplesBuilder +
                    ", retryService=" + retryService +
                    ", topics=" + getSubscribedTopics() +
                    ", topicWildcardPattern=" + getTopicWildcardPattern() +
                    '}';
        }

}
