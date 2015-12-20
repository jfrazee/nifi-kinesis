package io.github.jfrazee.nifi.processors.aws.kinesis

import java.io._
import java.nio.ByteBuffer

// NiFi
import org.apache.nifi.flowfile.FlowFile
import org.apache.nifi.processor.{ ProcessContext, ProcessSession }
import org.apache.nifi.processors.aws.AbstractAWSProcessor
import org.apache.nifi.annotation.documentation.{ Tags, CapabilityDescription }

// AWS
import com.amazonaws.services.kinesis.model.{ PutRecordsRequest, PutRecordsRequestEntry }

@Tags(Array("Amazon", "AWS", "Kinesis", "Put", "Publish"))
@CapabilityDescription("Write a data record from a producer into an Amazon Kinesis stream")
class PutKinesisRecord extends KinesisProcessor {
  import scala.collection.JavaConversions._

  private[this] def newRecordsRequestEntry(flowfile: FlowFile, session: ProcessSession): PutRecordsRequestEntry = {
    val out = new ByteArrayOutputStream

    session.exportTo(flowfile, out)

    val bytes = out.toByteArray
    val buffer = ByteBuffer.wrap(bytes)
    val key = getPartitionKey(bytes)

    new PutRecordsRequestEntry().withData(buffer).withPartitionKey(key)
  }

  private[this] def newRecordsRequest(flowfile: FlowFile, context: ProcessContext, session: ProcessSession): PutRecordsRequest = {
    val stream = getStreamName(flowfile, context)
    val record = newRecordsRequestEntry(flowfile, session)
    val records = List(record)
    new PutRecordsRequest().withStreamName(stream).withRecords(records)
  }

  def onTrigger(context: ProcessContext, session: ProcessSession): Unit = {
    for (flowfile <- Option(session.get)) {
      val request = newRecordsRequest(flowfile, context, session)
      val client = getKinesisClient(context)
      client.putRecords(request)
      session.transfer(flowfile, REL_SUCCESS)
    }
  }
}

object PutKinesisRecord extends KinesisProcessorProperties
