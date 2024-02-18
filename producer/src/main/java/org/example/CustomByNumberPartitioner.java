package org.example;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

public class CustomByNumberPartitioner implements Partitioner {

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        List<PartitionInfo> partitions =cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();

        if (keyBytes == null) {
            throw new InvalidRecordException("Incorrect key!");
        }

        try {
            int numberOfPartition = Integer.parseInt((String) key);
            if (numberOfPartition <= numPartitions)
                return numberOfPartition - 1;
        } catch (NumberFormatException ex) {
            // do nothing
        }
        return Math.abs(Utils.murmur2(keyBytes)) % (numPartitions - 1);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
