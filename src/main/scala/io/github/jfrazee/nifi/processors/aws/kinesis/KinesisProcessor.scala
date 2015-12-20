package io.github.jfrazee.nifi.processors.aws.kinesis

import java.security.MessageDigest
import javax.xml.bind.annotation.adapters.HexBinaryAdapter

// AWS
import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.services.kinesis.AmazonKinesisClient

// NiFi
import org.apache.nifi.flowfile.FlowFile
import org.apache.nifi.components.PropertyDescriptor
import org.apache.nifi.processor.ProcessContext
import org.apache.nifi.processors.aws.AbstractAWSProcessor

abstract class KinesisProcessor extends AbstractAWSProcessor[AmazonKinesisClient] with KinesisProcessorProperties {
  override def createClient(context: ProcessContext, credentials: AWSCredentials, config: ClientConfiguration): AmazonKinesisClient = {
    val client = new AmazonKinesisClient(credentials, config)
    for (p <- Option(context.getProperty(ENDPOINT)))
      client.setEndpoint(p.getValue)
    client
  }

  override def getSupportedPropertyDescriptors(): java.util.List[PropertyDescriptor] = {
    properties
  }

  protected def getKinesisClient(context: ProcessContext): AmazonKinesisClient = {
    val client = getClient()
    for (p <- Option(context.getProperty(ENDPOINT)))
      client.setEndpoint(p.getValue)
    client
  }

  protected def getStreamName(flowfile: FlowFile, context: ProcessContext): String = {
    context.getProperty(STREAM).evaluateAttributeExpressions(flowfile).getValue()
  }

  protected def getPartitionKey(bytes: Array[Byte]): String = {
    val md5 = MessageDigest.getInstance("MD5")
    val digest = md5.digest(bytes)
    new HexBinaryAdapter().marshal(digest).toLowerCase
  }
}
