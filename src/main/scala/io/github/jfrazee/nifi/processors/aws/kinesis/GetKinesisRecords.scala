package io.github.jfrazee.nifi.processors.aws.kinesis

// NiFi
import org.apache.nifi.processor.{ ProcessContext, ProcessSession }
import org.apache.nifi.processors.aws.AbstractAWSProcessor
import org.apache.nifi.annotation.documentation.{ Tags, CapabilityDescription }

@Tags(Array("Amazon", "AWS", "Kinesis", "Put", "Publish"))
@CapabilityDescription("Get data records from an Amazon Kinesis stream")
class GetKinesisRecords extends KinesisProcessor {
  def onTrigger(context: ProcessContext, session: ProcessSession): Unit = {
    for (flowfile <- Option(session.get)) {
      session.transfer(flowfile, REL_SUCCESS)
    }
  }
}

object GetKinesisRecords extends KinesisProcessorProperties
