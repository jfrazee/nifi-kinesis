package io.github.jfrazee.nifi.processors.aws.kinesis

import java.util.{ Collections, Arrays }

// NiFi
import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.processor.util.StandardValidators
import org.apache.nifi.processors.aws.AbstractAWSProcessor

trait KinesisProcessorProperties {
  val ACCESS_KEY = AbstractAWSProcessor.ACCESS_KEY
  val SECRET_KEY = AbstractAWSProcessor.SECRET_KEY
  val CREDENTIALS_FILE = AbstractAWSProcessor.CREDENTIALS_FILE
  val REGION = AbstractAWSProcessor.REGION
  val TIMEOUT = AbstractAWSProcessor.TIMEOUT

  val ENDPOINT =
    new PropertyDescriptor.Builder()
      .name("Endpoint")
      .description("The AWS endpoint for the processor")
      .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
      .build

  val STREAM =
    new PropertyDescriptor.Builder()
      .name("Stream")
      .description("The stream name associated with the processor")
      .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
      .expressionLanguageSupported(true)
      .required(true)
      .build

  val properties = Collections.unmodifiableList(Arrays.asList(
    ENDPOINT, STREAM, ACCESS_KEY, SECRET_KEY, CREDENTIALS_FILE, REGION, TIMEOUT))
}
