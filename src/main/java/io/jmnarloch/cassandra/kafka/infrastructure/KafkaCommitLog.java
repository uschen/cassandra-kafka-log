/**
 * Copyright (c) 2015-2016 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.cassandra.kafka.infrastructure;

import io.jmnarloch.cassandra.kafka.api.CommitLog;
import io.jmnarloch.cassandra.kafka.environment.Environment;
import io.jmnarloch.cassandra.kafka.environment.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaCommitLog implements CommitLog {

    private final String topic;

    private final KafkaProducer<String, byte[]> kafkaProducer;

    public KafkaCommitLog(Environment environment) {
        this.topic = environment.get(Properties.KAFKA_TOPIC);
        this.kafkaProducer = new KafkaProducer<>(environment.prefix("kafka.").asProperties());
    }

    @Override
    public void commit(String key, byte[] data) {
        kafkaProducer.send(new ProducerRecord<>(topic, key, data));
    }

    @Override
    public void close() {
        kafkaProducer.close();
    }
}
