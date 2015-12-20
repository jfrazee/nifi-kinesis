package io.github.jfrazee.nifi.processors.aws.kinesis

import java.io._

// ScalaTest
import org.scalatest._

// NiFi
import org.apache.nifi.util.{ TestRunner, TestRunners }

class PutKinesisRecordSpec extends FunSpec {
  describe("PutKinesisRecord") {
    it("should successfully transfer a FlowFile") {
      val runner = TestRunners.newTestRunner(new PutKinesisRecord)
      runner.setProperty(PutKinesisRecord.ACCESS_KEY, "XXXX")
      runner.setProperty(PutKinesisRecord.SECRET_KEY, "XXXX")
      runner.setProperty(PutKinesisRecord.ENDPOINT, "http://localhost:4567")
      runner.setProperty(PutKinesisRecord.STREAM, "put_record_test")
      runner.setProperty(PutKinesisRecord.TIMEOUT, "30 secs")

      val content = new ByteArrayInputStream("{}".getBytes)
      runner.enqueue(content)
      runner.run(1)
      runner.assertAllFlowFilesTransferred(REL_SUCCESS, 1)
    }
  }
}
