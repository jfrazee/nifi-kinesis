package io.github.jfrazee.nifi.processors.aws.kinesis

import java.util.{ Collections, Arrays }

// NiFi
import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.processor.util.StandardValidators
import org.apache.nifi.processors.aws.AbstractAWSProcessor

trait KinesisProcessorProperties {
  import AbstractAWSProcessor.{ ACCESS_KEY, SECRET_KEY, CREDENTIALS_FILE, REGION, TIMEOUT }

  val STREAM =
    new PropertyDescriptor.Builder()
      .name("Stream")
      .description("The stream name associated with the processor")
      .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
      .required(true)
      .build

  val properties = Collections.unmodifiableList(Arrays.asList(
    STREAM, ACCESS_KEY, SECRET_KEY, CREDENTIALS_FILE, REGION, TIMEOUT))
}
