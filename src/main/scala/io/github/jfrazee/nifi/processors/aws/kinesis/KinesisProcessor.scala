package io.github.jfrazee.nifi.processors.aws.kinesis

// AWS
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.kinesis.AmazonKinesisClient

// NiFi
import org.apache.nifi.processor.ProcessContext
import org.apache.nifi.processors.aws.AbstractAWSProcessor

trait KinesisProcessor extends AbstractAWSProcessor[AmazonKinesisClient] {
  override def createClient(context: ProcessContext, credentials: AWSCredentials, config: ClientConfiguration): AmazonKinesisClient = {
    new AmazonKinesisClient(credentials, config)
  }
}
